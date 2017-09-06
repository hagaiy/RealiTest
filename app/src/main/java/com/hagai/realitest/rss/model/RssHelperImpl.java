package com.hagai.realitest.rss.model;

import com.hagai.realitest.rss.presenter.RssContract;
import com.hagai.realitest.utils.retrofit.RetrofitClient;
import com.hagai.realitest.utils.retrofit.RssInterface;
import com.hagai.realitest.utils.rss_parser.RssFeed;
import com.hagai.realitest.utils.rss_parser.RssItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hagay on 9/5/2017.
 */

public class RssHelperImpl implements RssContract.Model.RssHelper {

    private RssInterface mRetroRss;
    RssContract.Model.RssRssHelperCallback mRssFetcherResultListener;

    public RssHelperImpl(RssContract.Model.RssRssHelperCallback rssFetcherResultListener) {
        mRetroRss = RetrofitClient.getClient().create(RssInterface.class);
        this.mRssFetcherResultListener = rssFetcherResultListener;
    }

    public void fetchRssById(final int id, final long timeStamp) {
        Call<RssFeed> call = mRetroRss.getRssFeeds(id);
        call.enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                if (response.isSuccessful()) {
                    List<GlobesRssItem> conervtedItems = convertRssItems2GlobesRssItems(response.body().getItems(), id);
                    mRssFetcherResultListener.onSuccess(conervtedItems, timeStamp);
                } else {
                    mRssFetcherResultListener.onError(timeStamp);
                }
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {
                mRssFetcherResultListener.onError(timeStamp);
            }
        });
    }

    private List<GlobesRssItem> convertRssItems2GlobesRssItems(List<RssItem> items, int feedId) {
        List<GlobesRssItem> list = new ArrayList<>();
        for (RssItem item : items) {
            GlobesRssItem globesRssItem = new GlobesRssItem(item);
            globesRssItem.setFid(feedId);
            list.add(globesRssItem);
        }
        return list;
    }
}
