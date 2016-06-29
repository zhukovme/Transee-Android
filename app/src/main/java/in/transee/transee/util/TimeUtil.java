package in.transee.transee.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.transee.transee.R;
import timber.log.Timber;

/**
 * Created by Zhukov Michael.
 */
public class TimeUtil {

    public static final String TIME_PATTERN = "kk:mm";

    public static String getMinuteDiff(Context context, String time) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).parse(time);
        } catch (ParseException e) {
            Timber.d(e, "Error parsing time");
        }

        long diff = date.getTime() - System.currentTimeMillis();

        long diffMinutes = diff / (1000 * 60) % 60;
        long diffHours = diff / (1000 * 60 * 60) % 24;

        if (diffHours != 0 && diffMinutes != 0) {
            diffHours += 23;
            diffMinutes += 60;
        } else if (diffMinutes == 0) {
            diffHours = diffHours == 0 ? diffHours : diffHours + 24;
        }

        String afterStr = context.getString(R.string.in_a);
        String hourStr = context.getString(R.string.hour_short);
        String minuteStr = context.getString(R.string.minute_short);

        String diffString;
        if (diffHours == 0 && diffMinutes == 0) {
            diffString = context.getString(R.string.now);
        } else if (diffHours == 0) {
            diffString = afterStr + " " + diffMinutes + " " + minuteStr;
        } else if (diffMinutes == 0){
            diffString = afterStr + " " + diffHours + " " + hourStr;
        } else {
            diffString = afterStr + " " + diffHours + " " + hourStr + " " + diffMinutes + " " +
                    minuteStr;
        }
        return diffString;
    }
}
