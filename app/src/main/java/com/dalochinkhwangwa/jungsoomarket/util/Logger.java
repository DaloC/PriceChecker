package com.dalochinkhwangwa.jungsoomarket.util;

import android.util.Log;

public class Logger {
    private static final String JIKAN_TAG = "TAG_JUNGSOO";

    public static void logError(String message){
        Log.e(JIKAN_TAG, message);
    }

    public static void logDebug(String message){
        Log.d(JIKAN_TAG, message);
    }
}
