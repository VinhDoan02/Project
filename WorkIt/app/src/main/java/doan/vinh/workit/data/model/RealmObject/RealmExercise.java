package doan.vinh.workit.data.model.RealmObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.Sort;

/**
 * Created by Doand on 3/22/2018.
 */

public class RealmExercise extends RealmObject {

    String name;
    String type;
    String exerciseID;
    boolean isCountable;

    RealmList<RealmRepetition> exerciseSetList;


    public void addRepetitionToList(RealmRepetition repetition)
    {
        exerciseSetList.add(repetition);

    }

    public void init(String name,String exerciseID,boolean isCountable)
    {
        this.name = name;
        this.exerciseID = exerciseID;
        this.isCountable = isCountable;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }

    public RealmList<RealmRepetition> getRepetitionList() {

        RealmList<RealmRepetition> sortedList = new RealmList<RealmRepetition>();
        sortedList.addAll(exerciseSetList.sort("date", Sort.DESCENDING));

        return sortedList;
    }

    public boolean isCountable() {
        return isCountable;
    }
}
