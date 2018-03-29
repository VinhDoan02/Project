package doan.vinh.workit.ui.mainpage.statstab;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.Transformations;
import android.util.Log;

import java.util.ArrayList;

import doan.vinh.workit.data.DataService;
import doan.vinh.workit.data.model.RealmObject.RealmExercise;
import doan.vinh.workit.data.model.StatsModel;
import doan.vinh.workit.ui.base.BaseViewModel;
import doan.vinh.workit.utils.rx.SchedulerProvider;
import io.realm.RealmResults;

/**
 * Created by Doand on 3/27/2018.
 */

public class StatsTabViewModel extends BaseViewModel {

    private LiveData<ArrayList<StatsModel>> response;


    public StatsTabViewModel(SchedulerProvider schedulerProvider, DataService dataService) {
        super(schedulerProvider, dataService);
        pullExerciseData();
    }


    public void pullExerciseData()
    {
        LiveData<RealmResults> realmdata = LiveDataReactiveStreams.fromPublisher(getDataService()
                .getAllExercise()
                .subscribeOn(getSchedulerProvider().ui())
                .observeOn(getSchedulerProvider().ui()));



        response = Transformations.map(realmdata, new Function<RealmResults, ArrayList<StatsModel>>() {

            @Override
            public ArrayList<StatsModel> apply(RealmResults input) {


                ArrayList<StatsModel> statsList = new ArrayList<>();
                int length = input.size();
                Log.d("s tab view ", "apply " + length );
                for(int i = 0; i < length;i++)
                {
                    RealmExercise tempRealmExercise = (RealmExercise) input.get(i);
                    Log.d("s tab ", "stufff " + tempRealmExercise.getName());
                    Log.d("s tab ", "stufff " + tempRealmExercise.getRepetitionList().size());
                    StatsModel tempStatsModel = new StatsModel();
                    tempStatsModel.initModel((RealmExercise) input.get(i));
                    statsList.add(tempStatsModel);
                }
                return statsList;
            }
        });
    }


    public LiveData<ArrayList<StatsModel>> getResponse()
    {
        return response;
    }


}
