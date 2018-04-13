package doan.vinh.scansdstorage;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    MainViewModel mainViewModel;
    NotificationCompat.Builder notification;
    TaskStackBuilder stackBuilder;
    NotificationManager manager;
    private ShareActionProvider mShareActionProvider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG,"activity");

        mainViewModel = new MainViewModel();

        checkForPermissions();
        initLayout();

        mainViewModel.getTop_10().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txt_top_10.setText(s);
            }
        });

    }

    public void initLayout()
    {


        mainViewModel.getScan_progress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                progressBar.setProgress(integer);
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
                if(aBoolean)
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


    @OnCheckedChanged(R.id.tggle_scan)
    void onButtonStarted(boolean started)
    {
        ContentResolver cr = getContentResolver();

       if(started)
       {
           btn_share.setEnabled(false);
           mainViewModel.startScan(cr);
           progressBar.setVisibility(View.VISIBLE);
           startStatusNotification();
       }else
       {
           Log.d(TAG,"stopped");
           mainViewModel.stopScan();
           stopStatusNotification();
       }
    }

    public void startStatusNotification()
    {
        Log.d(TAG,"notification started");
        notification = new NotificationCompat.Builder(MainActivity.this);
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

       notification.setChannelId("channel_sd");


        manager.notify(0, notification.build());
    }

    public void stopStatusNotification()
    {
      //  manager.cancelAll();
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
        mainViewModel.stopScan();
        super.onBackPressed();
    }
}
