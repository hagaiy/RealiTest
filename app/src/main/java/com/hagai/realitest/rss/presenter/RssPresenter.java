package com.hagai.realitest.rss.presenter;

import android.net.Uri;

import com.hagai.realitest.rss.model.Categories;
import com.hagai.realitest.rss.model.GlobesRssItem;
import com.hagai.realitest.rss.model.RssInteractorImpl;
import com.hagai.realitest.utils.sharedPrefs.SharedPrefsUtil;

import java.util.List;

/**
 * Created by hagay on 9/5/2017.
 */

public class RssPresenter implements RssContract.UserActions, RssContract.Model.RssInteractorCallback {

    private RssContract.ViewActions mViewActions;
    private RssContract.Model.RssInteractor mRssInteractor;

    public RssPresenter() {
        mRssInteractor = new RssInteractorImpl(this);
    }

    @Override
    public void setRssView(RssContract.ViewActions viewActions, Categories category) {
        this.mViewActions = viewActions;
        mRssInteractor.setFeed(category);
    }

    @Override
    public void OnitemClick(GlobesRssItem rssItem) {
        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil();
        sharedPrefsUtil.writeLabel(rssItem.getTitle());
        //
        Uri uri = Uri.parse(rssItem.getLink());
        mViewActions.showExternalLink(uri);
    }

    @Override
    public void stop() {
        mRssInteractor.stop();
    }


    @Override
    public void onListChanged(List<GlobesRssItem> list) {
        mViewActions.publishList(list);
    }

    @Override
    public void onRefreshStatusChanged(boolean isRefreshing) {
        mViewActions.publishRequestStatusChanges(isRefreshing);
    }
}
