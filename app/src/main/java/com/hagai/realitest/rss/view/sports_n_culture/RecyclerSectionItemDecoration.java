package com.hagai.realitest.rss.view.sports_n_culture;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hagai.realitest.R;

/**
 * Created by hagay on 9/6/2017.
 */

public class RecyclerSectionItemDecoration extends RecyclerView.ItemDecoration {

    private final int mHeaderOffset;
    private final boolean mIsSticky;
    private final SectionCallback mSectionCallback;

    private View mHeaderView;
    private TextView mHeaderTextView;

    public RecyclerSectionItemDecoration(int headerHeight, boolean sticky, @NonNull SectionCallback sectionCallback) {
        mHeaderOffset = headerHeight;
        this.mIsSticky = sticky;
        this.mSectionCallback = sectionCallback;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        if (mSectionCallback.isSection(pos)) {
            outRect.top = mHeaderOffset;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (mHeaderView == null) {
            mHeaderView = inflateHeaderView(parent);
            mHeaderTextView = (TextView) mHeaderView.findViewById(R.id.list_item_section_text);
            fixLayoutSize(mHeaderView, parent);
        }

        CharSequence previousHeader = "";
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final int position = parent.getChildAdapterPosition(child);

            CharSequence title = mSectionCallback.getSectionHeader(position);
            mHeaderTextView.setText(title);
            if (!previousHeader.equals(title) || mSectionCallback.isSection(position)) {
                drawHeader(c, child, mHeaderView);
                previousHeader = title;
            }
        }
    }

    private void drawHeader(Canvas c, View child, View headerView) {
        c.save();
        if (mIsSticky) {
            c.translate(0, Math.max(0, child.getTop() - headerView.getHeight()));
        } else {
            c.translate(0, child.getTop() - headerView.getHeight());
        }
        headerView.draw(c);
        c.restore();
    }

    private View inflateHeaderView(RecyclerView parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_section_header, parent, false);
    }

    private void fixLayoutSize(View view, RecyclerView parent) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(),
                View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(),
                View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(),
                view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(),
                view.getLayoutParams().height);

        view.measure(childWidth, childHeight);

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public interface SectionCallback {

        boolean isSection(int position);
        CharSequence getSectionHeader(int position);
    }
}