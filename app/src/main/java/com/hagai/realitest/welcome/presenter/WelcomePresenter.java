package com.hagai.realitest.welcome.presenter;

import com.hagai.realitest.welcome.model.DateInteractorImpl;
import com.hagai.realitest.welcome.model.LabelInteractorImpl;

/**
 * Created by hagay on 9/4/2017.
 */

public class WelcomePresenter implements WelcomeContract.UserAction, WelcomeContract.Model.DateInteractorCallback, WelcomeContract.Model.LabelIntercatorCallback {
    WelcomeContract.ViewActions mViewActions;
    DateInteractorImpl mDateInteractor;
    LabelInteractorImpl mLabelInteractor;

    public WelcomePresenter(WelcomeContract.ViewActions viewActions) {
        this.mViewActions = viewActions;
        //
        mDateInteractor = new DateInteractorImpl(this);
        mDateInteractor.calcCurrentDate();
        //
        mLabelInteractor = new LabelInteractorImpl(this);
    }

    @Override
    public void buttonPressed() {
        mViewActions.changeView();
    }

    @Override
    public void resume() {
        mLabelInteractor.findLabel();
    }


    @Override
    public void onCurrentDate(String date) {
        if (date != null) {
            mViewActions.publishDate(date);
        } else return;
    }

    @Override
    public void onLabel(String label) {
        if (label != null) {
            mViewActions.publishLabel(label);
        } else
            return;
    }
}
