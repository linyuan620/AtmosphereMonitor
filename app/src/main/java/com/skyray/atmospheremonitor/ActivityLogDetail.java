package com.skyray.atmospheremonitor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.skyray.atmospheremonitor.Adapter.LogAdapter;
import com.skyray.atmospheremonitor.Adapter.LogBigramHeaderAdapter;
import com.skyray.atmospheremonitor.Utils.DateOperator;
import com.skyray.atmospheremonitor.Utils.FullScreenController;
import com.skyray.atmospheremonitor.Utils.SimpleMessageShower;
import com.skyray.atmospheremonitor.controller.LogDataProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ActivityLogDetail extends BaseActivity {

    TextView toolbarTitle = null;

    private StickyHeadersItemDecoration top;
    private RecyclerView list;
    private LogDataProvider logDataProvider = null;
    private LogAdapter logAdapter = null;

    private void initControls() {

    }

    private void initList() {
        list = (RecyclerView) findViewById(R.id.log_list);

        list.setLayoutManager(new LinearLayoutManager(ActivityLogDetail.this, LinearLayoutManager.VERTICAL, false));

        logDataProvider = new LogDataProvider();
        logAdapter = new LogAdapter(this, logDataProvider);

        top = new StickyHeadersBuilder()
                .setAdapter(logAdapter)
                .setRecyclerView(list)
                .setStickyHeadersAdapter(new LogBigramHeaderAdapter(logDataProvider.getItems()))
                .build();

        list.setAdapter(logAdapter);
        list.addItemDecoration(top);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_detail);

        initControls();
        initList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.button_selector_nav);

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        if (toolbarTitle != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FullScreenController.hideNavigationBar(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityLogDetail.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
