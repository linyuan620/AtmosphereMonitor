package com.skyray.atmospheremonitor.datamodel;


import com.orm.SugarRecord;

/**
 * Created by linyuan on 2017/11/14 0014.
 */

public class ConnectionCfg extends SugarRecord {

    private int SlaveID = 1;

    private String ComPort = "COM1";

    private int BautRate = 9600;

    public ConnectionCfg() {
    }

    public ConnectionCfg(int slaveID, String comPort, int bautRate) {
        SlaveID = slaveID;
        ComPort = comPort;
        BautRate = bautRate;
    }

    public int getSlaveID() {
        return SlaveID;
    }

    public void setSlaveID(int slaveID) {
        SlaveID = slaveID;
    }

    /**
     * COM端口号
     * @return
     */
    public String getComPort() {
        return ComPort;
    }

    public void setComPort(String comPort) {
        ComPort = comPort;
    }

    /**
     * 波特率
     * @return
     */
    public int getBautRate() {
        return BautRate;
    }

    public void setBautRate(int bautRate) {
        BautRate = bautRate;
    }

}
