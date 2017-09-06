package com.hagai.realitest.rss.presenter;

import android.net.Uri;

import com.hagai.realitest.rss.model.Categories;
import com.hagai.realitest.rss.model.GlobesRssItem;
import com.hagai.realitest.utils.rss_parser.RssItem;

import java.util.List;

/**
 * Created by hagay on 9/5/2017.
 */

public interface RssContract {
    interface UserActions{
        void setRssView(ViewActions view, Categories category);
        void OnitemClick(GlobesRssItem rssItem);
        void stop();
    }
    interface ViewActions{
        void publishList(List<GlobesRssItem> list);
        void publishRequestStatusChanges(boolean isRefreshing);
        void showExternalLink(Uri uri);
    }
    interface Model{
        interface RssInteractorCallback {
            void onListChanged(List<GlobesRssItem> list);
            void onRefreshStatusChanged(boolean isRefreshing);
        }
        interface RssInteractor {
            void setFeed(Categories category);
            void stop();
        }
        interface RssRssHelperCallback {
            void onSuccess(List<GlobesRssItem> items, long timeStamp);
            void onError(long timeStamp);
        }
        interface RssHelper {
            void fetchRssById(final int id, final long timeStamp);
        }
    }
}
