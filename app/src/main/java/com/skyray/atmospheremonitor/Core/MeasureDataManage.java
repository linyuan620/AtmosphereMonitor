package com.skyray.atmospheremonitor.Core;

import com.skyray.atmospheremonitor.Utils.DateTimeFormater;
import com.skyray.atmospheremonitor.datamodel.MeasureData;

import java.util.Date;
import java.util.List;



/**
 * Created by linyuan on 2017/11/15 0015.
 */

public class MeasureDataManage {

    public static void addMeasureData(MeasureData mdata){
        if(mdata != null){
            mdata.save();
        }
    }

    public List<MeasureData> getAllLogInfo(){
        return MeasureData.listAll(MeasureData.class);
    }

    public List<MeasureData> getLogInfoByTime(Date begin , Date end){
        return MeasureData.find(MeasureData.class,"Measure_Time between ? and ?",
                DateTimeFormater.getTimeString(begin), DateTimeFormater.getTimeString(end));
    }

}
