package com.skyray.atmospheremonitor.controller;

/**
 * Created by linyuan on 2017/11/16 0016.
 */

import com.skyray.atmospheremonitor.datamodel.ConnectionCfg;
import java.util.List;

/**
 * 连接配置管理类
 */

public class ConnectionCfgController {
    private static ConnectionCfgController _CFGCtrl = null;

    private ConnectionCfg CFG = new ConnectionCfg();

    public ConnectionCfg GetConnectionCfg() {
        return CFG;
    }

    //加载配置并获取之
    //取出数据库中的第一个 如果有
    public static ConnectionCfgController GetConnectionCfgController(){
        if(_CFGCtrl == null){
            _CFGCtrl = new ConnectionCfgController();
            List<ConnectionCfg> allConfigs = ConnectionCfg.listAll(ConnectionCfg.class);
            if(allConfigs != null && allConfigs.size() > 0){
                _CFGCtrl.CFG = allConfigs.get(0);
            }
            if(_CFGCtrl.CFG == null){
                _CFGCtrl.CFG = new ConnectionCfg(1,"COM1",9600);
            }
        }
        return _CFGCtrl;
    }

    public void SaveConnectionCfg(ConnectionCfg connectionCfg){
        CFG.setSlaveID(connectionCfg.getSlaveID());
        CFG.setComPort(connectionCfg.getComPort());
        CFG.setBautRate(connectionCfg.getBautRate());
        CFG.save();
    }
}
