package com.hagai.realitest.utils.retrofit;

import com.hagai.realitest.utils.rss_parser.RssFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hagay on 9/5/2017.
 */
public interface RssInterface {
    @GET("webservice/rss/rssfeeder.asmx/FeederNode?")
    Call<RssFeed> getRssFeeds(@Query(value = "iID", encoded = true) int id);


}
