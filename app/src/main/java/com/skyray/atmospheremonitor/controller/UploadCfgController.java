package com.skyray.atmospheremonitor.controller;

/**
 * Created by linyuan on 2017/11/16 0016.
 */

import com.skyray.atmospheremonitor.datamodel.ConnectionCfg;
import com.skyray.atmospheremonitor.datamodel.UploadCfg;

import java.util.List;

/**
 * 连接配置管理类
 */

public class UploadCfgController {
    private static UploadCfgController _UFGCtrl = null;

    private UploadCfg UFG = new UploadCfg();

    public UploadCfg GetUploadCfg() {
        return UFG;
    }

    //加载配置并获取之
    //取出数据库中的第一个 如果有
    public static UploadCfgController GetUploadCfgController(){
        if(_UFGCtrl == null){
            _UFGCtrl = new UploadCfgController();
            List<UploadCfg> allConfigs = UploadCfg.listAll(UploadCfg.class);
            if(allConfigs != null && allConfigs.size() > 0){
                _UFGCtrl.UFG = allConfigs.get(0);
            }
            if(_UFGCtrl.UFG == null){
                _UFGCtrl.UFG = new UploadCfg(60,"null","58.210.116.142",9666,false);
            }
        }
        return _UFGCtrl;
    }

    public void SaveUploadCfg(UploadCfg uploadCfg){
        UFG.setEquipmentID(uploadCfg.getEquipmentID());
        UFG.setIp(uploadCfg.getIp());
        UFG.setPortNum(uploadCfg.getPortNum());
        UFG.setUploadIntervalTime(uploadCfg.getUploadIntervalTime());
        UFG.setWifiEnabled(uploadCfg.isWifiEnabled());
        UFG.save();
    }
}
