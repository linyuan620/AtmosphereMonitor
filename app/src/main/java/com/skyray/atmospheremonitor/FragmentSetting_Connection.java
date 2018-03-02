package com.skyray.atmospheremonitor;

/**
 * Created by linyuan on 2017/11/22 0022.
 */

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.skyray.atmospheremonitor.Utils.SimpleMessageShower;
import com.skyray.atmospheremonitor.controller.ConnectionCfgController;
import com.skyray.atmospheremonitor.datamodel.ConnectionCfg;


/**
 * 通信设置界面
 */
public class FragmentSetting_Connection extends FragmentBase implements View.OnClickListener{

    public FragmentSetting_Connection() {
        layoutID = R.layout.fragment_setting_connection;
    }

    Spinner spPortName = null;
    Spinner spBaudRate = null;
    EditText etSlaveID = null;
    Button btnSave = null;
    Button btnCancel = null;

    @Override
    protected void onInit() {
        spPortName = (Spinner) (getRootView().findViewById(R.id.sc_PortName));
        spBaudRate = (Spinner) (getRootView().findViewById(R.id.sc_BaudRate));
        etSlaveID = (EditText) (getRootView().findViewById(R.id.sc_SlaveID));
        btnSave = (Button) (getRootView().findViewById(R.id.btnConfirmConnectionConfig));
        btnSave.setOnClickListener(this);
        btnCancel = (Button) (getRootView().findViewById(R.id.btnCancelConnectionConfig));
        btnCancel.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapterPN = ArrayAdapter.createFromResource(
               getContext(),R.array.PortName,
                android.R.layout.simple_spinner_item);
        spPortName.setAdapter(adapterPN);

        ArrayAdapter<CharSequence> adapterBR = ArrayAdapter.createFromResource(
          getContext(),R.array.BaudRate,
                android.R.layout.simple_spinner_item);
        spBaudRate.setAdapter(adapterBR);

        loadAndShowConfig();
    }

    private void loadAndShowConfig(){
        ConnectionCfg cfg = ConnectionCfgController.GetConnectionCfgController().GetConnectionCfg();
        String[] portNames = getContext().getResources().getStringArray(R.array.PortName);
        String[] baudRates = getContext().getResources().getStringArray(R.array.BaudRate);

        if(cfg != null){
            //确认数据库读出的数据与系统限定的是否一致
            for(int i = 0 ;i<portNames.length;i++){
                if(portNames[i].equals(cfg.getComPort())){
                    spPortName.setSelection(i);
                    break;
                }
            }

            //确认数据库读出的数据与系统限定的是否一致
            for (int i=0;i<baudRates.length;i++){
                if(baudRates[i].equals(String.valueOf(cfg.getBautRate()))){
                    spBaudRate.setSelection(i);
                    break;
                }
            }

            etSlaveID.setText(String.valueOf(cfg.getSlaveID()));
        }
    }

    void saveConfig(){
        int slaveID = 0;
        if(etSlaveID.getText().toString().trim().equals("")){
            SimpleMessageShower.showMsg(getContext(),"请设置 从机地址 !");
            etSlaveID.requestFocus();
            return;
        }
        slaveID = Integer.parseInt(etSlaveID.getText().toString().trim());

        String portName = spPortName.getSelectedItem().toString();
        int baudRate = Integer.parseInt(spBaudRate.getSelectedItem().toString());

        ConnectionCfg config = new ConnectionCfg(slaveID,portName,baudRate);
        ConnectionCfgController.GetConnectionCfgController().SaveConnectionCfg(config);
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
            default:
                break;
        }
    }
}
