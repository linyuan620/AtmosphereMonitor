package com.skyray.atmospheremonitor;

/**
 * Created by linyuan on 2017/11/22 0022.
 */

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.kyleduo.switchbutton.SwitchButton;
import com.skyray.atmospheremonitor.Utils.ButtonUtils;
import com.skyray.atmospheremonitor.Utils.SimpleMessageShower;
import com.skyray.atmospheremonitor.controller.UploadCfgController;
import com.skyray.atmospheremonitor.datamodel.UploadCfg;

/**
 * 通信设置界面
 */
public class FragmentSetting_Upload extends FragmentBase implements View.OnClickListener{

    final String TAG = "FragmentSetting_Upload";

    public FragmentSetting_Upload() {
        layoutID = R.layout.fragment_setting_upload;
    }

    Spinner spIntervalTime = null;
    EditText etEquipmentId = null;
    EditText etIpAddress = null;
    EditText etPortNum = null;
    SwitchButton swButton = null;

    Button btnSave = null;
    Button btnCancel = null;

    @Override
    protected void onInit() {

        spIntervalTime = (Spinner) (getRootView().findViewById(R.id.sp_uploadInterval));

        ArrayAdapter<CharSequence> adapterPN = ArrayAdapter.createFromResource(
                getContext(),R.array.IntervalTime,
                android.R.layout.simple_spinner_item);
        spIntervalTime.setAdapter(adapterPN);

        etEquipmentId = (EditText) (getRootView().findViewById(R.id.ed_equipmentId));
        etIpAddress = (EditText) (getRootView().findViewById(R.id.ed_ipAddress));
        etPortNum = (EditText) (getRootView().findViewById(R.id.ed_portAddress));

        swButton = (SwitchButton)(getRootView().findViewById(R.id.sbWifiState));
        swButton.setOnClickListener(this);

        btnSave = (Button) (getRootView().findViewById(R.id.btnConfirmConnectionConfig));
        btnSave.setOnClickListener(this);
        btnCancel = (Button) (getRootView().findViewById(R.id.btnCancelConnectionConfig));
        btnCancel.setOnClickListener(this);

        loadAndShowConfig();
    }

    private void loadAndShowConfig(){
        UploadCfg cfg = UploadCfgController.GetUploadCfgController().GetUploadCfg();
        String[] intervalTime = getContext().getResources().getStringArray(R.array.IntervalTime);

        if(cfg != null){
            //确认数据库读出的数据与系统限定的是否一致
            for(int i = 0 ;i<intervalTime.length;i++){
                if(intervalTime[i].equals(String.valueOf(cfg.getUploadIntervalTime()))){
                    spIntervalTime.setSelection(i);
                    break;
                }
            }

            etPortNum.setText(String.valueOf(cfg.getPortNum()));
            etIpAddress.setText(cfg.getIp());
            etEquipmentId.setText(cfg.getEquipmentID());
            //wifiEnable setChecked 失能 Checked
            swButton.setChecked(cfg.isWifiEnabled());
        }
    }

    void saveConfig(){

        if(etPortNum.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请设置 端口号 !");
            etPortNum.requestFocus();
            return;
        }
        int portNum = Integer.parseInt(etPortNum.getText().toString().trim());

        int intervalTime = Integer.parseInt(spIntervalTime.getSelectedItem().toString());

        if(etIpAddress.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请设置 IP地址 !");
            etIpAddress.requestFocus();
            return;
        }
        String ipAddress = etIpAddress.getText().toString().trim();

        if(etEquipmentId.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请设置 设备号 !");
            etEquipmentId.requestFocus();
            return;
        }
        String equipmentId = etEquipmentId.getText().toString().trim();

        Boolean ifWifiEnable = swButton.isChecked();

        UploadCfg config = new UploadCfg(intervalTime,equipmentId,ipAddress,portNum,ifWifiEnable);

        UploadCfgController.GetUploadCfgController().SaveUploadCfg(config);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirmConnectionConfig:
                saveConfig();
                break;
            case R.id.btnCancelConnectionConfig:
                loadAndShowConfig();
                break;
            case R.id.sbWifiState:
                if (!ButtonUtils.isFastDoubleClick(R.id.sbWifiState)) {
                    //判断点击后的状态
                    if(swButton.isChecked()){
                        //unchecked 发送停止信号
                        Log.e(TAG, "checked: sbWifiState");
                    }else {
                        //unchecked 发送启动信号
                        Log.e(TAG, "unchecked: sbWifiState");
                    }
                }
                break;
            default:
                break;
        }
    }
}
