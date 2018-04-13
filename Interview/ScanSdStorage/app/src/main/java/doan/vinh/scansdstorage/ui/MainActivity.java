package doan.vinh.scansdstorage.ui;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import doan.vinh.scansdstorage.AppConstant;
import doan.vinh.scansdstorage.R;
import doan.vinh.scansdstorage.Utils.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private static final String IS_SCANNING = "isScanning";


    private MainViewModel mainViewModel;
    private NotificationManager manager;
    private Boolean isScanning;

    @Inject
    MainViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getViewModel();

        if(savedInstanceState != null)
        {
            isScanning = savedInstanceState.getBoolean(IS_SCANNING,true);
        }else
        {
            isScanning = false;
        }

        checkForPermissions();
        initLayout();

    }


    public void getViewModel()
    {
        mainViewModel = ViewModelProviders.of(this,factory).get(MainViewModel.class);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putBoolean(IS_SCANNING,isScanning);
    }



    public void initLayout()
    {

       // Log.d(TAG, "INITTTT layout " + mainViewModel);
        mainViewModel.getScan_progress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer!= null)
                {
                    progressBar.setProgress(integer);
                }

            }
        });

        mainViewModel.getTop_10().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txt_top_10.setText(s);
            }
        });


        mainViewModel.getAverage_file_size().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txt_average.setText(s);
            }
        });

        mainViewModel.getMost_frequent_5().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txt_mostFrequent.setText(s);
            }
        });

        mainViewModel.getOnCompleted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean != null && aBoolean)
                {
                    progressBar.setProgress(0);
                    progressBar.setVisibility(View.INVISIBLE);
                    btn_share.setEnabled(true);
                    toggleScan.setChecked(false);

                }
            }
        });

    }

    @BindView(R.id.progressBar) ProgressBar progressBar;

    @BindView(R.id.txt_top_ten)
    TextView txt_top_10;

    @BindView(R.id.txt_most_frequent) TextView txt_mostFrequent;

    @BindView(R.id.txt_average) TextView txt_average;

    @BindView(R.id.tggle_scan)
    ToggleButton toggleScan;


    @BindView(R.id.btn_share)
    Button btn_share;
    @OnClick(R.id.btn_share)
    void onButtonShareClick()
    {
        //start sharing menu
        Log.d(TAG,"sharing");

        StringBuilder dataString = new StringBuilder();
        dataString.append(txt_top_10.getText());
        dataString.append("\n");
        dataString.append(txt_mostFrequent.getText());
        dataString.append("\n");
        dataString.append(txt_average.getText());

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "\n\n");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, dataString.toString());
        startActivity(Intent.createChooser(sharingIntent,  "Share Data"));


    }

    public void clearAll()
    {
        txt_top_10.setText("");
        txt_average.setText("");
        txt_mostFrequent.setText("");
        btn_share.setEnabled(false);
    }

    @OnCheckedChanged(R.id.tggle_scan)
    void onButtonStarted(boolean started)
    {
        ContentResolver cr = getContentResolver();

       if(started && !isScanning)
       {
           clearAll();
           isScanning = true;
           mainViewModel.startScan(cr);
           progressBar.setVisibility(View.VISIBLE);
           startStatusNotification();
       }else if(started && isScanning)
       {
           progressBar.setVisibility(View.VISIBLE);
       }else
       {
           isScanning = false;
           mainViewModel.stopScan();
           progressBar.setVisibility(View.INVISIBLE);
           stopStatusNotification();
       }
    }

    public void startStatusNotification()
    {
        Log.d(TAG,"notification started");
        NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this);
        //Title for Notification
        notification.setContentTitle("ScanSdStorage");
        //Message in the Notification
        notification.setContentText("Scanning external directory in progress");
        //Alert shown when Notification is received
        notification.setTicker("ScanUtils started");
        //Icon to be set on Notification
        notification.setSmallIcon(R.drawable.ic_launcher);
        manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            Log.d(TAG,"creating channel");
            CharSequence name = "ScanUtils SD";
            String description = "Scanning external directory in progress";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel("channel_sd", name, importance);
            mChannel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            manager.createNotificationChannel(mChannel);
        }
        Log.d(TAG,"set channel");
       notification.setChannelId("channel_sd");

        Log.d(TAG,"notify channel");
        manager.notify(0, notification.build());
        Log.d(TAG,"after build");
    }

    public void stopStatusNotification()
    {
        if(manager != null)
        {
            manager.cancelAll();
        }

    }
    public void checkForPermissions()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    AppConstant.MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onBackPressed() {
        stopStatusNotification();
      //  mainViewModel.stopScan();
        super.onBackPressed();
    }
}
