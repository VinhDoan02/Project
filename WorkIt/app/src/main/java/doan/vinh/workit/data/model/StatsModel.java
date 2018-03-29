package doan.vinh.workit.data.model;

import android.util.Log;

import doan.vinh.workit.data.model.RealmObject.RealmExercise;
import doan.vinh.workit.data.model.RealmObject.RealmRepetition;
import doan.vinh.workit.utils.AppUtils;
import io.realm.RealmList;

/**
 * Created by Doand on 3/27/2018.
 */

public class StatsModel {
    String name;
    boolean isCountable;
    Long durationLastWeek;

    String TAG = "Stats model";

    long lastRepetitionDuration;
    int lastRepetitionCount;

    String lastRepetition;
    String statsTotal;
    String lastDayHistory;
    String last7DaysHistory;

    public String toString()
    {
        return name + " : " + lastRepetitionDuration + "\n "+lastRepetitionCount ;
    }

    public String getLastRepetition()
    {
        return lastRepetition;
    }

    public String getTotalStats()
    {
        return statsTotal;
    }

    public String getName()
    {
        return name;
    }

    public String getLastDayHistory()
    {
        return lastDayHistory;
    }

    public String getLast7DayHistory()
    {
        return last7DaysHistory;
    }

    public void initModel(RealmExercise exercise)
    {
        name = exercise.getName();
        isCountable = exercise.isCountable();
        int size = exercise.getRepetitionList().size();

       // Log.d("stats model", name + " : " + size);
        if(exercise.getRepetitionList().size() > 0)
        {
            setlastRepetition(exercise.getRepetitionList().get(0));
            computeStats(exercise.getRepetitionList());
        }else
        {

        }

    }

    public void computeStats(RealmList<RealmRepetition> repetitionList)
    {

        //in milliseconds
        long days = 1000*60*60*24;
        long sevenDays = days*7;

        long currentTime = System.currentTimeMillis();
        long last24hCutout = currentTime -days;
        long last7DaysCutout = currentTime - sevenDays;

        int lastDayQte = 0;
        int last7DaysQte = 0;

        long lastDayDuration = 0;
        long last7DaysDuration =0 ;

        long totalDuration = 0;
        long totalQte = 0;


        int listSize = repetitionList.size();
        Log.d(TAG,"repetition size " + listSize);
        for (int i = 0;i < listSize;i++)
        {
            long date = repetitionList.get(i).getDate();

                Log.d(TAG,"repetition id " + repetitionList.get(i).printOut());

                if(date >= last24hCutout)
                {
                    //last24h stats
                    if(isCountable)
                    {
                       lastDayQte = lastDayQte +  repetitionList.get(i).getQte();
                    }

                    lastDayDuration = lastDayDuration + repetitionList.get(i).getDuration();
                }else if(date < last7DaysCutout)
                {
                    // total stats
                    if(isCountable)
                    {
                        totalQte = totalQte +  repetitionList.get(i).getQte();
                    }
                        totalDuration = totalDuration + repetitionList.get(i).getDuration();
                }else
                {
                    //last 7days stats
                    if(isCountable)
                    {
                        last7DaysQte = last7DaysQte +  repetitionList.get(i).getQte();
                    }
                    last7DaysDuration = last7DaysDuration + repetitionList.get(i).getDuration();
                }


        }

        last7DaysDuration = last7DaysDuration + lastDayDuration;
        totalDuration = totalDuration + last7DaysDuration;

        if(isCountable)
        {
            last7DaysQte = last7DaysQte + lastDayQte;
            totalQte = totalQte + last7DaysQte;
        }

        Log.d(TAG,"1day " + lastDayDuration + " qte " + lastDayQte);
        Log.d(TAG,"7day " + last7DaysDuration + " qte " + last7DaysQte);
        Log.d(TAG,"total " + totalDuration + " qte " + totalQte);

        setLastDaysStats(lastDayDuration,lastDayQte);
        setLast7days(last7DaysDuration,lastDayQte);
        setTotalStats(totalDuration,totalQte);
    }


    public void setTotalStats(long duration,long qte)
    {
        StringBuilder totalStats = new StringBuilder();
        String time = AppUtils.convertTimeToStringFormat(duration);
        totalStats.append("Total time spent ");
        totalStats.append(": " + time);

        if(isCountable)
        {
            totalStats.append(" qte: ");
            totalStats.append(qte);
        }

        statsTotal = totalStats.toString();
    }


    public void setLast7days(long duration,long qte)
    {
        StringBuilder totalStats = new StringBuilder();
        String time = AppUtils.convertTimeToStringFormat(duration);
        totalStats.append("Last 7 Days, ");
        totalStats.append("time spent: " + time);

        if(isCountable)
        {
            totalStats.append(" qte: ");
            totalStats.append(qte);
        }

        last7DaysHistory = totalStats.toString();
    }

    public void setLastDaysStats(long duration,long qte)
    {
        StringBuilder totalStats = new StringBuilder();
        String time = AppUtils.convertTimeToStringFormat(duration);
        totalStats.append("Last 24h, ");
        totalStats.append("time spent : " + time);

        if(isCountable)
        {
            totalStats.append(" qte:");
            totalStats.append(qte);
        }

        lastDayHistory = totalStats.toString();
    }


    public void setlastRepetition(RealmRepetition repetition)
    {
        if(isCountable)
        {
            lastRepetitionCount = repetition.getQte();
        }

        lastRepetitionDuration = repetition.getDuration();

        lastRepetition = "Last rep duration: " + AppUtils.convertTimeToStringFormat(lastRepetitionDuration) + " qte: "+lastRepetitionCount ;
    }
}
