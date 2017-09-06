package com.hagai.realitest.rss.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.hagai.realitest.R;
import com.hagai.realitest.rss.presenter.RssPresenter;
import com.hagai.realitest.rss.view.cars.CarsFragment;
import com.hagai.realitest.rss.view.sports_n_culture.SportNCultureFragmet;


/**
 * Created by hagay on 9/5/2017.
 */

public class RssActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private RssPresenter mRssPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        //
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //
        mRssPresenter = new RssPresenter();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position) {
                case 0:
                    return CarsFragment.newInstance(mRssPresenter);
                case 1:
                    return SportNCultureFragmet.newInstance(mRssPresenter);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.cars);
                case 1:
                    return getString(R.string.sports_n_culture);

            }
            return null;
        }
    }
}