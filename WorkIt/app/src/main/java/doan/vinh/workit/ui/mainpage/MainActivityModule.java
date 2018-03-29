package doan.vinh.workit.ui.mainpage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Doand on 3/22/2018.
 */

@Module
public class MainActivityModule {

    @Provides
    MainPagerAdapter provideMainPageAdapter(MainActivity activity)
    {
        return new MainPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    MainViewModel provideMainViewModel(){return new MainViewModel();}




}
