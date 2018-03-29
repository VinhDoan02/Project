package doan.vinh.workit.ui.mainpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import doan.vinh.workit.ui.mainpage.exercisetab.ExerciseTabFragment;
import doan.vinh.workit.ui.mainpage.profiletab.ProfileTabFragment;
import doan.vinh.workit.ui.mainpage.statstab.StatsTabFragment;
import doan.vinh.workit.ui.mainpage.workouttab.WorkoutTabFragment;

/**
 * Created by Doand on 3/22/2018.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    String TAG = "MainPagerAdapter";
    private int mTabCount;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProfileTabFragment pFragment = ProfileTabFragment.newInstance();

                return pFragment;

            case 1:
                StatsTabFragment sFragment = StatsTabFragment.newInstance();

                return sFragment;

            case 2:
                ExerciseTabFragment eFragment = ExerciseTabFragment.newInstance();

                return eFragment;

            case 3:
                WorkoutTabFragment wFragment = WorkoutTabFragment.newInstance();

                return wFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }
}
