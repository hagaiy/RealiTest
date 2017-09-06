package com.hagai.realitest;

import android.app.Application;
import android.content.Context;

/**
 * Created by hagay on 9/6/2017.
 */

public class RealiApp extends Application
{
    public static final int CARS_FEED_ID = 3220;
    public static final int SPORTS_FEED_ID = 2605;
    public static final int CULTURE_FEED_ID = 3317;

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        RealiApp.mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return RealiApp.mContext;
    }
}
