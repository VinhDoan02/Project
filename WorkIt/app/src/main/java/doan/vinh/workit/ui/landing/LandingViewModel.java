package doan.vinh.workit.ui.landing;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import doan.vinh.workit.data.DataService;
import doan.vinh.workit.data.model.RealmObject.RealmExercise;
import doan.vinh.workit.ui.base.BaseViewModel;
import doan.vinh.workit.utils.rx.SchedulerProvider;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Doand on 3/15/2018.
 */

public class LandingViewModel extends BaseViewModel {
    String TAG = "LandingViewModel";
    MutableLiveData<Boolean> navigationData = new MutableLiveData<Boolean>();

    public LandingViewModel(SchedulerProvider schedulerProvider, DataService dataService) {
        super(schedulerProvider, dataService);
    }


    public void checkUser() {
        getDataService().hasUserInfo().subscribe(hasUser -> {
            navigationData.setValue(hasUser);
        });

    }


    public void checkExercise()
    {
        getDataService().hasExerciseLoaded().subscribe(hasExercise -> getAllExercise(hasExercise));
    }

    public void getAllExercise(Boolean hasExercise)
    {
        Log.d("landing vm " , "get all exercise " + hasExercise);
        if(!hasExercise)
        {
            getDataService().addAllExercise();
        }else
        {
            //check exercise list
            Realm realm = Realm.getDefaultInstance();
            realm.where(RealmExercise.class)
                    .findAllAsync()
                    .asFlowable()
                    .filter(RealmResults::isLoaded).subscribe(
                            resultList ->{
                                int size = resultList.size();
                                Log.d(TAG,"size " + size);

                                for(int i =0;  i < size ; i++)
                                {
                                    Log.d(TAG,"value " + resultList.get(i).getName());
                                }
                            }

            );

        }
    }

    MutableLiveData<Boolean> getNavigationData() {
        return navigationData;
    }



}
