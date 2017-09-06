package com.hagai.realitest.welcome.model;


import com.hagai.realitest.utils.sharedPrefs.SharedPrefsUtil;
import com.hagai.realitest.welcome.presenter.WelcomeContract;

/**
 * Created by hagay on 9/4/2017.
 */

public class LabelInteractorImpl implements WelcomeContract.Model.LabelIntercator {

    WelcomeContract.Model.LabelIntercatorCallback mCallback;

    public LabelInteractorImpl(WelcomeContract.Model.LabelIntercatorCallback callback)
    {
        this.mCallback = callback;
    }
    @Override
    public void findLabel()
    {
        SharedPrefsUtil sharedPrefsUtil = new  SharedPrefsUtil();
        String label  = sharedPrefsUtil.readLabel();
        mCallback.onLabel(label);
    }
}
