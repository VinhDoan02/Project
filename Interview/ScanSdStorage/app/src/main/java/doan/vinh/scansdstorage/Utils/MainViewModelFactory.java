package doan.vinh.scansdstorage.Utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import doan.vinh.scansdstorage.ui.MainViewModel;

/**
 * Created by Doand on 4/12/2018.
 */

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final ScanUtils scanUtils;

    @Inject
    public MainViewModelFactory(ScanUtils scanUtils)
    {
        this.scanUtils = scanUtils;
    }

    @NonNull
    @Override
    public MainViewModel create(Class modelClass) {
        return new MainViewModel(scanUtils);
    }


}
