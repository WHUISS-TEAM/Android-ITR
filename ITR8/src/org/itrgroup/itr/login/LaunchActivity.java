package org.itrgroup.itr.login;

import org.itrgroup.itr.R;
import org.itrgroup.itr.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class LaunchActivity  extends Activity {

	//用于设置自动登陆
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		
		sp = this.getSharedPreferences("userInfo", this.MODE_WORLD_READABLE);
		Handler handler = new Handler();
		
		//在LaunchActivity界面等待0.888秒
		handler.postDelayed(new Runnable() {
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//如果已经登录过则自动跳转到MainActivity，否则回到LoginActivity
				if(sp.getBoolean("IS_CHECKED", false)){
					startActivity(new Intent(LaunchActivity.this, MainActivity.class));
				}else{
					startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
				}
			}
		}, 888);

	}

}
