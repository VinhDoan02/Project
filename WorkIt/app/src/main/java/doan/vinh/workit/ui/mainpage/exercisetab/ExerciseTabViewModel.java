package doan.vinh.workit.ui.mainpage.exercisetab;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.Transformations;

import java.util.ArrayList;

import doan.vinh.workit.data.DataService;
import doan.vinh.workit.data.model.Exercise;
import doan.vinh.workit.data.model.RealmObject.RealmExercise;
import doan.vinh.workit.ui.base.BaseViewModel;
import doan.vinh.workit.utils.rx.SchedulerProvider;
import io.realm.RealmResults;

/**
 * Created by Doand on 3/22/2018.
 */

public class ExerciseTabViewModel extends BaseViewModel{

    private LiveData<ArrayList<Exercise>> response;

    public void loadExerciseList()
    {

        LiveData<RealmResults> realmdata = LiveDataReactiveStreams.fromPublisher(getDataService()
                .getAllExercise()
                .subscribeOn(getSchedulerProvider().ui())
                .observeOn(getSchedulerProvider().ui()));



       response = Transformations.map(realmdata, new Function<RealmResults, ArrayList<Exercise>>() {

            @Override
            public ArrayList<Exercise> apply(RealmResults input) {


                ArrayList<Exercise> exercisesList = new ArrayList<>();
                int length = input.size();
                for(int i = 0; i < length;i++)
                {
                    RealmExercise tempRealmExercise = (RealmExercise) input.get(i);
                    Exercise tempExercise = new Exercise();
                    tempExercise.initExercise(tempRealmExercise);
                    exercisesList.add(tempExercise);
                }
                return exercisesList;
            }
        });

    }


    public ExerciseTabViewModel(SchedulerProvider schedulerProvider, DataService dataService)
    {
        super(schedulerProvider,dataService);
    }


    LiveData<ArrayList<Exercise>> response() {
        return response;
    }


}
