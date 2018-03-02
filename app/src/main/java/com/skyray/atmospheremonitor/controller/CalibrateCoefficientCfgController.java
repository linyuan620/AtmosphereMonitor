package com.skyray.atmospheremonitor.controller;

import com.skyray.atmospheremonitor.datamodel.CalibrateCoefficient;
import java.util.List;


public class CalibrateCoefficientCfgController {
    private CalibrateCoefficient CFG = new CalibrateCoefficient();

    public CalibrateCoefficient GetCalibrateCoefficient() {
        return CFG;
    }

    private static CalibrateCoefficientCfgController _CFGCtrl = null;

    /**
     * 加载配置并获取之
     *
     * @return
     */
    public static CalibrateCoefficientCfgController GetCalibrateCoefficientController() {
        if (_CFGCtrl == null) {
            _CFGCtrl = new CalibrateCoefficientCfgController();
            List<CalibrateCoefficient> allConfigs = CalibrateCoefficient.listAll(CalibrateCoefficient.class);
            if (allConfigs != null && allConfigs.size() > 0) {
                _CFGCtrl.CFG = allConfigs.get(0);
            }
            if (_CFGCtrl.CFG == null) {
                _CFGCtrl.CFG = new CalibrateCoefficient();
            }
        }
        return _CFGCtrl;
    }

    public void SaveCalibrateCoefficient(CalibrateCoefficient CalibrateCoefficient) {
        CFG.setCoKValue(CalibrateCoefficient.getCoKValue());
        CFG.setCoBValue(CalibrateCoefficient.getCoBValue());

        CFG.setNo2KValue(CalibrateCoefficient.getNo2KValue());
        CFG.setNo2BValue(CalibrateCoefficient.getNo2BValue());

        CFG.setSo2KValue(CalibrateCoefficient.getSo2KValue());
        CFG.setSo2BValue(CalibrateCoefficient.getSo2BValue());

        CFG.setO3KValue(CalibrateCoefficient.getO3KValue());
        CFG.setO3BValue(CalibrateCoefficient.getO3BValue());

        CFG.setH2sKValue(CalibrateCoefficient.getH2sKValue());
        CFG.setH2sBValue(CalibrateCoefficient.getH2sBValue());

        CFG.setTvocKValue(CalibrateCoefficient.getTvocKValue());
        CFG.setTvocBValue(CalibrateCoefficient.getTvocBValue());

        CFG.setTspKValue(CalibrateCoefficient.getTspKValue());
        CFG.setTspBValue(CalibrateCoefficient.getTspBValue());

        CFG.setPm10KValue(CalibrateCoefficient.getPm10KValue());
        CFG.setPm10BValue(CalibrateCoefficient.getPm10BValue());

        CFG.setPm25KValue(CalibrateCoefficient.getPm25KValue());
        CFG.setPm25BValue(CalibrateCoefficient.getPm25BValue());

        CFG.save();
    }
}
