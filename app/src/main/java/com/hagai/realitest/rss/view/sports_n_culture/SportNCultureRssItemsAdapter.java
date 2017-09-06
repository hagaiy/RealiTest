package com.hagai.realitest.rss.view.sports_n_culture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hagai.realitest.R;
import com.hagai.realitest.rss.model.GlobesRssItem;
import com.hagai.realitest.utils.rss_parser.RssItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hagay on 9/6/2017.
 */

public class SportNCultureRssItemsAdapter extends RecyclerView.Adapter<SportNCultureRssItemsAdapter.ViewHolder> {

    private final List<GlobesRssItem> mItems = new ArrayList<>();
    private SportNCultureRssItemsAdapter.OnItemClickListener mListener;
    private final Context mContext;

    SportNCultureRssItemsAdapter(Context context) {
        mContext = context;
    }

    List<GlobesRssItem> getList() {
        return mItems;
    }

    void setListener(SportNCultureRssItemsAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    void setItems(List<GlobesRssItem> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    void insertItems(List<GlobesRssItem> items) {
        if (mItems.isEmpty()) {
            setItems(items);
        } else {
            int id = items.get(0).getFid();
            clearById(id);
            mItems.addAll(items);
            Collections.sort(mItems);
        }
        notifyDataSetChanged();
    }

    private void clearById(int id) {
        List<GlobesRssItem> tmpList = new ArrayList<>();
        for (GlobesRssItem item : mItems) {
            if (item.getFid() == id) {
                tmpList.add(item);
            }
        }
        mItems.removeAll(tmpList);
    }

    @Override
    public SportNCultureRssItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rss_item, parent, false);
        return new SportNCultureRssItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SportNCultureRssItemsAdapter.ViewHolder holder, int position) {
        if (mItems.size() <= position) {
            return;
        }
        final GlobesRssItem item = mItems.get(position);
        holder.textTitle.setText(item.getTitle());
        holder.textPubDate.setText(item.getPubDate());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onItemSelected(item);
            }
        });
        holder.itemView.setTag(item);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    interface OnItemClickListener {
        void onItemSelected(GlobesRssItem rssItem);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;

        TextView textPubDate;

        RelativeLayout llTextContainer;


        ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.titleTextView);
            textPubDate = itemView.findViewById(R.id.PubDateTextView);
            llTextContainer = itemView.findViewById(R.id.llTextContainer);
        }
    }
}