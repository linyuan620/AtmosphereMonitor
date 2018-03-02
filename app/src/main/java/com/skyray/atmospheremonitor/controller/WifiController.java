package com.skyray.atmospheremonitor.controller;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.skyray.atmospheremonitor.datamodel.UploadCfg;

/**
 * Created by linyuan on 2017/12/15 0015.
 */

public class WifiController {

    private boolean isWifiOpen = false;
    private WifiManager wifiManager=null;

    private static WifiController _WFCtrl = null;

    public WifiController() {
        UploadCfg cfg = UploadCfgController.GetUploadCfgController().GetUploadCfg();
        isWifiOpen = cfg.isWifiEnabled();
    }

    public void InitWifiControl(WifiManager wifiManager){
        wifiManager.setWifiEnabled(isWifiOpen);
    }
    //加载

    public static WifiController GetWifiController(){
        if(_WFCtrl == null){
            _WFCtrl = new WifiController();
        }
        return _WFCtrl;
    }

}
