package com.skyray.atmospheremonitor;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.skyray.atmospheremonitor.Utils.AlertDialogManager;
import com.skyray.atmospheremonitor.Utils.NumberPointManage;
import com.skyray.atmospheremonitor.Utils.SimpleMessageShower;
import com.skyray.atmospheremonitor.controller.CalibrateCoefficientCfgController;
import com.skyray.atmospheremonitor.datamodel.CalibrateCoefficient;

/**
 * 手动操作界面
 */

public class FragmentMaintain_HandCalibrate extends FragmentBase implements View.OnClickListener{

    public FragmentMaintain_HandCalibrate() {
        layoutID = R.layout.fragment_maintain_handcalibrate;
    }

    String strBtnSelected = ""; //记录选择的是哪个选项

    private RadioGroup rg1, rg2;
    private RadioButton rb_co, rb_no2, rb_so2, rb_o3, rb_h2s, rb_tvoc, rb_tsp, rb_pm10, rb_pm25;

    private EditText standardOne ;
    private EditText standardTwo ;

    private EditText sampleOne ;
    private EditText sampleTwo ;

    private EditText curK ;
    private EditText curB ;

    private EditText calK ;
    private EditText calB ;

    private Button btnCal;
    private Button btnReplace;

    private CalibrateCoefficient coefficient;

    double standardOneValue;
    double standardTwoValue;
    double sampleOneValue;
    double sampleTwoValue;
    double calKValue;
    double calBValue;

    boolean isCalcuted = false;

    @Override
    protected void onInit() {
        rg1 = (RadioGroup) (getRootView().findViewById(R.id.rg1));
        rg2 = (RadioGroup) (getRootView().findViewById(R.id.rg2));

        rb_co = (RadioButton) (getRootView().findViewById(R.id.rb_co));
        rb_no2 = (RadioButton) (getRootView().findViewById(R.id.rb_no2));
        rb_so2 = (RadioButton) (getRootView().findViewById(R.id.rb_so2));
        rb_o3 = (RadioButton)  (getRootView().findViewById(R.id.rb_o3));
        rb_h2s = (RadioButton) (getRootView().findViewById(R.id.rb_h2s));
        rb_tvoc = (RadioButton) (getRootView().findViewById(R.id.rb_tvoc));
        rb_tsp = (RadioButton) (getRootView().findViewById(R.id.rb_tsp));
        rb_pm10 = (RadioButton) (getRootView().findViewById(R.id.rb_pm10));
        rb_pm25 = (RadioButton) (getRootView().findViewById(R.id.rb_pm25));

        //创建监听器，为每个RadioButton注册监听

        BtnSelected btnSelected1 = new BtnSelected("1");
        BtnSelected btnSelected2 = new BtnSelected("2");
        BtnSelected btnSelected3 = new BtnSelected("3");
        BtnSelected btnSelected4 = new BtnSelected("4");
        BtnSelected btnSelected5 = new BtnSelected("5");
        BtnSelected btnSelected6 = new BtnSelected("6");
        BtnSelected btnSelected7 = new BtnSelected("7");
        BtnSelected btnSelected8 = new BtnSelected("8");
        BtnSelected btnSelected9 = new BtnSelected("9");

        rb_co.setOnClickListener(btnSelected1);
        rb_no2.setOnClickListener(btnSelected2);
        rb_so2.setOnClickListener(btnSelected3);
        rb_o3.setOnClickListener(btnSelected4);
        rb_h2s.setOnClickListener(btnSelected5);
        rb_tvoc.setOnClickListener(btnSelected6);
        rb_tsp.setOnClickListener(btnSelected7);
        rb_pm10.setOnClickListener(btnSelected8);
        rb_pm25.setOnClickListener(btnSelected9);

        standardOne = (EditText)getRootView().findViewById(R.id.etStandardOne);
        standardTwo = (EditText)getRootView().findViewById(R.id.etStandardTwo);
        sampleOne = (EditText)getRootView().findViewById(R.id.etSample1);
        sampleTwo = (EditText)getRootView().findViewById(R.id.etSample2);

        standardOne.setText("50");
        sampleOne.setText("50");

        standardTwo.setText("500");
        sampleTwo.setText("500");

        curK = (EditText)getRootView().findViewById(R.id.etCurrentK);
        curB = (EditText)getRootView().findViewById(R.id.etCurrentB);

        calK = (EditText)getRootView().findViewById(R.id.etCalibrateK);
        calB = (EditText)getRootView().findViewById(R.id.etCalibrateB);

        btnCal = (Button)getRootView().findViewById(R.id.btnCalculateKB);
        btnReplace = (Button)getRootView().findViewById(R.id.btnUpdateKB);

        btnCal.setOnClickListener(this);
        btnReplace.setOnClickListener(this);

        coefficient = CalibrateCoefficientCfgController.GetCalibrateCoefficientController().GetCalibrateCoefficient();
    }

    //点击事件的监听器
    public class BtnSelected implements View.OnClickListener {

        private String btnId;

        public BtnSelected(String str) {
            btnId = str;
        }

        @Override
        public void onClick(View v) {
            strBtnSelected = btnId;//选择的某一项
            //点击了第一行　，就把另外行的点击项清空
            if (btnId.equals("1") || btnId.equals("2") || btnId.equals("3") || btnId.equals("4")|| btnId.equals("5")) {
                rg2.clearCheck();
            } else if (btnId.equals("6") || btnId.equals("7") || btnId.equals("8")|| btnId.equals("9")) {
                rg1.clearCheck();
            }
        }
    }


