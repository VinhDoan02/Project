package doan.vinh.workit.di.Builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import doan.vinh.workit.ui.exercise.ExerciseActivity;
import doan.vinh.workit.ui.exercise.ExerciseActivityModule;
import doan.vinh.workit.ui.landing.LandingActivity;
import doan.vinh.workit.ui.landing.LandingActivityModule;
import doan.vinh.workit.ui.login.LoginActivity;
import doan.vinh.workit.ui.login.LoginActivityModule;
import doan.vinh.workit.ui.mainpage.MainActivityModule;
import doan.vinh.workit.ui.mainpage.exercisetab.ExerciseTabFragment;
import doan.vinh.workit.ui.mainpage.exercisetab.ExerciseTabFragmentModule;
import doan.vinh.workit.ui.mainpage.MainActivity;
import doan.vinh.workit.ui.mainpage.profiletab.ProfileTabFragment;
import doan.vinh.workit.ui.mainpage.profiletab.ProfileTabFragmentModule;
import doan.vinh.workit.ui.mainpage.statstab.StatsTabFragment;
import doan.vinh.workit.ui.mainpage.statstab.StatsTabFragmentModule;
import doan.vinh.workit.ui.mainpage.workouttab.WorkoutTabFragment;
import doan.vinh.workit.ui.mainpage.workouttab.WorkoutTabFragmentModule;

/**
 * Created by Doand on 3/15/2018.
 */
@Module
public abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = LandingActivityModule.class)
    abstract LandingActivity bindLandingActiviy();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActiviy();

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = ExerciseActivityModule.class)
    abstract ExerciseActivity bindExerciseActivity();





    @ContributesAndroidInjector(modules = ExerciseTabFragmentModule.class)
    abstract ExerciseTabFragment ExerciseTabFragmentInjector();


    @ContributesAndroidInjector(modules = StatsTabFragmentModule.class)
    abstract StatsTabFragment StatsTabFragmentInjector();


    @ContributesAndroidInjector(modules = ProfileTabFragmentModule.class)
    abstract ProfileTabFragment ProfileTabFragmentInjector();


    @ContributesAndroidInjector(modules = WorkoutTabFragmentModule.class)
    abstract WorkoutTabFragment WorkoutTabFragmentInjector();
}
