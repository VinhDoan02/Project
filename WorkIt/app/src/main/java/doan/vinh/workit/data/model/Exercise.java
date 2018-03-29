package doan.vinh.workit.data.model;

import doan.vinh.workit.data.model.RealmObject.RealmExercise;

/**
 * Created by Doand on 3/26/2018.
 */

public class Exercise {
    String name;
    String type;
    String exerciseID;
    boolean isCountable;

    public void initExercise(RealmExercise realmExercise)
    {
        this.name = realmExercise.getName();
        this.type = realmExercise.getType();
        this.exerciseID = realmExercise.getExerciseID();
        this.isCountable = realmExercise.isCountable();
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public boolean isCountable() {
        return isCountable;
    }
}
