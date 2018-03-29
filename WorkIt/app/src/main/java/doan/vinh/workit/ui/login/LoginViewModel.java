package doan.vinh.workit.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import doan.vinh.workit.data.RealmUtils;
import doan.vinh.workit.data.model.RealmObject.RealmUser;
import io.realm.Realm;

/**
 * Created by Doand on 3/15/2018.
 */

public class LoginViewModel {

    String TAG = "LoginViewModel";
    MutableLiveData<Boolean> navigationData = new MutableLiveData<Boolean>();

    public LoginViewModel()
    {

    }



    public void guestLogin()
    {
        //add guest to realm;

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RealmUser guest = bgRealm.createObject(RealmUser.class);
                guest.saveEnum(RealmUtils.LoggedInMode.LOGGED_IN_MODE_GUEST);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG,"on guest creation success");
                navigationData.setValue(true);

            }
        });

    }

    MutableLiveData<Boolean> getNavigationData() {
        return navigationData;
    };
}
