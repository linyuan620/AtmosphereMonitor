package com.skyray.atmospheremonitor.datamodel;


import com.orm.SugarRecord;

/**
 * Created by linyuan on 2017/11/14 0014.
 */

public class LightScreenCfg extends SugarRecord {

    private int lightScreenTime = 300;

    public LightScreenCfg() {
    }

    public LightScreenCfg(int lightScreenTime) {
        this.lightScreenTime = lightScreenTime;
    }

    public int getLightScreenTime() {
        return lightScreenTime;
    }

    public void setLightScreenTime(int lightScreenTime) {
        this.lightScreenTime = lightScreenTime;
    }
}
