package doan.vinh.workit.ui.base;

import android.arch.lifecycle.ViewModel;

import doan.vinh.workit.data.DataService;
import doan.vinh.workit.utils.rx.SchedulerProvider;

/**
 * Created by Doand on 3/26/2018.
 */

public class BaseViewModel extends ViewModel {

    private final SchedulerProvider schedulerProvider;
    private final DataService dataService;


    public BaseViewModel(SchedulerProvider schedulerProvider,DataService dataService)
    {
        this.schedulerProvider = schedulerProvider;
        this.dataService = dataService;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public DataService getDataService() {
        return dataService;
    }

}
