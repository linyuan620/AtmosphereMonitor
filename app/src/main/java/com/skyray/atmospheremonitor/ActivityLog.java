package com.skyray.atmospheremonitor;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.skyray.atmospheremonitor.Utils.DateOperator;
import com.skyray.atmospheremonitor.Utils.FullScreenController;
import com.skyray.atmospheremonitor.Utils.SimpleMessageShower;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ActivityLog extends BaseActivity implements View.OnClickListener{

    DatePicker dtBeginDate = null;
    TimePicker tpBeginTime = null;
    DatePicker dtEndDate = null;
    TimePicker tpEndTime = null;

    Button btnQueryLog = null;

    private void initControls() {
        Date dtNow = DateOperator.getNow();
        dtBeginDate = (DatePicker) findViewById(R.id.dp_begin);
        Date date = DateOperator.addDay(dtNow, -7);
        dtBeginDate.updateDate(date.getYear() + 1900, date.getMonth(), date.getDate());

        tpBeginTime = (TimePicker) findViewById(R.id.tp_begin);
        tpBeginTime.setIs24HourView(true);
        tpBeginTime.setCurrentHour(date.getHours());
        tpBeginTime.setCurrentMinute(date.getMinutes());

        dtEndDate = (DatePicker) findViewById(R.id.dp_end);
        dtEndDate.updateDate(dtNow.getYear() + 1900, dtNow.getMonth(), dtNow.getDate());

        tpEndTime = (TimePicker) findViewById(R.id.tp_end);
        tpEndTime.setIs24HourView(true);
        tpEndTime.setCurrentHour(dtNow.getHours());
        tpEndTime.setCurrentMinute(dtNow.getMinutes());

        resizePikcer(dtBeginDate);
        resizePikcer(tpBeginTime);
        resizePikcer(dtEndDate);
        resizePikcer(tpEndTime);

        btnQueryLog = (Button)findViewById(R.id.btnQueryLog);
        btnQueryLog.setOnClickListener(this);
    }

    private void initList() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log);

        initControls();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLog.this.finish();
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


    /*
 * 调整numberpicker大小
 */
    private void resizeNumberPicker(NumberPicker np){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);
        np.setLayoutParams(params);
    }

    /**
     * 调整FrameLayout大小
     * @param tp
     */
    private void resizePikcer(FrameLayout tp){
        List<NumberPicker> npList = findNumberPicker(tp);
        for(NumberPicker np:npList){
            resizeNumberPicker(np);
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(null != viewGroup){
            for(int i = 0;i<viewGroup.getChildCount();i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btnQueryLog:
                Intent intent = new Intent();
                intent.setClass(ActivityLog.this,ActivityLogDetail.class);
                startActivity(intent);
                break;

        }
    }
}
