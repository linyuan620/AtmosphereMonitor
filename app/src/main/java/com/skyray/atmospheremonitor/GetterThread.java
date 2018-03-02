package com.skyray.atmospheremonitor;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.skyray.atmospheremonitor.Core.ConnectionManager;
import com.skyray.atmospheremonitor.Core.MainStatusManage;
import com.skyray.atmospheremonitor.Utils.DateOperator;
import com.skyray.atmospheremonitor.Utils.DateTimeFormater;
import com.skyray.atmospheremonitor.controller.CalibrateCoefficientCfgController;
import com.skyray.atmospheremonitor.controller.ConnectionCfgController;
import com.skyray.atmospheremonitor.controller.SerialMaster;
import com.skyray.atmospheremonitor.controller.UploadCfgController;
import com.skyray.atmospheremonitor.datamodel.CalibrateCoefficient;
import com.skyray.atmospheremonitor.datamodel.CommandDefine;
import com.skyray.atmospheremonitor.datamodel.MeasureData;
import com.skyray.atmospheremonitor.datamodel.Messages;

import java.util.Date;

/**
 * Created by linyuan on 2017/12/15 0015.
 */
//读取背板采集数据线程
public class GetterThread extends Thread {

    private String TAG = getClass().getName();

    private Handler handler;
    private CalibrateCoefficient coefficient =
            CalibrateCoefficientCfgController.
                    GetCalibrateCoefficientController().GetCalibrateCoefficient();

    public GetterThread(Handler handler)
    {
        this.handler = handler;
    }

    @Override
    public void run() {
        //连接主机
        try{
            ConnectionManager.connect();
        }catch (Exception e){
            e.printStackTrace();
            sendShowGetterThreadMsg(e.getMessage());
        }
        //延时200ms等待主机连接成功
        try{
            Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }

        int intervalTime = UploadCfgController.GetUploadCfgController().GetUploadCfg().getUploadIntervalTime();
        int slaveId = ConnectionCfgController.GetConnectionCfgController().
                GetConnectionCfg().getSlaveID();

        if(intervalTime >0 || intervalTime <=300){

        }else
        {
            intervalTime = 30;
        }
        MainStatusManage.setIfStopOperation(false);

        while(!MainStatusManage.isIfStopOperation()){

            Log.e(TAG, "run: caijizhong!");

            short[] data = SerialMaster.readInputRegisters(slaveId,
                    CommandDefine.InputRegStartAdress,CommandDefine.InputRegNum);
            if( data != null && data.length == CommandDefine.InputRegNum ) {
                MeasureData mData = new MeasureData();
                double resultHeader , result;

                resultHeader = getUnsignedShort(data[0]);

                result = getUnsignedShort(data[1]);
                mData.setWindSpeed(result/10.0);

                result = getUnsignedShort(data[2]);
                mData.setWindDir(result/10.0);

                //温度范围-20至80度
                //result = getUnsignedShort(data[3]);
                mData.setTemperature(data[3]/10.0);

                result = getUnsignedShort(data[4]);
                mData.setHumidity(result/10.0);

                result = getUnsignedShort(data[5]);
                mData.setPressure(result/10.0);

                result = getUnsignedShort(data[6]);
                result = coefficient.getTspKValue()*result + coefficient.getTspBValue();
                mData.setTsp(result/10.0);

                result = getUnsignedShort(data[7]);
                result = coefficient.getPm10KValue()*result + coefficient.getPm10BValue();
                mData.setPm10(result/10.0);

                result = getUnsignedShort(data[8]);
                result = coefficient.getPm25KValue()*result + coefficient.getPm25BValue();
                mData.setPm25(result/10.0);

                result = getUnsignedShort(data[9]);
                mData.setNoise(result/10.0);

                result = (getUnsignedShort(data[10]) ) | (getUnsignedShort(data[11]) << 8) ;
                result = coefficient.getCoKValue()*result + coefficient.getCoBValue();
                mData.setCo(result);

                result = (getUnsignedShort(data[12]) ) | (getUnsignedShort(data[13]) << 8) ;
                result = coefficient.getNo2KValue()*result + coefficient.getNo2BValue();
                mData.setNo2(result);

                result = (getUnsignedShort(data[14]) ) | (getUnsignedShort(data[15]) << 8) ;
                result = coefficient.getSo2KValue()*result + coefficient.getSo2BValue();
                mData.setSo2(result);

                result = (getUnsignedShort(data[16]) ) | (getUnsignedShort(data[17]) << 8) ;
                result = coefficient.getO3KValue()*result + coefficient.getO3BValue();
                mData.setO3(result);

                result = (getUnsignedShort(data[18]) ) | (getUnsignedShort(data[19]) << 8) ;
                result = coefficient.getH2sKValue()*result + coefficient.getH2sBValue();
                mData.setH2s(result);

                result = (getUnsignedShort(data[20]) ) | (getUnsignedShort(data[21]) << 8) ;
                result = coefficient.getTvocKValue()*result + coefficient.getTvocBValue();
                mData.setTvoc(result);

                sendShowMesaureStatusMsg(mData);
                Message msg = Message.obtain(handler,Messages.Measure_Msg);
                msg.obj = mData;
                handler.sendMessage(msg);

                SaveResult(mData);
            }else{
                Log.e(TAG, "run: data is null");
            }

            try{
                Thread.sleep(intervalTime*1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //将data字节型数据转换为0~65535(0xFFFF 即为 WORD)
    public int getUnsignedShort(short data){
        return data& 0x0FFFF;
    }

    void SaveResult(MeasureData mdata){
        Date dt = DateOperator.getNow();
        Date operTime = new Date(dt.getYear() ,dt.getMonth(),dt.getDate(),dt.getHours(),dt.getMinutes(),dt.getSeconds());

        mdata.setMeasureTime(operTime);
        mdata.setDateString(DateTimeFormater.getTimeString(operTime));

        mdata.save();
    }

    public void sendShowMesaureStatusMsg(MeasureData mData){
        synchronized(handler){
            Message msg = Message.obtain(handler,Messages.Measure_Msg);
            msg.obj = mData;
            handler.sendMessage(msg);
        }
    }

    public void sendShowGetterThreadMsg(String mData){
        synchronized(handler) {
            Message msg = Message.obtain(handler, Messages.GetterThread_Msg);
            msg.obj = mData;
            handler.sendMessage(msg);
        }
    }
}
