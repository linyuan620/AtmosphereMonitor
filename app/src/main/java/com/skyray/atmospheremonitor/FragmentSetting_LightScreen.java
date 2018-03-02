package com.skyray.atmospheremonitor;

/**
 * Created by linyuan on 2017/11/22 0022.
 */

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.skyray.atmospheremonitor.controller.LightScreenController;
import com.skyray.atmospheremonitor.datamodel.LightScreenCfg;

/**
 * 亮屏设置界面
 */
public class FragmentSetting_LightScreen extends FragmentBase implements View.OnClickListener{

    final String TAG = "FragmentSetting_LightScreen";

    public FragmentSetting_LightScreen() {
        layoutID = R.layout.fragment_setting_lightscreen;
    }

    Spinner spLightScreenTime = null;

    Button btnSave = null;
    Button btnCancel = null;

    @Override
    protected void onInit() {

        spLightScreenTime = (Spinner) (getRootView().findViewById(R.id.sp_lightScreenTime));

        ArrayAdapter<CharSequence> adapterPN = ArrayAdapter.createFromResource(
                getContext(),R.array.LightScreenTime,
                android.R.layout.simple_spinner_item);
        spLightScreenTime.setAdapter(adapterPN);

        btnSave = (Button) (getRootView().findViewById(R.id.btnConfirmConnectionConfig));
        btnSave.setOnClickListener(this);
        btnCancel = (Button) (getRootView().findViewById(R.id.btnCancelConnectionConfig));
        btnCancel.setOnClickListener(this);

        loadAndShowConfig();
    }

    private void loadAndShowConfig(){
        LightScreenCfg cfg = LightScreenController.GetLightScreenCfg();

        String[] lightScreenTime = getContext().getResources().getStringArray(R.array.LightScreenTime);

        if(cfg != null){
            //确认数据库读出的数据与系统限定的是否一致 去除最后一项
            for(int i = 0 ;i<lightScreenTime.length-1;i++){
                if(lightScreenTime[i].equals(String.valueOf(cfg.getLightScreenTime()))){
                    spLightScreenTime.setSelection(i);
                    break;
                }
            }
            //从不数据库中存入0 lightScreenTime =0
            if( 0 == cfg.getLightScreenTime() ){
                spLightScreenTime.setSelection(lightScreenTime.length -1);
            }

        }
    }

    void saveConfig(){

        int intervalTime = 0;

        if( ! "永不关屏".equals(spLightScreenTime.getSelectedItem().toString()) ){
           intervalTime = Integer.parseInt(spLightScreenTime.getSelectedItem().toString());
        }

        LightScreenCfg config = new LightScreenCfg(intervalTime);

        LightScreenController.SaveLightScreenConfig(config);
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
