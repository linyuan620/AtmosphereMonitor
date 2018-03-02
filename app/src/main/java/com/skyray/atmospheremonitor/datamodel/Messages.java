package com.skyray.atmospheremonitor.datamodel;

/**
 * Created by linyuan on 2017/11/14 0014.
 */

public class Messages {

    /**
     * 更新当前你系统时间消息
     */
    public final static int ShowSystemTime_Msg = 0;
    /**
     * 显示测量状态消息
     */
    public final static int Measure_Msg = 1;
    /**
     * 标定状态消息
     */
    public final static int CalibrateStatus_Msg = 2;
    /**
     * 执行动作或动作完成
     */
    public final static int ExcuteHandControlOver = 3;

    /**
     * 上传服务子线程消息
     */
    public final static int ClientThread_Msg = 4;

    /**
     * 背板读取服务子线程消息
     */
    public final static int GetterThread_Msg = 5;

    /**
     * 屏幕控制消息
     */
    public final static int ScreenControl_Msg = 6;

}
