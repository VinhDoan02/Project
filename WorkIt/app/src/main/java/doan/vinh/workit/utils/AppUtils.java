package doan.vinh.workit.utils;

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Doand on 3/15/2018.
 */

public class AppUtils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

  /* public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }*/



    /**
     *
     * @param duration in seconds
     * @return String under format "nb Days, nb H, nb Min, nb Sec"
     */
    public static String convertTimeToStringFormat(long duration)
    {
      //  Log.d("AppUtils ", "duration " + duration);
        long minutes = 60;
        long hours = minutes*60;
        long days = hours * 24;

        long remainderSeconds = duration%minutes;
        duration = duration - remainderSeconds;
        long remainderMinutes = duration%hours;
        duration = duration - remainderMinutes*minutes;
        long remainderHours = duration%days;
        duration = duration - remainderHours * hours;
        long remainderDays = duration/days;

        StringBuilder conversion = new StringBuilder();
        if(remainderDays > 0)
        {
            conversion.append(remainderDays + " days, ");
        }

        if(remainderHours > 0)
        {
            conversion.append(remainderHours + " h, ");
        }

        if(remainderMinutes > 0)
        {
            conversion.append(remainderMinutes + " min, ");
        }

        conversion.append(remainderSeconds + " sec");

       // Log.d("AppUtils ", "conversion " + conversion.toString());
        return conversion.toString();

    }
}
