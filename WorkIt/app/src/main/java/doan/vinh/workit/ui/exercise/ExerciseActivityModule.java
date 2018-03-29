package doan.vinh.workit.ui.exercise;

import dagger.Module;
import dagger.Provides;
import doan.vinh.workit.data.DataService;
import doan.vinh.workit.utils.rx.SchedulerProvider;

/**
 * Created by Doand on 3/24/2018.
 */

@Module
public class ExerciseActivityModule {


    @Provides
    ExerciseViewModel providesExerciseViewModel(SchedulerProvider schedulerProvider, DataService dataService)
    {
        return new ExerciseViewModel(schedulerProvider,dataService);
    }
}
