package com.hagai.realitest.utils.sharedPrefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.hagai.realitest.RealiApp;

/**
 * Created by hagay on 9/6/2017.
 */

public class SharedPrefsUtil
{
    private final String ID = "com.hagai.realitest";
    private final String KEY = "RssLabel";
    public String readLabel()
    {
        SharedPreferences prefs = RealiApp.getAppContext().getSharedPreferences(ID, Context.MODE_PRIVATE);
        String label = prefs.getString(KEY, null);
        return label;
    }
    public void writeLabel(String label)
    {
        SharedPreferences prefs = RealiApp.getAppContext().getSharedPreferences(ID, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY, label).apply();
    }
}
