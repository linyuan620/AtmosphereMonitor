package com.skyray.atmospheremonitor;

/**
 * Created by linyuan on 2017/11/22 0022.
 */

import android.view.View;
import android.widget.Button;

/**
 * 亮屏设置界面
 */
public class FragmentSetting_MeasureUnit extends FragmentBase implements View.OnClickListener{

    final String TAG = "FragmentSetting_MeasureUnit";

    public FragmentSetting_MeasureUnit() {
        layoutID = R.layout.fragment_setting_measureunit;
    }

    Button btnSave = null;
    Button btnCancel = null;

    @Override
    protected void onInit() {

        btnSave = (Button) (getRootView().findViewById(R.id.btnConfirmConnectionConfig));
        btnSave.setOnClickListener(this);
        btnCancel = (Button) (getRootView().findViewById(R.id.btnCancelConnectionConfig));
        btnCancel.setOnClickListener(this);

        loadAndShowConfig();
    }

    private void loadAndShowConfig(){

    }

    void saveConfig(){

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
