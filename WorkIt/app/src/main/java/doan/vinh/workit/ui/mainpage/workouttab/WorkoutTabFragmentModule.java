package doan.vinh.workit.ui.mainpage.workouttab;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Doand on 3/23/2018.
 */

@Module
public class WorkoutTabFragmentModule {

    @Provides
    WorkoutTabViewModel provideWorkoutViewModel(){return new WorkoutTabViewModel();}
}
