package com.hagai.realitest.rss.view.cars;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hagai.realitest.R;
import com.hagai.realitest.rss.model.Categories;
import com.hagai.realitest.rss.model.GlobesRssItem;
import com.hagai.realitest.rss.presenter.RssContract;

import java.util.List;

/**
 * Created by hagay on 9/5/2017.
 */

public class CarsFragment extends Fragment implements RssContract.ViewActions, CarsRssItemsAdapter.OnItemClickListener {

    private CarsRssItemsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RssContract.UserActions mRssPresenter;
    private TextView mRefreshingTextView;

    public CarsFragment(RssContract.UserActions rssPresenter) {
        this.mRssPresenter = rssPresenter;
    }

    public static CarsFragment newInstance(RssContract.UserActions rssPresenter) {
        CarsFragment fragment = new CarsFragment(rssPresenter);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rss, container, false);
        mAdapter = new CarsRssItemsAdapter(getActivity());
        mAdapter.setListener(this);
        //
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRefreshingTextView = rootView.findViewById(R.id.refreshingTextView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mRssPresenter.setRssView(this, Categories.CARS);
        }
    }

    @Override
    public void publishList(List<GlobesRssItem> list) {
        mAdapter.setItems(list);
    }

    @Override
    public void publishRequestStatusChanges(boolean isRefreshing) {
        if (mRefreshingTextView != null) {
            if (isRefreshing)
                mRefreshingTextView.setVisibility(View.VISIBLE);
            else mRefreshingTextView.setVisibility(View.INVISIBLE);
        }
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

    @Override
    public void onItemSelected(GlobesRssItem rssItem) {
        mRssPresenter.OnitemClick(rssItem);
    }
}
