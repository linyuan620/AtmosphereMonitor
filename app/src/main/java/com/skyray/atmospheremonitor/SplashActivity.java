package com.skyray.atmospheremonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.skyray.atmospheremonitor.Utils.FullScreenController;

public class SplashActivity extends Activity {

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		loadMainUI();
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
		//this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(3000);
		findViewById(R.id.rl_splash_root).startAnimation(aa);

		connectTask();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			FullScreenController.hideNavigationBar(this);
		}
	}

	private void loadMainUI() {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	public void connectTask() {
		MyTask task = new MyTask();
		task.execute();
	}
	private class MyTask extends AsyncTask {

		@Override
		protected String doInBackground(Object... params) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			loadMainUI();
		}
		
	}
}
