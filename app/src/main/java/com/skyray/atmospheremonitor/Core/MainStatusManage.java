package com.skyray.atmospheremonitor.Core;

/**
 * Created by linyuan on 2017/12/6 0006.
 */

public class MainStatusManage {

    /**
     * 是否停止测量
     */

    private static boolean ifStopOperation = false;

    private static boolean isClientThreadMainRun = false;

    private static boolean isClientThreadSonRun = false;

    private static boolean isMeasureUpdated = false;

    private static String upLoadFrameData = null;

    public static boolean isIfStopOperation() {
        return ifStopOperation;
    }

    public static void setIfStopOperation(boolean ifStopOperation) {
        MainStatusManage.ifStopOperation = ifStopOperation;
    }

    public static boolean isIsClientThreadMainRun() {
        return isClientThreadMainRun;
    }

    public static void setIsClientThreadMainRun(boolean isClientThreadMainRun) {
        MainStatusManage.isClientThreadMainRun = isClientThreadMainRun;
    }

    public static boolean isIsClientThreadSonRun() {
        return isClientThreadSonRun;
    }

    public static void setIsClientThreadSonRun(boolean isClientThreadSonRun) {
        MainStatusManage.isClientThreadSonRun = isClientThreadSonRun;
    }

    public static boolean isIsMeasureUpdated() {
        return isMeasureUpdated;
    }

    public static void setIsMeasureUpdated(boolean isMeasureUpdated) {
        MainStatusManage.isMeasureUpdated = isMeasureUpdated;
    }

    public static String getUpLoadFrameData() {
        return upLoadFrameData;
    }

    public static void setUpLoadFrameData(String upLoadFrameData) {
        MainStatusManage.upLoadFrameData = upLoadFrameData;
    }
}
