package doan.vinh.workit.data;

import android.util.Log;

import doan.vinh.workit.data.model.Exercise;
import doan.vinh.workit.data.model.RealmObject.RealmExercise;
import doan.vinh.workit.data.model.RealmObject.RealmRepetition;
import doan.vinh.workit.data.model.RealmObject.RealmUser;
import doan.vinh.workit.utils.AppConstants;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by Doand on 3/26/2018.
 */

public class RealmDataService implements DataService{

    @Override
    public Single<Boolean> hasUserInfo() {

        final Realm realm = Realm.getDefaultInstance();
        Single<Boolean> returnValue = realm.where(RealmUser.class)
                .findAllAsync()
                .asFlowable().doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        realm.close();
                    }
                }).filter(RealmResults::isLoaded).map(new Function<RealmResults<RealmUser>, Boolean>() {
                                                          @Override
                                                          public Boolean apply(RealmResults<RealmUser> userRealms) throws Exception {
                                                              return userRealms.isEmpty();
                                                          }
                                                      }
                ).firstOrError();

        return returnValue;

    }

    @Override
    public Single<Boolean> hasExerciseLoaded() {
        final Realm realm = Realm.getDefaultInstance();
        Single<Boolean> returnValue = realm.where(RealmExercise.class)
                .findAllAsync()
                .asFlowable().doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        realm.close();
                    }
                }).filter(RealmResults::isLoaded).map(new Function<RealmResults<RealmExercise>, Boolean>() {
                                                          @Override
                                                          public Boolean apply(RealmResults<RealmExercise> exerciseRealms) throws Exception {
                                                              Boolean hasExercise = !(exerciseRealms.isEmpty());
                                                              return hasExercise;
                                                          }
                                                      }
                ).firstOrError();

        return returnValue;
    }

    @Override
    public Completable createGuestUser() {

        return Completable.fromCallable(()->{
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        RealmUser guest = bgRealm.createObject(RealmUser.class);
                        guest.saveEnum(RealmUtils.LoggedInMode.LOGGED_IN_MODE_GUEST);
                    }
                });
            } finally {
                if (realm != null) {
                    realm.close();

                }
            }

            return true;
        });

    }



    @Override
    public Completable addRepetition(long date, String ExerciseID, long timeElapsed, int qte) {

        return Completable.fromCallable(()->{
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {

                        RealmExercise exercise;
                        RealmResults<RealmExercise> exerciseResult = bgRealm.where(RealmExercise.class)
                                .contains("exerciseID", ExerciseID)
                                .findAll();
                        exercise = exerciseResult.get(0);

                        Log.d("data service "," " + exercise.getExerciseID());
                        RealmRepetition repetition = bgRealm.createObject(RealmRepetition.class);
                        repetition.setDate(date);
                        repetition.setExerciseID(ExerciseID);
                        repetition.setDuration(timeElapsed);
                        repetition.setQte(qte);

                        exercise.addRepetitionToList(repetition);
                    }
                });
            } finally {
                if (realm != null) {
                    realm.close();

                }
            }

            return true;
        });
    }

    @Override
    public Completable addRepetition(long date, String ExerciseID, long timeElapsed) {
        return Completable.fromCallable(()->{
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {

                        RealmExercise exercise;
                        RealmResults<RealmExercise> usersResult = bgRealm.where(RealmExercise.class)
                                .contains("exerciseID", ExerciseID)
                                .findAll();
                        exercise = usersResult.get(0);

                        RealmRepetition repetition = bgRealm.createObject(RealmRepetition.class);
                        repetition.setDate(date);
                        repetition.setExerciseID(ExerciseID);
                        repetition.setDuration(timeElapsed);

                        exercise.addRepetitionToList(repetition);
                    }
                });
            } finally {
                if (realm != null) {
                    realm.close();

                }
            }
            return true;
        });
    }


    @Override
    public Flowable<Exercise> getExerciseFromID(String exerciseID) {

        final Realm realm = Realm.getDefaultInstance();
        Flowable returnValue = realm.where(RealmExercise.class).contains("exerciseID",exerciseID)
                .findAllAsync()
                .asFlowable()
                .filter(RealmResults::isLoaded).map(new Function<RealmResults<RealmExercise>, Exercise>() {

                    @Override
                    public Exercise apply(RealmResults<RealmExercise> realmExercises) throws Exception {

                        RealmExercise tempRealmExercise = realmExercises.get(0);
                        Exercise tempExercise = new Exercise();
                        tempExercise.initExercise(tempRealmExercise);
                        return tempExercise;
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        realm.close();
                    }
                });

        return returnValue;

    }

    @Override
    public Flowable<RealmResults> getAllExercise() {

        final Realm realm = Realm.getDefaultInstance();
        Flowable returnValue = realm.where(RealmExercise.class)
                .findAllAsync()
                .asFlowable()
                .filter(RealmResults::isLoaded).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        realm.close();
                    }
                });;

        return returnValue;
    }

    @Override
    public Completable addAllExercise() {

        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Log.d(TAG,"add exercise");
                RealmExercise pushup = bgRealm.createObject(RealmExercise.class);
                pushup.init("Push Up", AppConstants.EXERCISE_PUSHUP,true);

                RealmExercise burpee = bgRealm.createObject(RealmExercise.class);
                burpee.init("Burpee", AppConstants.EXERCISE_BURPEE,true);

                RealmExercise plank = bgRealm.createObject(RealmExercise.class);
                plank.init("Plank", AppConstants.EXERCISE_PLANK,false);

                RealmExercise mountain_climber = bgRealm.createObject(RealmExercise.class);
                mountain_climber.init("Mountain Climber", AppConstants.EXERCISE_MOUTAIN_CLIMBER,true);

                RealmExercise squat = bgRealm.createObject(RealmExercise.class);
                squat.init("Squat", AppConstants.EXERCISE_SQUAT,true);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                realm.close();
            }
        },new Realm.Transaction.OnError(){

            @Override
            public void onError(Throwable error) {
                realm.close();
            }
        });

        return null;
    }
}
