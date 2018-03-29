package doan.vinh.workit.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import doan.vinh.workit.data.DataService;
import doan.vinh.workit.data.RealmDataService;
import doan.vinh.workit.utils.rx.SchedulerProvider;

/**
 * Created by Doand on 3/15/2018.
 */
@Module
public class AppModule {

    @Provides
    SchedulerProvider provideSchedulerProvider(){return new SchedulerProvider();}

    @Provides
    @Singleton
    DataService provideDataService(){return new RealmDataService();}

}
