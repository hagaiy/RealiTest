package com.hagai.realitest.rss.view.sports_n_culture;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hagai.realitest.R;
import com.hagai.realitest.RealiApp;
import com.hagai.realitest.rss.model.Categories;
import com.hagai.realitest.rss.model.GlobesRssItem;
import com.hagai.realitest.rss.presenter.RssContract;


import java.util.List;

/**
 * Created by hagay on 9/6/2017.
 */

public class SportNCultureFragmet extends Fragment implements RssContract.ViewActions, SportNCultureRssItemsAdapter.OnItemClickListener {

    private SportNCultureRssItemsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RssContract.UserActions mRssPresenter;
    private TextView mRefreshingTextView;

    public SportNCultureFragmet(RssContract.UserActions rssPresenter) {
        this.mRssPresenter = rssPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rss, container, false);
        mRefreshingTextView = rootView.findViewById(R.id.refreshingTextView);
        mAdapter = new SportNCultureRssItemsAdapter(getActivity());
        //
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerSectionItemDecoration sectionItemDecoration = new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                true,
                getSectionCallback(mAdapter.getList()));
        mRecyclerView.addItemDecoration(sectionItemDecoration);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public static SportNCultureFragmet newInstance(RssContract.UserActions rssPresenter) {
        SportNCultureFragmet fragment = new SportNCultureFragmet(rssPresenter);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("CarsFragment", "setUserVisibleHint " + isVisibleToUser);
        if (isVisibleToUser) {
            mRssPresenter.setRssView(this, Categories.SPORTS_N_CULTURE);
        }
    }

    @Override
    public void publishList(List<GlobesRssItem> list) {
        mAdapter.insertItems(list);
    }

    @Override
    public void publishRequestStatusChanges(boolean isRefreshing) {
        if (isRefreshing)
            mRefreshingTextView.setVisibility(View.VISIBLE);
        else mRefreshingTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showExternalLink(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        mRssPresenter.stop();
    }

    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<GlobesRssItem> items) {
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0
                        || items.get(position).getFid() != items.get(position - 1).getFid();
            }

            @Override
            public CharSequence getSectionHeader(int position) {
                if (items.get(position).getFid() == RealiApp.SPORTS_FEED_ID) {
                    return getString(R.string.sports);
                } else if (items.get(position).getFid() == RealiApp.CULTURE_FEED_ID) {
                    return getString(R.string.culture);
                } else
                    return "";
            }
        };
    }

    @Override
    public void onItemSelected(GlobesRssItem rssItem) {
        mRssPresenter.OnitemClick(rssItem);
    }
}