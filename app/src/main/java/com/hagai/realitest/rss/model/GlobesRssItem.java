package com.hagai.realitest.rss.model;

import android.support.annotation.NonNull;

import com.hagai.realitest.utils.rss_parser.RssItem;


/**
 * Created by hagay on 9/6/2017.
 */

public class GlobesRssItem implements Comparable<GlobesRssItem> {

    private int mFid;
    private String mTitle;
    private String mLink;
    private String mDescription;
    private String mPubDate;

    public GlobesRssItem(RssItem rssItem) {
        mTitle = rssItem.getTitle();
        mLink = rssItem.getLink();
        mDescription = rssItem.getDescription();
        mPubDate = rssItem.getPublishDate();
    }

    public int getFid() {
        return mFid;
    }

    public void setFid(int fid) {
        this.mFid = fid;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setmubDate(String pubDate) {
        this.mPubDate = pubDate;
    }

    @Override
    public int compareTo(@NonNull GlobesRssItem item) {
        if (this.mFid == 2605 && item.getFid() == 3317)
            return -1;
        else if (this.mFid == 3317 && item.getFid() == 2605)
            return 1;
        else
            return 0;
    }
}
