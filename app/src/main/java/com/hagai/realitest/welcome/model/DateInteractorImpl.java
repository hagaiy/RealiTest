package com.hagai.realitest.welcome.model;

import com.hagai.realitest.welcome.presenter.WelcomeContract;

import java.text.SimpleDateFormat;

/**
 * Created by hagay on 9/4/2017.
 */

public class DateInteractorImpl implements WelcomeContract.Model.DateInteractor {
    WelcomeContract.Model.DateInteractorCallback mCallback;

    public DateInteractorImpl(WelcomeContract.Model.DateInteractorCallback callback)
    {
        this.mCallback = callback;
    }
    @Override
    public void calcCurrentDate()
    {
        long timeInMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        final String pubDateString = sdf.format(timeInMillis);
        mCallback.onCurrentDate(pubDateString);
    }
}
