package com.skyray.atmospheremonitor.controller;

import com.temolin.hardware.GPIO_Pin;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by linyuan on 2017/12/15 0015.
 */

public class RunLedController {

    private static boolean isLedRunOn = false;
    private static GPIO_Pin pinLedRun = new GPIO_Pin(64);

    private static RunLedController _RLCtrl = null;

    private static Timer timer = null;

    public RunLedController() {

        pinLedRun.setToGpioMode();
        pinLedRun.setModeOUTPUT();

    }

    public void StartRunLedControl(){
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isLedRunOn) {
                    pinLedRun.setHIGH();
                    isLedRunOn = false;
                } else {
                    pinLedRun.setLOW();
                    isLedRunOn = true;
                }
            }
        },0,500);
    }


    public void StopRunLedControl(){
        timer.cancel();
        timer = null;
        //实际使用时没起作用
        pinLedRun.setLOW();
    }


//加载

    public static RunLedController GetRunLedController(){
        if(_RLCtrl == null){
            _RLCtrl = new RunLedController();
        }
        return _RLCtrl;
    }



}
