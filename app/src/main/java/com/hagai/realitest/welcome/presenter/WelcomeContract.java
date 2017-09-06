package com.hagai.realitest.welcome.presenter;

import android.content.Context;

/**
 * Created by hagay on 9/4/2017.
 */

public interface WelcomeContract {

    interface ViewActions{
        void publishDate(String date);
        void publishLabel(String label);
        void changeView();
    }

    interface UserAction{
        void buttonPressed();
        void resume();
    }
    interface Model{
        interface DateInteractor{
            void calcCurrentDate();
        }
        interface DateInteractorCallback{
            void onCurrentDate(String date);
        }
        interface LabelIntercator{
            void findLabel();
        }
        interface LabelIntercatorCallback{
            void onLabel(String labal);
        }
    }
}
