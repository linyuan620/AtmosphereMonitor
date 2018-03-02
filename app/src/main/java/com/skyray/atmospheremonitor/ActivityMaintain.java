package com.skyray.atmospheremonitor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.skyray.atmospheremonitor.Utils.FullScreenController;

import java.util.HashMap;

public class ActivityMaintain extends BaseActivity{

    public final String TAG = getClass().getName();
    public static String START_MAINTAIN_ACTION = "START_MAINTAIN_ACTION";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreenController.setNoTitleAndHideNavigationBar(this);
        setContentView(R.layout.activity_maintain);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //TabLayout
        TabLayout tabLayout  = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ActivityMaintain.this.finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            FullScreenController.hideNavigationBar(this);
        }
    }

    /**
     *仪表维护子菜单类型
     */
    enum MaintainType{
        HandCalibrate(0),HandDebug(1);

        private int _value;

        private MaintainType(int value){_value = value;};

        public int value(){return _value;};

        public static MaintainType valueOf(int value){
            switch(value){
                case 0:
                    return HandCalibrate;
                case 1:
                    return HandDebug;
                default:
                    return null;
            }
        }
    }

    HashMap<MaintainType,FragmentBase> fragments = new HashMap<>();

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MaintainType maintainType = MaintainType.valueOf(position);
            if(!ActivityMaintain.this.fragments.containsKey(maintainType)){
                FragmentBase fragment = null;
                switch(maintainType){
                    case HandCalibrate:
                        fragment = new FragmentMaintain_HandCalibrate();
                        break;
                    case HandDebug:
                        fragment = new FragmentMaintain_HandDebug();
                        break;
                    default:
                        break;
                }
                ActivityMaintain.this.fragments.put(maintainType,fragment);
            }
            return ActivityMaintain.this.fragments.get(maintainType);
        }

        @Override
        public int getCount() {
            return MaintainType.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            MaintainType maintainType = MaintainType.valueOf(position);
            switch (maintainType){
                case HandCalibrate:
                    return getResources().getString(R.string.e_HandCalibrate);
                case HandDebug:
                    return getResources().getString(R.string.e_HandDebug);
            }
            return null;
        }
    }
}
