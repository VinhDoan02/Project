package doan.vinh.workit.data;

/**
 * Created by Doand on 3/15/2018.
 */

public class RealmUtils {



    public enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3),
        LOGGED_IN_MODE_GUEST(4);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }

}
