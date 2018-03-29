package doan.vinh.workit.ui.exercise;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.util.Log;

import doan.vinh.workit.data.DataService;
import doan.vinh.workit.data.model.Exercise;
import doan.vinh.workit.ui.base.BaseViewModel;
import doan.vinh.workit.utils.rx.SchedulerProvider;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Doand on 3/24/2018.
 */

public class ExerciseViewModel extends BaseViewModel {

    Handler timerHandler;
    SchedulerProvider sProvider;
    long startTime = 0;
    long timeElapsed;
    MutableLiveData<String> timerData = new MutableLiveData<String>();
    MutableLiveData<Boolean> uploadComplete = new MutableLiveData<>();
    MutableLiveData<Boolean> timerCompleted = new MutableLiveData<>();

    Boolean timerStarted = false;
    String TAG = "EviewModel";

    Exercise exercise;
    //ExerciseWrapper eWrapper;

    public ExerciseViewModel(SchedulerProvider sProvider, DataService dataService)
    {
        super(sProvider,dataService);
        this.sProvider = sProvider;
    }



    public void startTimer()
    {

        timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {

            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int hundreds = (int)(millis/10);
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                hundreds = hundreds % 100;
               /* Log.d(TAG,"hund" + hundreds);
                Log.d(TAG,"seco" + seconds);
                Log.d(TAG,"minu" + minutes);*/
                String time = String.format("%02d:%02d:%02d", minutes, seconds,hundreds);
                timerData.setValue(time);
                timerData.setValue(time);
                if(timerStarted)
                {
                    timerHandler.postDelayed(this, 50);
                }

            }
        };


       timerHandler.post(timerRunnable);

    }

    public void triggerTimer()
    {
        if(timerStarted)
        {
            Log.d(TAG,"Stop");
            stopTimer();
            timerStarted = false;

        }else
        {
            timerStarted = true;
            startTime = System.currentTimeMillis();
            startTimer();
        }
    }


    public void stopTimer()
    {
        timerHandler.removeCallbacksAndMessages(null);
        //save data to realm
        //date
        //number of rep
        //
        timeElapsed = System.currentTimeMillis()-startTime;

        if(exercise.isCountable())
        {
            timerCompleted.setValue(true);
        }else
        {
            submitResult();
        }

    }
    public void submitResult()
    {
        //add to realm with time elapse and current date and exercise I
        long date = System.currentTimeMillis();
        getDataService().addRepetition(date, exercise.getExerciseID(),timeElapsed).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                uploadComplete.setValue(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void submitResult(int qte)
    {
        long date = System.currentTimeMillis();
        getDataService().addRepetition(date, exercise.getExerciseID(),timeElapsed,qte).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                uploadComplete.setValue(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void getExerciseFromID(String exerciseID)
    {

        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(getDataService().getExerciseFromID(exerciseID)
                .subscribeOn(getSchedulerProvider().ui())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(exercise -> this.exercise = exercise));


    }

    MutableLiveData<Boolean> getUploadComplete(){return uploadComplete;}

    MutableLiveData<Boolean> getTimerCompleted(){return timerCompleted;}

    MutableLiveData<String> getTimerData() {
        return timerData;
    }
}
