package doan.vinh.workit.data.model.RealmObject;

import io.realm.RealmObject;

/**
 * Created by Doand on 3/25/2018.
 */

public class RealmRepetition extends RealmObject {
    long date;
    long duration;
    int qte;
    String exerciseID;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    /**
     * save duration in seconds
     * @param duration in milliseconds
     */
    public void setDuration(long duration) {

        this.duration = (duration/1000);

    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String printOut()
    {
        return date + " : " + exerciseID
                + "\n"+ duration + " - " + qte;
    }
}
