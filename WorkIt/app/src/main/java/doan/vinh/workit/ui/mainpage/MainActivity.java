package doan.vinh.workit.ui.mainpage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import doan.vinh.workit.R;
import doan.vinh.workit.databinding.ActivityMainBinding;
import doan.vinh.workit.ui.base.BaseFragment;

public class MainActivity extends AppCompatActivity{
    MenuItem prevMenuItem;
    String TAG = "MainActivity";

    @Inject
    MainPagerAdapter mPagerAdapter;

    @Inject
    MainViewModel mMainViewModel;

    @BindView(R.id.viewpager) ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        setUpMenu();
    }

    public void setUpMenu()
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        mPagerAdapter.setCount(4);
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_profile:
                                //mViewPager.setCurrentItem(0);
                                mViewPager.setCurrentItem(0);
                                break;
                            case R.id.action_stats:
                                mViewPager.setCurrentItem(1);
                                break;
                            case R.id.action_exercise:
                             //   mViewPager.setCurrentItem(1);
                                mViewPager.setCurrentItem(2);
                                break;
                            case R.id.action_workout:
                             //   mViewPager.setCurrentItem(2);
                                mViewPager.setCurrentItem(3);
                                break;
                        }
                        return true;
                    }
                });

        bottomNavigationView.setSelectedItemId(R.id.action_exercise);

    }

}
