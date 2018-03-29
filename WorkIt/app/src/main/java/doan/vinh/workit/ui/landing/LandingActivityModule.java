package doan.vinh.workit.ui.landing;

import dagger.Module;
import dagger.Provides;
import doan.vinh.workit.data.DataService;
import doan.vinh.workit.utils.rx.SchedulerProvider;

/**
 * Created by Doand on 3/15/2018.
 */
@Module
public class LandingActivityModule {


    @Provides
    LandingViewModel providesLandingViewModel(SchedulerProvider schedulerProvider, DataService dataService)
    {
        return new LandingViewModel(schedulerProvider,dataService);
    }
}
