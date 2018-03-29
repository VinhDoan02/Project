package doan.vinh.workit.data.model.RealmObject;

import doan.vinh.workit.data.RealmUtils;
import io.realm.RealmObject;

/**
 * Created by Doand on 3/15/2018.
 */

public class RealmUser extends RealmObject {

    String loggedMode;


    public void saveEnum(RealmUtils.LoggedInMode val) {
        this.loggedMode = val.toString();
    }

    public RealmUtils.LoggedInMode getEnum() {
        return RealmUtils.LoggedInMode.valueOf(loggedMode);
    }

    String Username;



}

