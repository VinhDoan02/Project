package doan.vinh.scansdstorage.ui;

import dagger.Module;
import dagger.Provides;
import doan.vinh.scansdstorage.Utils.MainViewModelFactory;
import doan.vinh.scansdstorage.Utils.ScanUtils;

/**
 * Created by Doand on 4/12/2018.
 */
@Module
public class MainActivityModule {



    @Provides
    MainViewModelFactory providesMainViewModelFactory()
    {
        return new MainViewModelFactory(ScanUtils.getInstance());
    }

    @Provides
    MainViewModel providesMainViewModel()
    {
        return new MainViewModel(ScanUtils.getInstance());
    }

}
