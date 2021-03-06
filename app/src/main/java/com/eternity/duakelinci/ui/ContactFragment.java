package com.eternity.duakelinci.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.eternity.duakelinci.InnerFragment.MyFragmentPagerAdapter;
import com.eternity.duakelinci.InnerFragment.Tab1Fragment;
import com.eternity.duakelinci.InnerFragment.Tab2Fragment;
import com.eternity.duakelinci.InnerFragment.Tab3Fragment;
import com.eternity.duakelinci.InnerFragment.Tab4Fragment;
import com.eternity.duakelinci.R;

import java.util.List;
import java.util.Vector;

/**
 * Created by WIN7 on 2/13/2016.
 */
public class ContactFragment extends Fragment implements TabHost.OnTabChangeListener,
        ViewPager.OnPageChangeListener {
    private TabHost tabHost;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myViewPagerAdapter;
    int i = 0;
    View v;

    public ContactFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.tabs_viewpager_layout, container, false);
        i++;

        // init tabhost
        this.initializeTabHost(savedInstanceState);

        // init ViewPager
        this.initializeViewPager();
        this.onPageSelected(3);

		/*
		 * part1*****************************************************************
		 * ********
		 */
        return v;
    }
    // fake content for tabhost
    class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }
    private void initializeViewPager() {
        List<Fragment> fragments = new Vector<Fragment>();
        //edit isi fragment
        fragments.add(new Tab1Fragment());
        fragments.add(new Tab2Fragment());
        fragments.add(new Tab3Fragment());
        fragments.add(new Tab4Fragment());


        this.myViewPagerAdapter = new MyFragmentPagerAdapter(
                getChildFragmentManager(), fragments);
        this.viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myViewPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);

    }
    private void initializeTabHost(Bundle savedInstanceState) {
        tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();
        //edit tabs
        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("Tab " + 1);
        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.tab1_selector) );
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Tab " + 2);
        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.tab2_selector) );
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Tab " + 3);
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.tab3_selector));
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Tab " + 4);
        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.tab4_selector) );
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);
        tabHost.setOnTabChangedListener(this);

    }



    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {this.tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);

        HorizontalScrollView hScrollView = (HorizontalScrollView) v
                .findViewById(R.id.hScrollView);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 1;
        hScrollView.smoothScrollTo(scrollPos, 0);


    }
}
