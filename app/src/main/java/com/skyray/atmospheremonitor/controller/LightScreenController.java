package com.skyray.atmospheremonitor.controller;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

import com.skyray.atmospheremonitor.datamodel.LightScreenCfg;
import com.skyray.atmospheremonitor.datamodel.Messages;
import com.skyray.atmospheremonitor.datamodel.UploadCfg;
import com.temolin.hardware.GPIO_Pin;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by linyuan on 2017/12/15 0015.
 */

public class LightScreenController {

    //设备管理者
    private static DevicePolicyManager mDevicePolicyManager;
    //关屏组件
    private static ComponentName mCompName;

    private static PowerManager mPowerManager;
    private static PowerManager.WakeLock mScreenLock;

    private static Context context;

    private static Timer timer = null;

    private static boolean isScreenOn = false;

    private static GPIO_Pin pinLightScreen = new GPIO_Pin(63);

    private static int pinLightScreenValue;
    private static int pinLightScreenValueTmp;

    private static LightScreenController _SCCtrl = null;

    private static Handler handler;

    private static LightScreenCfg LFG = new LightScreenCfg();

    public static LightScreenCfg GetLightScreenCfg() {
        return LFG;
    }

    public LightScreenController(Handler handler,
                                 DevicePolicyManager mDevicePolicyManager,
                                 ComponentName mCompName,
                                 PowerManager mPowerManager,
                                 Context context) {

        this.handler = handler;
        this.mDevicePolicyManager = mDevicePolicyManager;
        this.mCompName = mCompName;
        this.mPowerManager = mPowerManager;
        this.context = context;

        mScreenLock = mPowerManager.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP //该flag使能屏幕关闭时，也能点亮屏幕（通常的wakelock只能维持屏幕处于一直开启状态，如果灭屏时，是不会自动点亮的）
                        | PowerManager.SCREEN_DIM_WAKE_LOCK
                        | PowerManager.ON_AFTER_RELEASE, "screenOnWakeLock");

        pinLightScreen.setToGpioMode();
        pinLightScreen.setModeINPUT();

        pinLightScreenValueTmp = pinLightScreen.getInputVal();

    }

    public void startLightScreenControl() {

            if (LFG.getLightScreenTime() != 0) {

            timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    pinLightScreenValue = pinLightScreen.getInputVal();

                    if (pinLightScreenValue != pinLightScreenValueTmp) {
                        pinLightScreenValueTmp = pinLightScreenValue;

                        if (!isScreenOn) {
                            sendLightScreenControlMsg();
                        }
                    }
                }
            }, 0, 500);
        }
    }

    public static  void  stopLightScreenControl(){
        timer.cancel();
        timer = null;
    }

    private static void sendLightScreenControlMsg(){
        synchronized(handler) {
            Message msg = Message.obtain(handler, Messages.ScreenControl_Msg);
            handler.sendMessage(msg);
        }
    }

    public static void lightScreen(){
        //isHeld()，判断是否获取锁，false未获取锁
        if (!mScreenLock.isHeld()) {
            mScreenLock.acquire();
            //Toast.makeText(MainActivity.this, "acquire", Toast.LENGTH_SHORT).show();
        } else {
            mScreenLock.release();
            mScreenLock.acquire();
            //Toast.makeText(MainActivity.this, "release", Toast.LENGTH_SHORT).show();
        }
        isScreenOn = true;
    }

    //点击按钮关闭屏幕
    public static void onScreenOff() {
        // 判断该组件是否有系统管理员的权限
        if (!mDevicePolicyManager.isAdminActive(mCompName)) {//这一句一定要有...
            Intent intent = new Intent();
            //指定动作
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            //指定给那个组件授权
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mCompName);
            context.startActivity(intent);
        } else {
            //立即关闭屏幕
            mDevicePolicyManager.lockNow();
            isScreenOn = false;
            //mDevicePolicyManager.resetPassword("123321", 0);
//            Log.i(TAG, "具有权限,将进行锁屏....");
//            Log.i(TAG, "going to shutdown screen");
        }
    }

    public static void unLightScreenWithDelayed(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScreenOff();
            }
        }, LFG.getLightScreenTime()*1000);
    }

    public static void SaveLightScreenConfig(LightScreenCfg cfg){
        LFG.setLightScreenTime(cfg.getLightScreenTime());
        LFG.save();
    }

//加载

    public static LightScreenController GetLightScreenController(Handler handler,
                                                                 DevicePolicyManager mDevicePolicyManager,
                                                                 ComponentName mCompName,
                                                                 PowerManager mPowerManager,
                                                                 Context context){
        if(_SCCtrl == null){
            _SCCtrl = new LightScreenController(handler,
                    mDevicePolicyManager,
                    mCompName,
                    mPowerManager,
                    context);

            List<LightScreenCfg> allConfigs = LightScreenCfg.listAll(LightScreenCfg.class);
            if(allConfigs != null && allConfigs.size() > 0){
                _SCCtrl.LFG = allConfigs.get(0);
            }
            if(_SCCtrl.LFG == null){
                _SCCtrl.LFG = new LightScreenCfg(300);
            }
        }
        return _SCCtrl;
    }
}
