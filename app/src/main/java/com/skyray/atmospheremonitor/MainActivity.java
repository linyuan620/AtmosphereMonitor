package com.skyray.atmospheremonitor;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.skyray.atmospheremonitor.BroadcastReceiver.YNAdminReceiver;
import com.skyray.atmospheremonitor.Core.MainStatusManage;
import com.skyray.atmospheremonitor.Core.MyLogManager;
import com.skyray.atmospheremonitor.Utils.FullScreenController;
import com.skyray.atmospheremonitor.Utils.SystemTimeShower;
import com.skyray.atmospheremonitor.controller.LightScreenController;
import com.skyray.atmospheremonitor.controller.RunLedController;
import com.skyray.atmospheremonitor.controller.UploadFrameConstruct;
import com.skyray.atmospheremonitor.controller.WifiController;
import com.skyray.atmospheremonitor.datamodel.MeasureData;
import com.skyray.atmospheremonitor.datamodel.Messages;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = getClass().getName();

    private ClientThread clientThread;
    private GetterThread getterThread;

    /**
     * 切换显示子窗体方法
     * @param c 子窗体方法
     */
    public void showChildActivity(Class c){
        Intent intent = new Intent(MainActivity.this,c);
        startActivity(intent);
    }

    /**
     * 切换显示子窗体方法
     * @param c 子窗体方法
     */
    public void showChildActivity(Class c,Intent intent){
        startActivity(intent);
    }

    /**
     * 显示登录界面
     * @param
     */
    public void showLoginActivity(Intent intent){
        showChildActivity(ActivityLogin.class,intent);
    }

    /**
     * 显示仪表维护界面
     * @param
     */
    public void showMaintainActivity(){
        showChildActivity(ActivityMaintain.class);
    }

    /**
     * 显示历史数据界面
     * @param
     */
    public void showHistoryActivity(){
        showChildActivity(ActivityHistory.class);
    }

    /**
     * 显示运行日志界面
     * @param
     */
    public void showLogActivity(){
        showChildActivity(ActivityLog.class);
    }

    /**
     * 显示设置界面
     * @param
     */
    public void showSettingActivity(){
        showChildActivity(ActivitySetting.class);
    }

    /**
     * 显示关于界面
     * @param
     */
    public void showAboutActivity(){
        showChildActivity(ActivityAbout.class);
    }

    public void showDrawerMenu(){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLogManager.addLogInfo("系统启动");

        initAllControls();

        SystemTimeShower.getSystemTimeShower().startShowSystemTime(handler);

        RunLedController.GetRunLedController().StartRunLedControl();

        LightScreenController.GetLightScreenController(handler,
                (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE),
                new ComponentName(this, YNAdminReceiver.class),
                (PowerManager) getSystemService(POWER_SERVICE),
                this
        ).startLightScreenControl();

        WifiController.GetWifiController().InitWifiControl(
                (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE));

        getterThread = new GetterThread(handler);
        new Thread(getterThread).start();

        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemTimeShower.getSystemTimeShower().stopShowSystemTime();

        RunLedController.GetRunLedController().StopRunLedControl();
        LightScreenController.stopLightScreenControl();

        if(getterThread != null){
            getterThread.interrupt();
        }

        if(clientThread != null){
            clientThread.interrupt();
        }

        MainStatusManage.setIfStopOperation(true);
        MainStatusManage.setIsClientThreadSonRun(false);
        MainStatusManage.setIsClientThreadMainRun(false);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            FullScreenController.hideNavigationBar(this);
        }
    }

    @Override
    public void onBackPressed() {
        SugarORMApp.exit(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDrawerMenu:
                showDrawerMenu();
                break;
            default:
                break;
        }
    }
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;

    private ImageButton btnDrawerMenu;

    //主界面文本显示
    private TextView tvWindSpeed;
    private TextView tvWindDir;
    private TextView tvTemperature;
    private TextView tvHumidty;
    private TextView tvPressure;
    private TextView tvTsp;
    private TextView tvPm10;
    private TextView tvPm25;
    private TextView tvNoise;
    private TextView tvCo;
    private TextView tvNo2;
    private TextView tvSo2;
    private TextView tvO3;
    private TextView tvTvoc;

    //时间显示文本
    private TextView tvSystemTime;

    private void initAllControls(){
        //侧菜单
        mDrawerLayout =  (DrawerLayout)findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.nav_view) ;
        //主界面导航按钮
        btnDrawerMenu = (ImageButton) findViewById(R.id.btnDrawerMenu);

        //主界面程序退出按钮
        btnDrawerMenu.setOnClickListener(this);

        tvWindSpeed = (TextView) findViewById(R.id.WindSpeed);
        tvWindDir = (TextView) findViewById(R.id.WindDir);
        tvTemperature = (TextView) findViewById(R.id.Tmp);
        tvHumidty= (TextView) findViewById(R.id.Hum);
        tvPressure= (TextView) findViewById(R.id.Pre);
        tvTsp= (TextView) findViewById(R.id.Tsp);
        tvPm10= (TextView) findViewById(R.id.Pm10);
        tvPm25= (TextView) findViewById(R.id.Pm2_5);
        tvNoise= (TextView) findViewById(R.id.Noise);
        tvCo= (TextView) findViewById(R.id.Co);
        tvNo2= (TextView) findViewById(R.id.No2);
        tvSo2= (TextView) findViewById(R.id.So2);
        tvO3= (TextView) findViewById(R.id.O3);
        tvTvoc= (TextView) findViewById(R.id.Tvoc);

        //时间显示文本
        tvSystemTime = (TextView) findViewById(R.id.tvSystemTime);

        //设置选中item 这里默认不选中
        //navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //取消被选中状态
                item.setChecked(false);
                mDrawerLayout.closeDrawers();
                switch(item.getItemId()){
                    case R.id.menu_item_activity_maintain:
                        Intent intent = new Intent(MainActivity.this,ActivityLogin.class);
                        intent.setAction(ActivityMaintain.START_MAINTAIN_ACTION);
                        showLoginActivity(intent);
                        break;
                    case R.id.menu_item_activity_history:
                        showHistoryActivity();
                        break;
                    case R.id.menu_item_activity_log:
                        showLogActivity();
                        break;
                    case R.id.menu_item_activity_setting:
                        Intent intent1 = new Intent(MainActivity.this,ActivityLogin.class);
                        intent1.setAction(ActivitySetting.START_SETTING_ACTION);
                        showLoginActivity(intent1);
                        break;
                    case R.id.menu_item_activity_about:
                        showAboutActivity();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private  void showSystemTime(Object msg){
        if(msg != null){
            tvSystemTime.setText(msg.toString());
        }
    }

    private void showMeasureInfoInMain(Object msg){
        if(msg != null)
        {
            MeasureData mdata = (MeasureData) msg;

            tvWindSpeed.setText("风速:  "+ mdata.getWindSpeed() +" m/s");
            tvWindDir.setText("风向:  "+ mdata.getWindDir() +" °");
            tvTemperature.setText("温度:  "+ mdata.getTemperature() +" °C");
            tvHumidty.setText("湿度:  "+ mdata.getHumidity() +" %RH");
            tvPressure.setText("压力:  "+ mdata.getPressure() +" kPa");
            tvTsp.setText("Tsp:  "+"0"+" ug/m3");
            tvPm10.setText("Pm10:  "+ mdata.getPm10() +" ug/m3");
            tvPm25.setText("Pm2.5:  "+ mdata.getPm25() +" ug/m3");
            tvNoise.setText("噪声:  "+"0"+" dB");
            tvCo.setText("Co:  "+ mdata.getCo() +" Ppb");
            tvNo2.setText("No2:  "+ mdata.getNo2() +" Ppb");
            tvSo2.setText("So2:  "+ mdata.getSo2() +" Ppb");
            tvO3.setText("O3:  "+ mdata.getO3() +" Ppb");
            tvTvoc.setText("Tvoc:  "+ "0" +" Ppb");

            String uploadFrame = UploadFrameConstruct.GetUploadFrameConstruct().makeUploadFrame(mdata);

            MainStatusManage.setUpLoadFrameData(uploadFrame);
            MainStatusManage.setIsMeasureUpdated(true);
        }
    }

     Handler handler = new Handler(){
        public void handleMessage(Message msg){

            switch(msg.what){
                case Messages.ShowSystemTime_Msg:
                    showSystemTime(msg.obj);
                    break;
                case Messages.Measure_Msg:
                    showMeasureInfoInMain(msg.obj);
                    break;
                case Messages.ClientThread_Msg:
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case Messages.GetterThread_Msg:
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case Messages.ScreenControl_Msg:
                    LightScreenController.lightScreen();
                    LightScreenController.unLightScreenWithDelayed();
                    break;
                default:
                    break;
            }
        }
    };

}




