package com.skyray.atmospheremonitor.Core;


import com.skyray.atmospheremonitor.controller.SerialMaster;

/**
 * Created by linyuan on 2017/11/16 0016.
 */

public class ConnectionManager {


    private static boolean _IfConnected = false;

    public static void connect(){

        if(! _IfConnected){
            SerialMaster.InitConnection();
            _IfConnected = true;
        }
    }
}
