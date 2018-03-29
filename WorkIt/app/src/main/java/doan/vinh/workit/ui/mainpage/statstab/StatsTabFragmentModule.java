package doan.vinh.workit.ui.mainpage.statstab;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import doan.vinh.workit.data.DataService;
import doan.vinh.workit.utils.rx.SchedulerProvider;

/**
 * Created by Doand on 3/27/2018.
 */

@Module
public class StatsTabFragmentModule {

@Provides
    StatsTabViewModel provideStatsViewModel(SchedulerProvider schedulerProvider, DataService dataService)
    {
    return new StatsTabViewModel(schedulerProvider,dataService);
    }

    @Provides
    StatsTabAdapter providesStatsTabAdapter()
    {
        return new StatsTabAdapter(new ArrayList<>());
    }
}
