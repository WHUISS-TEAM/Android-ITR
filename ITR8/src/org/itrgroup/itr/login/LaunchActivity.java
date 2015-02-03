package org.itrgroup.itr.login;

import org.itrgroup.itr.R;
import org.itrgroup.itr.main.MainActivity;
import org.itrgroup.itr.utils.AppConfig;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

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
		//判断程序是否是第一次启动
		//创建名为first_or_not的xml文件，也可以改成用来统计程序启动次数的文件
		SharedPreferences check = getSharedPreferences("first_or_not", 0);
		boolean first = check.getBoolean("first", true);
		if(first){
			check.edit().putBoolean("first", false).commit();
			new ini_Thread().start();
		}
		
		//在LaunchActivity界面等待0.888秒
		handler.postDelayed(new Runnable() {
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//如果已经登录过则自动跳转到MainActivity，否则回到LoginActivity
				if(sp.getBoolean("IS_CHECKED", false)){
					startActivity(new Intent(LaunchActivity.this, MainActivity.class));
					//增加了finish，在跳转完成后按返回键无法返回等待界面
					finish();
				}else{
					startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
					finish();
				}
			}
		}, 888);

	}
	
	private class ini_Thread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//在data/data/<package_name>下创建app_database文件夹
			getDir("database",MODE_PRIVATE);
			//创建数据库文件并关闭数据库
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(AppConfig.DATABASE_PATH, null);
			//创建一级标签 数据表
			String pub_tag_level1 = "CREATE TABLE pub_tag_level1 ("
					  + "tag_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
					  + "tag_name VARCHAR(50) NOT NULL"
					  + ")";
			//创建用户标签 数据表
			String user_tag ="CREATE TABLE user_tag ("
					+ "tag_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
					+ "tag_name VARCHAR(50) NOT NULL"
					+ ")";
			//创建用户资料 数据表
			String profile = "CREATE TABLE profile ("
					+ "profile_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
					+ "email VARCHAR(50) NOT NULL,"
					+ "username VARCHAR(50) NOT NULL,"
					+ "avatar VARCHAR(50) NOT NULL DEFAULT 'avatar_1.png',"  //默认是avatar_1.png
					+ "sex SMALLINT NOT NULL DEFAULT '2',"  // 1 for male, 0 for female, 3 for unknown
					+ "age SMALLINT NOT NULL DEFAULT '0',"
					+ "school VARCHAR(50),"
					+ "major VARCHAR(50),"
					+ "introduction VARCHAR(100),"
					+ "experience VARCHAR(200)"
					+ ")";
			//创建用户标签关系 数据表
			String user_tag_link = "CREATE TABLE user_tag_link ("
					+ "link_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
					+ "profile_id integer,"
					+ "tag_id SMALLINT,"
					+ "FOREIGN KEY(profile_id) REFERENCES profile (profile_id)"
					+ ")";
			
			try {
				database.execSQL(pub_tag_level1);
				database.execSQL(user_tag);
				
				for(String index : AppConfig.pub_tag_level1){
					ContentValues values = new ContentValues();
					values.put("tag_name", index);
					database.insert("pub_tag_level1", null, values);
				}
				for (String index : AppConfig.user_tag_items) {
					ContentValues values = new ContentValues();
					values.put("tag_name", index);
					database.insert("user_tag", null, values);
				}
				
				database.execSQL(profile);
				database.execSQL(user_tag_link);
				
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("fail");
			}finally{
				database.close();
			}
		}
	}

}
