package com.skyray.atmospheremonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.skyray.atmospheremonitor.Utils.FullScreenController;
import com.skyray.atmospheremonitor.Utils.SimpleMessageShower;

public class ActivityLogin extends BaseActivity implements View.OnClickListener {

    final String userName = "user";
    final String userPassword = "123456";
    final String adminName = "admin";
    final String adminPassword = "456789";

    /**
     * 切换显示子窗体方法
     * @param c 子窗体方法
     */
    public void showChildActivity(Class c){
        Intent intent = new Intent(ActivityLogin.this,c);
        startActivity(intent);
    }

    /**
     * 显示仪表维护界面
     * @param
     */
    public void showMaintainActivity(){
        showChildActivity(ActivityMaintain.class);
    }

    /**
     * 显示设置界面
     * @param
     */
    public void showSettingActivity(){
        showChildActivity(ActivitySetting.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        initAllControls();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLogin.this.finish();
            }
        });
    }

    private Button btnLogin;
    private EditText etUserName;
    private EditText etUserPassword;

    private void initAllControls() {
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        etUserName = (EditText)findViewById(R.id.etUserName);
        etUserPassword = (EditText)findViewById(R.id.etUserPassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                verifyPassword();
                break;
            default:
                break;
        }
    }

    private void verifyPassword(){

        if (getIntent().getAction() == ActivityMaintain.START_MAINTAIN_ACTION){
            if(etUserName.getText().toString().equals(adminName)){
                if(etUserPassword.getText().toString().equals(adminPassword)){
                    showMaintainActivity();
                    ActivityLogin.this.finish();
                }else {
                    SimpleMessageShower.showMsg(this,"请输入正确的密码!");
                }
            }else{
                SimpleMessageShower.showMsg(this,"请输入正确的用户名!");
            }
        }else if(getIntent().getAction() == ActivitySetting.START_SETTING_ACTION){
            if(etUserName.getText().toString().equals(userName)){
                if(etUserPassword.getText().toString().equals(userPassword)){
                    showSettingActivity();
                    ActivityLogin.this.finish();
                }else {
                    SimpleMessageShower.showMsg(this,"请输入正确的密码!");
                }
            }else{
                SimpleMessageShower.showMsg(this,"请输入正确的用户名!");
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            FullScreenController.hideNavigationBar(this);
        }
    }

}
