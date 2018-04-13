package doan.vinh.scansdstorage.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import doan.vinh.scansdstorage.ui.MainActivity;
import doan.vinh.scansdstorage.ui.MainActivityModule;

/**
 * Created by Doand on 4/12/2018.
 */
@Module
public abstract class ActivityBuilder {



    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();

}
