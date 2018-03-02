package com.skyray.atmospheremonitor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.skyray.atmospheremonitor.Adapter.ActionListPanelListAdapter;
import com.skyray.atmospheremonitor.Adapter.LogAdapter;
import com.skyray.atmospheremonitor.Adapter.LogBigramHeaderAdapter;
import com.skyray.atmospheremonitor.Utils.FullScreenController;
import com.skyray.atmospheremonitor.controller.LogDataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysu.zyb.panellistlibrary.PanelListLayout;


public class ActivityHistoryDetail extends BaseActivity {

    TextView toolbarTitle = null;
    private PanelListLayout pl_root;
    private ListView lv_content;

    private ActionListPanelListAdapter adapter;

    private List<Map<String, String>> contentList = new ArrayList<>();

    private void initControls() {

    }

    private void initList() {
        initView();
        initContentDataList();
        adapter = new ActionListPanelListAdapter(this, pl_root, lv_content, R.layout.item_content, contentList);
        adapter.setInitPosition(0);
        adapter.setSwipeRefreshEnabled(true);
        adapter.setRowDataList(getRowDataList());
        adapter.setTitle("序号");

        adapter.setOnRefreshListener(new CustomRefreshListener());
        pl_root.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history_detail);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * 此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
         * (只会在第一次初始化菜单时调用) Inflate the menu; this adds items to the action bar
         * if it is present.
         */
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityHistoryDetail.this.finish();
                break;
            case R.id.export:
                ActivityHistoryDetail.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            // do sth here, example:
            //Toast.makeText(this, "custom SwipeRefresh listener", Toast.LENGTH_SHORT).show();
            adapter.getSwipeRefreshLayout().setRefreshing(false);// don`t forget to call this
        }
    }
    /** 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private List<String> getRowDataList(){
        List<String> rowDataList = new ArrayList<>();
        rowDataList.add("Time");//0列
        rowDataList.add("WindSpeed");//1列
        rowDataList.add("WindDir");//2列
        rowDataList.add("Temperature");
        rowDataList.add("Humidity");//4列
        rowDataList.add("Pressure");
        rowDataList.add("Tsp");//6列
        rowDataList.add("Pm10");
        rowDataList.add("Pm2.5");//8列
        rowDataList.add("Noise");
        rowDataList.add("Co");//10列
        rowDataList.add("No2");
        rowDataList.add("So2");//12列
        rowDataList.add("O3");
        rowDataList.add("H2s");//14列
        rowDataList.add("Tvoc");
        return rowDataList;
    }
    /**
     * 初始化content数据
     */
    private void initContentDataList() {
        for (int i = 1; i < 22; i++) {
            Map<String, String> data = new HashMap<>();
            data.put("0", "yyyy/MM/dd");
            data.put("1", "第" + i + "行第2个");
            data.put("2", "第" + i + "行第3个");
            data.put("3", "HH:mm:ss");
            data.put("4", "第" + i + "行第5个");
            data.put("5", "第" + i + "行第6个");
            data.put("6", "第" + i + "行第7个");
            data.put("7", "第" + i + "行第8个");
            data.put("8", "第" + i + "行第9个");
            data.put("9", "第" + i + "行第10个");
            data.put("10", "第" + i + "行第11个");
            data.put("11", "第" + i + "行第12个");
            data.put("12", "第" + i + "行第13个");
            data.put("13", "第" + i + "行第14个");
            data.put("14", "第" + i + "行第15个");
            data.put("15", "第" + i + "行第16个");
            data.put("16", "第" + i + "行第17个");

            contentList.add(data);
        }
    }

    private void initView() {
        pl_root = (PanelListLayout) findViewById(R.id.id_pl_root);
        lv_content = (ListView) findViewById(R.id.id_lv_content);
    }
}
