package doan.vinh.workit.ui.mainpage.workouttab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.AndroidSupportInjection;
import doan.vinh.workit.R;
import doan.vinh.workit.ui.base.BaseFragment;


public class WorkoutTabFragment extends BaseFragment {

    public static WorkoutTabFragment newInstance()
    {
        Bundle args = new Bundle();
        WorkoutTabFragment fragment = new WorkoutTabFragment();
        fragment.setArguments(args);
        return fragment;
    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_tab, container, false);
    }


    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }



}
