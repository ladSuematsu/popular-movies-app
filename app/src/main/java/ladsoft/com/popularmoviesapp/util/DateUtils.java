package ladsoft.com.popularmoviesapp.util;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static final String ISO_8601_COMPLIANT_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    private static final String LOG_TAG = DateUtils.class.getSimpleName();

    public static long getSystemCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    public static String getCurrentFormattedDate(String template) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        return sdf.format(calendar.getTime());
    }

    public static String getFormattedDate(long timestamp, String template, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        if(timeZone != null) {
            calendar.setTimeZone(timeZone);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(template);
        return sdf.format(calendar.getTime());
    }

    public static String getFormattedDate(String time, String format) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_COMPLIANT_DATETIME_PATTERN);
            Date date = sdf.parse(time);

            SimpleDateFormat sdfAux = new SimpleDateFormat(format);
            result = sdfAux.format(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Error while parsing the data string. Is it ISO-8601 compliant?", e);
        }

        return result;
    }

    public static Calendar getCalendar(String time, String format) {
        Calendar calendar = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time);

            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Error while parsing the data string. Is it ISO-8601 compliant?", e);
        }

        return calendar;
    }
}
