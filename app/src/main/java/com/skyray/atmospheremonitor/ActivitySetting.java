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


public class ActivitySetting extends BaseActivity{

    public String TAG = getClass().getName();
    public static String START_SETTING_ACTION = "START_SETTING_ACTION";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SettingPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreenController.setNoTitleAndHideNavigationBar(this);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SettingPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySetting.this.finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FullScreenController.hideNavigationBar(this);
        }
    }

    /**
     * 设置子菜单类型
     */
    enum SettingType {
        upload(0),lightScreen(1),measureUnit(2),connection(3);

        private int _value;

        private SettingType(int value) {
            _value = value;
        }

        public int value() {
            return _value;
        }

        public static SettingType valueOf(int value) {
            switch (value) {
                case 0:
                    return upload;
                case 1:
                    return lightScreen;
                case 2:
                    return measureUnit;
                case 3:
                    return connection;
                default:
                    return null;
            }
        }
    }

    HashMap<SettingType, FragmentBase> fragments = new HashMap<>();

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SettingPagerAdapter extends FragmentPagerAdapter {

        public SettingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SettingType settingType = SettingType.valueOf(position);

            if (!fragments.containsKey(settingType)) {
                FragmentBase fragment = null;
                switch (settingType) {
                    case upload:
                        fragment = new FragmentSetting_Upload();
                        break;
                    case connection:
                        fragment = new FragmentSetting_Connection();
                        break;
                    case lightScreen:
                        fragment = new FragmentSetting_LightScreen();
                        break;
                    case measureUnit:
                        fragment = new FragmentSetting_MeasureUnit();
                        break;
                    default:
                        break;
                }
                fragments.put(settingType, fragment);
            }
            return fragments.get(settingType);
        }

        @Override
        public int getCount() {
            return SettingType.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            SettingType settingType = SettingType.valueOf(position);
            switch (settingType) {
                case connection:
                    return getResources().getString(R.string.title_setting_connection);
                case upload:
                    return getResources().getString(R.string.title_setting_upload);
                case lightScreen:
                    return getResources().getString(R.string.title_setting_lightScreen);
                case measureUnit:
                    return getResources().getString(R.string.title_setting_measureUnit);
            }
            return null;
        }
    }
}
