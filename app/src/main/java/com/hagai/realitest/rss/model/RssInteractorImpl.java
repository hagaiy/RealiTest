package com.hagai.realitest.rss.model;

import android.util.Log;

import com.hagai.realitest.RealiApp;
import com.hagai.realitest.rss.presenter.RssContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hagay on 9/5/2017.
 */

public class RssInteractorImpl implements RssContract.Model.RssRssHelperCallback,RssContract.Model.RssInteractor{
    private Disposable mDisposable;
    private RssContract.Model.RssHelper mRssHelper;
    private RssContract.Model.RssInteractorCallback mRssInteractorCallback;
    //
    private Categories mCategory = null;
    //
    private List<Long> mRequsetsTracker;

    public RssInteractorImpl(RssContract.Model.RssInteractorCallback rssInteractorCallback) {
        this.mRssInteractorCallback = rssInteractorCallback;
        mRequsetsTracker = new ArrayList<>();
        mRssHelper = new RssHelperImpl(this);
    }
    @Override
    public void setFeed(Categories category) {
        this.mCategory = category;
        deactivateRxTimer();
        activateRxTimer();
    }

    private DisposableObserver<Long> getObserver() {
        return new DisposableObserver<Long>() {

            @Override
            public void onNext(Long aLong) {
                fireRequset();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("RssInteractorImpl", " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void fireRequset() {
        switch (mCategory) {
            case CARS: {
                long timeStamp = System.currentTimeMillis();
                mRssHelper.fetchRssById(RealiApp.CARS_FEED_ID, timeStamp);
                mRequsetsTracker.add(timeStamp);
                mRssInteractorCallback.onRefreshStatusChanged(true);
                break;
            }
            case SPORTS_N_CULTURE: {
                long timeStamp = System.currentTimeMillis();
                mRssHelper.fetchRssById(RealiApp.SPORTS_FEED_ID, timeStamp);
                mRequsetsTracker.add(timeStamp);
                mRssInteractorCallback.onRefreshStatusChanged(true);
                //
                timeStamp = System.currentTimeMillis();
                mRssHelper.fetchRssById(RealiApp.CULTURE_FEED_ID, timeStamp);
                mRequsetsTracker.add(timeStamp);
                mRssInteractorCallback.onRefreshStatusChanged(true);
                break;
            }

            default:
                return;
        }
    }

    private void activateRxTimer() {
        mDisposable = getObservable().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(getObserver());
    }

    private Observable<Long> getObservable() {
        return Observable.interval(0, 5, TimeUnit.SECONDS);
    }

    private void deactivateRxTimer() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    @Override
    public void onSuccess(List<GlobesRssItem> items, long timeStamp) {
        manageRequsetsQueue(timeStamp);
        mRssInteractorCallback.onListChanged(items);
    }

    @Override
    public void onError(long timeStamp) {
        manageRequsetsQueue(timeStamp);
    }

    private void manageRequsetsQueue(long timeStamp) {
        if (mRequsetsTracker.contains(timeStamp)) {
            mRequsetsTracker.remove(mRequsetsTracker.indexOf(timeStamp));
        }
        if (mRequsetsTracker.isEmpty()) {
            mRssInteractorCallback.onRefreshStatusChanged(false);
        }
    }
    @Override
    public void stop() {
        deactivateRxTimer();
    }
}
