package com.skyray.atmospheremonitor.datamodel;


import com.orm.SugarRecord;

/**
 * Created by linyuan on 2017/11/14 0014.
 */

public class UploadCfg extends SugarRecord {

    private int UploadIntervalTime = 60;

    private String EquipmentID = "TM80D98E31DE0D";

    private String Ip = "58.210.116.142";

    private int PortNum = 9666;

    private boolean IsWifiEnabled = false;

    public UploadCfg() {
    }

    public UploadCfg(int uploadIntervalTime, String equipmentID, String ip, int portNum ,boolean isWifiEnabled) {
        UploadIntervalTime = uploadIntervalTime;
        EquipmentID = equipmentID;
        PortNum = portNum;
        Ip = ip;
        IsWifiEnabled = isWifiEnabled;
    }

    public int getUploadIntervalTime() {
        return UploadIntervalTime;
    }

    public void setUploadIntervalTime(int uploadIntervalTime) {
        UploadIntervalTime = uploadIntervalTime;
    }

    public String getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        EquipmentID = equipmentID;
    }

    public int getPortNum() {
        return PortNum;
    }

    public void setPortNum(int portNum) {
        PortNum = portNum;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public boolean isWifiEnabled() {
        return IsWifiEnabled;
    }

    public void setWifiEnabled(boolean wifiEnabled) {
        IsWifiEnabled = wifiEnabled;
    }
}
