package doan.vinh.workit.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import dagger.android.support.AndroidSupportInjection;
import doan.vinh.workit.ui.mainpage.MainActivity;

/**
 * Created by Doand on 3/22/2018.
 */

public abstract class BaseFragment extends Fragment {

    private MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            this.mActivity = activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }



    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public MainActivity getBaseActivity() {
        return mActivity;
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

}
