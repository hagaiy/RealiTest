package com.hagai.realitest.rss.view.cars;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hagai.realitest.R;
import com.hagai.realitest.rss.model.GlobesRssItem;

import java.util.ArrayList;
import java.util.List;


public class CarsRssItemsAdapter extends RecyclerView.Adapter<CarsRssItemsAdapter.ViewHolder> {

    private final List<GlobesRssItem> mItems = new ArrayList<>();
    private OnItemClickListener mListener;
    private final Context mContext;

    CarsRssItemsAdapter(Context context) {
        mContext = context;
    }

    void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    void setItems(List<GlobesRssItem> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public CarsRssItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rss_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarsRssItemsAdapter.ViewHolder holder, int position) {
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