    private void calculateCoefficient(){
        coefficient = CalibrateCoefficientCfgController.GetCalibrateCoefficientController().GetCalibrateCoefficient();

        if( getSamValue() ){
            isCalcuted = true;
            int btnSelcetedValue = Integer.valueOf(strBtnSelected).intValue();
            switch (btnSelcetedValue){
                case 1:
                    curK.setText(NumberPointManage.toString(coefficient.getCoKValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getCoBValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getCoKValue() , coefficient.getCoBValue());
                    break;
                case 2:
                    curK.setText(NumberPointManage.toString(coefficient.getNo2KValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getNo2BValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getNo2KValue() , coefficient.getNo2BValue());
                    break;
                case 3:
                    curK.setText(NumberPointManage.toString(coefficient.getSo2KValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getSo2BValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getSo2KValue() , coefficient.getSo2BValue());
                    break;
                case 4:
                    curK.setText(NumberPointManage.toString(coefficient.getO3KValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getO3BValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getO3KValue() , coefficient.getO3BValue());
                    break;
                case 5:
                    curK.setText(NumberPointManage.toString(coefficient.getH2sKValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getH2sBValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getH2sKValue() , coefficient.getH2sBValue());
                    break;
                case 6:
                    curK.setText(NumberPointManage.toString(coefficient.getTvocKValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getTvocBValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getTvocKValue() , coefficient.getTvocBValue());
                    break;
                case 7:
                    curK.setText(NumberPointManage.toString(coefficient.getTspKValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getTspBValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getTspKValue() , coefficient.getTspBValue());
                    break;
                case 8:
                    curK.setText(NumberPointManage.toString(coefficient.getPm10KValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getPm10BValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getPm10KValue() , coefficient.getPm10BValue());
                    break;
                case 9:
                    curK.setText(NumberPointManage.toString(coefficient.getPm25KValue(),2));
                    curB.setText(NumberPointManage.toString(coefficient.getPm25BValue(),2));

                    calculateKB( standardOneValue, standardTwoValue, sampleOneValue, sampleTwoValue,
                            coefficient.getPm25KValue() , coefficient.getPm25BValue());
                    break;
                default:
                    break;
            }
        }
    }

    private void replaceCoefficient(){

        if( getCalValue() ){
            AlertDialogManager.showDialog(getContext(), "确定替换?",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    calK.setText(curK.getText().toString().trim());
                    calB.setText(curB.getText().toString().trim());
                    isCalcuted = false;
                }
            },
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
            //                MyLogManager.addLogInfo("程序退出");
                    int btnSelcetedValue = Integer.valueOf(strBtnSelected).intValue();
                    switch (btnSelcetedValue){
                        case 1:
                            coefficient.setCoKValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setCoBValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        case 2:
                            coefficient.setNo2KValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setNo2BValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        case 3:
                            coefficient.setSo2KValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setSo2BValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        case 4:
                            coefficient.setO3KValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setO3BValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        case 5:
                            coefficient.setH2sKValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setH2sBValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        case 6:
                            coefficient.setTvocKValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setTvocBValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        case 7:
                            coefficient.setTspKValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setTspBValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        case 8:
                            coefficient.setPm10KValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setPm10BValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        case 9:
                            coefficient.setPm25KValue(Double.valueOf(calK.getText().toString().trim()));
                            coefficient.setPm25BValue(Double.valueOf(calB.getText().toString().trim()));
                            break;
                        default:
                            break;
                    }
                    CalibrateCoefficientCfgController.
                            GetCalibrateCoefficientController().
                            SaveCalibrateCoefficient(coefficient);
                }
            });
        }
    }

    void calculateKB(double standardOneValue,double standardTwoValue,
                     double sampleOneValue,double sampleTwoValue,
                     double curKValue,double curBValue)
    {
        if(sampleTwoValue == sampleOneValue){
            SimpleMessageShower.showMsg(getContext(),"请设置不相等的两个样品采样点 !");
            sampleTwo.requestFocus();
        }else{
            calKValue = curKValue *((standardTwoValue - standardOneValue)/(sampleTwoValue -sampleOneValue));
            calBValue = standardOneValue - calKValue * (sampleOneValue/curKValue - curBValue);

            calK.setText(NumberPointManage.toString(calKValue,2));
            calB.setText(NumberPointManage.toString(calBValue,2));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCalculateKB:
                if(!isCalcuted) {
                    calculateCoefficient();
                }else{
                    SimpleMessageShower.showMsg(getContext(),"请确认是否替换上次计算结果 !");
                }
                break;
            case R.id.btnUpdateKB:
                 replaceCoefficient();
                break;
            default:
                break;
        }
    }

    boolean getSamValue(){
        if(strBtnSelected.equals("")){
            SimpleMessageShower.showMsg(getContext(),"请选择标定物 !");
            return false;
        }

        if (standardOne.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请输入标样一 !");
            standardOne.requestFocus();
            return false;
        }else{
            standardOneValue = Double.valueOf(standardOne.getText().toString().trim());
        }

        if(standardTwo.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请输入标样二 !");
            standardTwo.requestFocus();
            return false;
        }else{
            standardTwoValue = Double.valueOf(standardTwo.getText().toString().trim());
        }

        if (sampleOne.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请输入样品一 !");
            sampleOne.requestFocus();
            return false;
        }else{
            sampleOneValue = Double.valueOf(sampleOne.getText().toString().trim());
        }

        if (sampleTwo.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请输入样品二 !");
            sampleTwo.requestFocus();
            return false;
        }else{
            sampleTwoValue = Double.valueOf(sampleTwo.getText().toString().trim());
        }
        return true;
    }

    boolean getCalValue(){

        if(!getSamValue()){
           return false;
        }

        if(calK.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请填充标定K值 !");
            calK.requestFocus();
            return false;
        }

        if(calB.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请填充标定B值 !");
            calB.requestFocus();
            return false;
        }
        return true;
    }
}
