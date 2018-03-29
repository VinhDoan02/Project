package doan.vinh.workit.ui.mainpage.exercisetab;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import doan.vinh.workit.data.DataService;
import doan.vinh.workit.data.model.Exercise;
import doan.vinh.workit.utils.rx.SchedulerProvider;

/**
 * Created by Doand on 3/22/2018.
 */

@Module
public class ExerciseTabFragmentModule {

    @Provides
    ExerciseTabViewModel provideExerciseViewModel(SchedulerProvider schedulerProvider, DataService dataService){
        return new ExerciseTabViewModel(schedulerProvider,dataService);}

    @Provides
    ExerciseTabAdapter provideExerciceAdapter(ExerciseTabFragment eFragment)
    {
        return new ExerciseTabAdapter(eFragment.getActivity(),new ArrayList<Exercise>());
    }
}

