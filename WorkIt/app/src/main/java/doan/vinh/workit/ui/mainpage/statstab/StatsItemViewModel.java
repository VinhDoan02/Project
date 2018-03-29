package doan.vinh.workit.ui.mainpage.statstab;

import android.databinding.Observable;
import android.databinding.ObservableField;

import doan.vinh.workit.data.model.StatsModel;

/**
 * Created by Doand on 3/27/2018.
 */

public class StatsItemViewModel  {

    public final ObservableField<String> lastRepetition;

    public final ObservableField<String> exerciseName;

    public final ObservableField<String> statsLastDay;

    public final ObservableField<String> statsLast7Days;

    public final ObservableField<String> statsTotal;

    public StatsItemViewModel(StatsModel model)
    {

        exerciseName = new ObservableField<>((model.getName()));
        lastRepetition = new ObservableField<>((model.getLastRepetition()));
        statsLastDay = new ObservableField<>((model.getLastDayHistory()));
        statsLast7Days = new ObservableField<>((model.getLast7DayHistory()));
        statsTotal = new ObservableField<>((model.getTotalStats()));
    }


}
