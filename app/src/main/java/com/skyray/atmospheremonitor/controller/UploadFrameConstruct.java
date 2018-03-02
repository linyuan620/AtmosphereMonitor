package com.skyray.atmospheremonitor.controller;

import com.skyray.atmospheremonitor.datamodel.MeasureData;
import com.skyray.atmospheremonitor.datamodel.UploadCfg;

/**
 * Created by linyuan on 2017/12/15 0015.
 */

public class UploadFrameConstruct {

    private String start = new String("Start<");
    private String serialNumHeader = new String("Serial:");
    private static String SerialNum = "0";
    private String windSpeed = new String(";WindSpeed:");
    private String windDir = new String(";WindDir:");
    private String temperature = new String(";Temperature:");
    private String humidity = new String(";Humidity:");
    private String pressure = new String(";Pressure:");
    private String tsp = new String(";Tsp:");
    private String pm10 = new String(";Pm10:");
    private String pm25 = new String(";Pm2.5:");
    private String noise = new String(";Noise:");
    private String co = new String(";co:");
    private String no2 = new String(";no2:");
    private String so2 = new String(";so2:");
    private String o3 = new String(";o3:");
    private String tvoc = new String(";tvoc:");
    private String end = new String(";>End");
    private String dataGram = new String();

    private static UploadFrameConstruct _UFCtrl = null;


    public UploadFrameConstruct() {

        UploadCfg cfg = UploadCfgController.GetUploadCfgController().GetUploadCfg();
        SerialNum = cfg.getEquipmentID();

    }

    //加载

    public static UploadFrameConstruct GetUploadFrameConstruct(){
        if(_UFCtrl == null){
            _UFCtrl = new UploadFrameConstruct();
        }
        return _UFCtrl;
    }


    public String makeUploadFrame(MeasureData mdata){
        //重构数据
        StringBuilder sb = new StringBuilder();
        sb.append(start);
        sb.append(serialNumHeader);
        sb.append(SerialNum);

        sb.append(windSpeed);
        sb.append(String.valueOf(mdata.getWindSpeed()));
        sb.append(windDir);
        sb.append(String.valueOf(mdata.getWindDir()));
        sb.append(temperature);
        sb.append(String.valueOf(mdata.getTemperature()));
        sb.append(humidity);
        sb.append(String.valueOf(mdata.getHumidity()));
        sb.append(pressure);
        sb.append(String.valueOf(mdata.getPressure()));
        sb.append(tsp);
        sb.append("0");
        //sb.append(msg.getData().getString("TSP"));
        sb.append(pm10);
        sb.append(String.valueOf(mdata.getPm10()));
        sb.append(pm25);
        sb.append(String.valueOf(mdata.getPm25()));
        sb.append(noise);
        sb.append("0");
        //sb.append(msg.getData().getString("NOISE"));
        sb.append(co);
        sb.append(String.valueOf(mdata.getCo()));
        sb.append(no2);
        sb.append(String.valueOf(mdata.getNo2()));
        sb.append(so2);
        sb.append(String.valueOf(mdata.getSo2()));
        sb.append(o3);
        sb.append(String.valueOf(mdata.getO3()));
        sb.append(tvoc);
        sb.append("0");
        //sb.append(String.valueOf(mdata.getTvoc()));
        sb.append(end);
        dataGram = sb.toString();
        return dataGram;
    }
}
