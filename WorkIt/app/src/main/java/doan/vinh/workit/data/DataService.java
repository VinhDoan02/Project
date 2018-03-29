package doan.vinh.workit.data;

import doan.vinh.workit.data.model.Exercise;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.realm.RealmResults;

/**
 * Created by Doand on 3/25/2018.
 */

public interface DataService {

    Single<Boolean> hasUserInfo();

    Single<Boolean> hasExerciseLoaded();

    Completable createGuestUser();

    Completable addRepetition(long date,String ExerciseID,long timeElapsed,int qte);

    Completable addRepetition(long date,String ExerciseID,long timeElapsed);

    Flowable<Exercise> getExerciseFromID(String exerciseID);

    Flowable<RealmResults> getAllExercise();

    Completable addAllExercise();

}
