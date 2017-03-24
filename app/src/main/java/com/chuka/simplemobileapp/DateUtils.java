package com.chuka.simplemobileapp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Pattern;

/**
 * Created by Clement on 3/24/17.
 */

public class DateUtils {

    private static final String TAG = DateTimeUtils.class.getSimpleName();

    /**
     * Returns the duration passed since this time
     * @param time
     * @return Duration since time specified
     */
    public static String getDurationSince(DateTime time) {
        String duration = null;
        DateTime now = new DateTime();

        // Today
        if (now.getDayOfMonth() == time.getDayOfMonth()) {

            // Same hour
            if (now.getHourOfDay() == time.getHourOfDay()) {

                // Started moments ago
                int minutesPassed = Minutes.minutesBetween(time, now).getMinutes();
                if (minutesPassed == 0) {
                    duration = "Just Now";
                } else {
                    duration = minutesPassed + " mins ago";
                }
            } else {
                duration = Hours.hoursBetween(time, now).getHours() + " hrs ago";
            }
        } else {
            duration = getFormattedTime(time);
        }

        return duration;
    }

    public static String getFormattedTime(DateTime time) {
        String formattedTime = null;
        DateTime now = new DateTime();

        // Today
        if (now.getDayOfMonth() == time.getDayOfMonth()) {
            formattedTime = "Today, " + get12hourTime(time);
        } else if (now.getDayOfMonth() - 1 == time.getDayOfMonth()) {
            // Yesterday
            formattedTime = "Yesterday, " + get12hourTime(time);
        } else {
            formattedTime = time.monthOfYear().getAsShortText() + " " + time.getDayOfMonth() + ", " + get12hourTime(time);
        }

        return formattedTime;
    }

    private static String get12hourTime(DateTime time) {
        DateTimeFormatter builder = DateTimeFormat.forPattern("hh:mm a");
        String b = builder.print(time).toLowerCase();
        Pattern p = Pattern.compile("0.*");
        if (p.matcher(b).matches()) {
            return b.substring(1);
        }
        return b;
    }
}
