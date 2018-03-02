package com.skyray.atmospheremonitor;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.skyray.atmospheremonitor.Core.MainStatusManage;
import com.skyray.atmospheremonitor.controller.UploadCfgController;
import com.skyray.atmospheremonitor.datamodel.Messages;
import com.skyray.atmospheremonitor.datamodel.UploadCfg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;

/**
 * Created by linyuan on 2017/3/31.
 */

public class ClientThread extends Thread {

    private Socket s;
    private Handler handler;

    BufferedReader br = null;

    private String TAG = new String("ClientThread");

    OutputStream os = null;

    public ClientThread(Handler handler)
    {
        this.handler = handler;
        MainStatusManage.setIsClientThreadSonRun(true);
        MainStatusManage.setIsClientThreadMainRun(true);
    }

    @Override
    public void run()
    {
        while( MainStatusManage.isIsClientThreadMainRun() )
        {
            try
            {
                //延时5s等待系统拨号成功
                Thread.sleep(5000);
                //无指定的超时默认连接超时时间是90s

                UploadCfg cfg = UploadCfgController.GetUploadCfgController().GetUploadCfg();

                //s = new Socket("222.92.254.180",8090);
                Log.v(TAG, "ip"+cfg.getIp()+"portNum"+cfg.getPortNum());

                s = new Socket(cfg.getIp(),cfg.getPortNum());

                Log.v(TAG, "new Socket!");
                MainStatusManage.setIsClientThreadSonRun(true);

                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                try{
                    os = s.getOutputStream();
                }catch(IOException e){
                    e.printStackTrace();
                }
                byte errCnt = 0;

                while( MainStatusManage.isIsClientThreadSonRun() ) {
                    //----------------------------------------------------------------------------
                    SimpleDateFormat sDateFormat = new SimpleDateFormat(
                            "yyyy/MM/dd HH:mm:ss");
                    String date = sDateFormat.format(new java.util.Date());
                    Log.v(TAG, "new date!");

                    if(MainStatusManage.isIsMeasureUpdated()){

                        Log.v(TAG, "MeaureData is updated!");
                        try {
                            os.write((date + "://   "+ new String(MainStatusManage.getUpLoadFrameData().getBytes()) + "\r\n").getBytes("utf-8"));
                            Thread.sleep(1000*cfg.getUploadIntervalTime());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.v(TAG, "out sleep!");
                        String content = null;
                        try {
                            //有数据可供读取
                            Log.v(TAG, "is br ready!");
                            if(br.ready()){
                                Log.v(TAG, "br is ready!");
                                //不断读取Socket输入流的内容
                                if ((content = br.readLine()) != null) {
                                    Log.v(TAG, content);
                                    Log.v(TAG, "print content");
                                } else {
                                    Log.v(TAG, "null return!");
                                }

                                if ((content != null) && (content.equals("OK")) && (errCnt < 10)) {
                                    sendShowClientThreadMsg("接收返回帧:"+content);
                                } else {
                                    errCnt++;
                                    Log.v(TAG, "返回帧校验错误"+errCnt);
                                    sendShowClientThreadMsg("返回帧校验错误"+errCnt);
                                }
                            }else{
                                errCnt++;
                                Log.v(TAG, "数据读取超时错误"+errCnt);
                                sendShowClientThreadMsg("数据读取超时错误"+errCnt);
                            }

                            if (errCnt >= 10) {
                                MainStatusManage.setIsClientThreadSonRun(false);
                                //释放IO Socket 资源
                                try
                                {
                                    os.close();
                                    br.close();
                                    s.close();
                                    //关闭套接字
                                    sendShowClientThreadMsg("关闭套接字,重启连接!!");
                                }
                                catch (IOException e)
                                {
                                    Log.v(TAG, "关闭IO错误!!");
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(2000);
                            Log.v(TAG, "just sleep 2s!");
                            //关闭套接字
                            sendShowClientThreadMsg("测量数据未更新!!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//--------------------------------------------------------------
                }
            }
            catch (SocketTimeoutException e)
            {
                Log.v(TAG, "网络连接超时!!");
                sendShowClientThreadMsg("网络连接超时!!");
            }
            catch (Exception e)
            {
                Log.v(TAG, "创建网络连接失败!!" + e.getMessage());
                sendShowClientThreadMsg("创建网络连接失败!!");
                e.printStackTrace();
            }
        }
    }

    public void sendShowClientThreadMsg(String mData){
        synchronized(handler) {
            Message msg = Message.obtain(handler, Messages.ClientThread_Msg);
            msg.obj = mData;
            handler.sendMessage(msg);
        }
    }
}

