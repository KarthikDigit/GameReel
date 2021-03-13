package com.instaclone.utils;

import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utility {

    private static final String TAG = "Utility";

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            Log.e(TAG, "isValidFormat: " + ex.getMessage());
        }
        return date != null;
    }

    public static String getDateInString(Date date, String format) {

        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    public static Date getStringInDate(String s, String format) {

        SimpleDateFormat fmt = new SimpleDateFormat(format);
        try {
            return fmt.parse(s);
        } catch (ParseException e) {

            return null;
        }
    }


}
