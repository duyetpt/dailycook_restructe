package org;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    private static final String STANDARD_TIME_ZONE = "Etc/GMT+0";
    public static final long A_MONTH_MILI = 1000l * 60l * 60l * 24l * 30l;

    // private static final String VN_TIME_ZONE = "Etc/GMT+7";
    public static long getCurrentGMTTime() {

        TimeZone timezone = TimeZone.getTimeZone(STANDARD_TIME_ZONE);
        long currentTime = System.currentTimeMillis();
        return currentTime - timezone.getOffset(currentTime);
    }

    public static String getDateyyyyMMdd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        return sdf.format(new Date(getCurrentGMTTime()));
    }

    public static String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }
}
