package com.innovations.aguilar.pocketstrats.logging;

import android.util.Log;

// TODO: Decide which logging framework to use (probably setup log4j to forward to logcat somehow)
public class Logging {
    static final String Tag = "PocketStrats";
    public static int d(String msg) {
        return Log.d(Tag, msg);
    }
    public static int d(String msg, Throwable tr) {
        return Log.d(Tag, msg, tr);
    }
    public static int w(String msg) {
        return Log.w(Tag, msg);
    }
    public static int w(String msg, Throwable tr) {
        return Log.w(Tag, msg, tr);
    }
    public static int i(String msg) {
        return Log.i(Tag, msg);
    }
    public static int i(String msg, Throwable tr) {
        return Log.i(Tag, msg, tr);
    }
    public static int e(String msg) {
        return Log.e(Tag, msg);
    }
    public static int e(String msg, Throwable tr) {
        return Log.e(Tag, msg, tr);
    }
    public static int v(String msg) {
        return Log.v(Tag, msg);
    }
    public static int v(String msg, Throwable tr) {
        return Log.v(Tag, msg, tr);
    }
    public static int wtf(String msg) {
        return Log.wtf(Tag, msg);
    }
    public static int wtf(String msg, Throwable tr) {
        return Log.wtf(Tag, msg, tr);
    }
}
