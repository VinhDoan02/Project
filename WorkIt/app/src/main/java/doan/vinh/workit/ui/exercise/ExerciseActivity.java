package doan.vinh.workit.ui.exercise;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import doan.vinh.workit.R;
import doan.vinh.workit.ui.mainpage.MainActivity;

public class ExerciseActivity extends AppCompatActivity {

    String TAG = "Exercise Activity";
    @Inject
    ExerciseViewModel mExerciseViewModel;

    @BindView(R.id.txt_timer) TextView txt_timer;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String exercise_type = intent.getStringExtra("eType");
        //Log.d()
        init(exercise_type);
    }

    public void init(String exerciseID)
    {
        //mExerciseViewModel.startTimer();
        dialog = onCreateDialog();
        mExerciseViewModel.getExerciseFromID(exerciseID);
        mExerciseViewModel.getTimerData().observe(this,response ->updateTimer(response));
        mExerciseViewModel.getTimerCompleted().observe(this,response -> onTimerCompleted(response));
        mExerciseViewModel.getUploadComplete().observe(this,response ->onUploadCompleted(response));
    }

    public void onUploadCompleted(boolean completed)
    {
        if(completed)
        {
            Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }

    @BindView(R.id.btn_trigger_timer) Button btn_trigger_timer;
    //@OnClick(R.id.btn_trigger_timer)
    @OnCheckedChanged(R.id.btn_trigger_timer)
    void onButtonStarted(boolean started)
    {
        mExerciseViewModel.triggerTimer(started);
    }

    @BindView(R.id.btn_cancel) Button btn_cancel;
    @OnClick(R.id.btn_cancel)
    void onCancelExercise()
    {
        Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void updateTimer(String timer)
    {
        txt_timer.setText(timer);
    }

    public void onTimerCompleted(boolean completed)
    {
        if(completed)
        {
            //pop up dialog box to ask for number of repetition;
            dialog.show();
        }

    }

    public Dialog onCreateDialog()
    {
       Dialog dialog = new Dialog(this);
       dialog.setContentView(R.layout.dialog_exercise_completed);

       Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
       EditText edit_result = (EditText)dialog.findViewById(R.id.edit_qte_completed);

       btn_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String txt_int = edit_result.getText().toString();
               if(!txt_int.isEmpty())
               {
                   int qte = Integer.parseInt(txt_int);
                   if(qte>=0)
                   {
                       mExerciseViewModel.submitResult(qte);
                       dialog.hide();
                   }
               }else
               {
                   Log.d(TAG,"please input a number");
               }

           }
       });

       dialog.setCancelable(false);
       dialog.setCanceledOnTouchOutside(false);
       return dialog;
    }



}
