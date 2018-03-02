package com.skyray.atmospheremonitor.datamodel;

import com.orm.SugarRecord;
import com.skyray.atmospheremonitor.Utils.DateTimeFormater;
import com.skyray.atmospheremonitor.Utils.NumberPointManage;

import java.util.Date;

/**
 * Created by linyuan on 2017/12/6 0006.
 */

public class MeasureData extends SugarRecord {

    /**
     * 测量时间
     */
    private Date MeasureTime = new Date();

    private String DateString;

    /**
     * 风速值
     */
    private double windSpeed;

    /**
     * 风向
     */
    private double windDir;

    private double temperature;

    private double humidity;

    private double pressure;

    private double tsp;

    private double pm10;

    private double pm25;

    private double noise;

    private double co;

    private double no2;

    private double so2;

    private double o3;

    private double h2s;

    private double tvoc;

    public MeasureData() {
    }

    public MeasureData(Date measureTime, double windSpeed, double windDir,
                       double temperature, double humidity, double pressure,
                       double tsp, double pm10, double pm25, double noise,
                       double co, double no2, double so2, double o3, double tvoc) {
        MeasureTime = measureTime;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.tsp = tsp;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.noise = noise;
        this.co = co;
        this.no2 = no2;
        this.so2 = so2;
        this.o3 = o3;
        this.tvoc = tvoc;
    }

    public Date getMeasureTime() {
        return MeasureTime;
    }

    public void setMeasureTime(Date measureTime) {
        MeasureTime = measureTime;
    }

    public String getDateString() {
        return DateString;
    }

    public void setDateString(String dateString) {
        DateString = dateString;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDir() {
        return windDir;
    }

    public void setWindDir(double windDir) {
        this.windDir = windDir;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTsp() {
        return tsp;
    }

    public void setTsp(double tsp) {
        this.tsp = tsp;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public double getNoise() {
        return noise;
    }

    public void setNoise(double noise) {
        this.noise = noise;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getNo2() {
        return no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getSo2() {
        return so2;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getH2s() {
        return h2s;
    }

    public void setH2s(double h2s) {
        this.h2s = h2s;
    }

    public double getTvoc() {
        return tvoc;
    }

    public void setTvoc(double tvoc) {
        this.tvoc = tvoc;
    }

    @Override
    public String toString() {
        return DateTimeFormater.getTimeString(MeasureTime) + "\t\t"
                + NumberPointManage.toString(windSpeed,1) +"\t\t"
                + NumberPointManage.toString(windDir,1)+"\t\t"
                + NumberPointManage.toString(temperature,1)+"\t\t"
                + NumberPointManage.toString(humidity,1)+"\t\t"
                + NumberPointManage.toString(pressure,1)+"\t\t"
                + NumberPointManage.toString(tsp,1)+"\t\t"
                + NumberPointManage.toString(pm10,1)+"\t\t"
                + NumberPointManage.toString(pm25,1)+"\t\t"
                + NumberPointManage.toString(noise,1)+"\t\t"
                + NumberPointManage.toString(co,1)+"\t\t"
                + NumberPointManage.toString(no2,1)+"\t\t"
                + NumberPointManage.toString(so2,1)+"\t\t"
                + NumberPointManage.toString(o3,1)+"\t\t"
                + NumberPointManage.toString(h2s,1)+"\t\t"
                + NumberPointManage.toString(tvoc,1)+"\t\t";
    }
}
