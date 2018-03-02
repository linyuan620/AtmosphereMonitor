package com.skyray.atmospheremonitor.datamodel;

import com.orm.SugarRecord;

/**
 * 标定系数
 */

public class CalibrateCoefficient extends SugarRecord {

    private double CoKValue = 1;
    private double CoBValue = 0;

    private double No2KValue = 1;
    private double No2BValue =0;

    private double So2KValue = 1;
    private double So2BValue = 0;

    private double O3KValue = 1;
    private double O3BValue = 0;

    private double H2sKValue = 1;
    private double H2sBValue = 0;

    private double TvocKValue = 1;
    private double TvocBValue = 0;

    private double TspKValue = 1;
    private double TspBValue = 0;

    private double Pm10KValue = 1;
    private double Pm10BValue = 0;

    private double Pm25KValue = 1;
    private double Pm25BValue = 0;

    public CalibrateCoefficient() {
    }

    public double getCoKValue() {
        return CoKValue;
    }

    public void setCoKValue(double coKValue) {
        CoKValue = coKValue;
    }

    public double getCoBValue() {
        return CoBValue;
    }

    public void setCoBValue(double coBValue) {
        CoBValue = coBValue;
    }

    public double getNo2KValue() {
        return No2KValue;
    }

    public void setNo2KValue(double no2KValue) {
        No2KValue = no2KValue;
    }

    public double getNo2BValue() {
        return No2BValue;
    }

    public void setNo2BValue(double no2BValue) {
        No2BValue = no2BValue;
    }

    public double getSo2KValue() {
        return So2KValue;
    }

    public void setSo2KValue(double so2KValue) {
        So2KValue = so2KValue;
    }

    public double getSo2BValue() {
        return So2BValue;
    }

    public void setSo2BValue(double so2BValue) {
        So2BValue = so2BValue;
    }

    public double getO3KValue() {
        return O3KValue;
    }

    public void setO3KValue(double o3KValue) {
        O3KValue = o3KValue;
    }

    public double getO3BValue() {
        return O3BValue;
    }

    public void setO3BValue(double o3BValue) {
        O3BValue = o3BValue;
    }

    public double getH2sKValue() {
        return H2sKValue;
    }

    public void setH2sKValue(double h2sKValue) {
        H2sKValue = h2sKValue;
    }

    public double getH2sBValue() {
        return H2sBValue;
    }

    public void setH2sBValue(double h2sBValue) {
        H2sBValue = h2sBValue;
    }

    public double getTvocKValue() {
        return TvocKValue;
    }

    public void setTvocKValue(double tvocKValue) {
        TvocKValue = tvocKValue;
    }

    public double getTvocBValue() {
        return TvocBValue;
    }

    public void setTvocBValue(double tvocBValue) {
        TvocBValue = tvocBValue;
    }

    public double getTspKValue() {
        return TspKValue;
    }

    public void setTspKValue(double tspKValue) {
        TspKValue = tspKValue;
    }

    public double getTspBValue() {
        return TspBValue;
    }

    public void setTspBValue(double tspBValue) {
        TspBValue = tspBValue;
    }

    public double getPm10KValue() {
        return Pm10KValue;
    }

    public void setPm10KValue(double pm10KValue) {
        Pm10KValue = pm10KValue;
    }

    public double getPm10BValue() {
        return Pm10BValue;
    }

    public void setPm10BValue(double pm10BValue) {
        Pm10BValue = pm10BValue;
    }

    public double getPm25KValue() {
        return Pm25KValue;
    }

    public void setPm25KValue(double pm25KValue) {
        Pm25KValue = pm25KValue;
    }

    public double getPm25BValue() {
        return Pm25BValue;
    }

    public void setPm25BValue(double pm25BValue) {
        Pm25BValue = pm25BValue;
    }
}
