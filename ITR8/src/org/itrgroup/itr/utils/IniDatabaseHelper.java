package org.itrgroup.itr.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class IniDatabaseHelper extends SQLiteOpenHelper{

	public IniDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("craete tables");
		//建表语句
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
				+ "profile_id integer NOT NULL,"
				+ "email VARCHAR(50) NOT NULL,"
				+ "username VARCHAR(50) NOT NULL,"
				+ "userhead VARCHAR(50) NOT NULL DEFAULT 'avatar_1.png',"  //默认是avatar_1.png
				+ "sex SMALLINT NOT NULL DEFAULT '2',"  // 1 for male, 0 for female, 2 for unknown
				+ "age SMALLINT NOT NULL DEFAULT '0',"
				+ "school VARCHAR(50),"
				+ "major VARCHAR(50),"
				+ "introduction VARCHAR(100),"
				+ "experience VARCHAR(200)"
				+ ")";
		//创建用户标签关系 数据表
		String user_tag_link = "CREATE TABLE user_tag_link ("
				+ "link_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "profile_id integer NOT NULL,"
				+ "tag_id SMALLINT NOT NULL,"
				+ "FOREIGN KEY(profile_id) REFERENCES profile (profile_id)"
				+ ")";
		//为用户创建接收到消息的数据表
		String create_sql = "CREATE TABLE Received ("
				+ "Pub_inforId integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "Pub_userName varchar(50) NOT NULL DEFAULT '',"
				+ "Pub_userHead char(255) DEFAULT NULL,"
				+ "Inf_time datetime NOT NULL,"
				+ "Inf_loc tinytext,"
				+ "Inf_content text NOT NULL,"
				+ "Pub_tag_level1 smallint NOT NULL,"
				+ "Pub_tag_level2 text NOT NULL,"
				+ "Vote_num smallint NOT NULL DEFAULT '0',"
				+ "Com_num smallint NOT NULL DEFAULT '0',"
				+ "Share_num smallint NOT NULL DEFAULT '0',"
				+ "FOREIGN KEY(Pub_tag_level1) REFERENCES pub_tag_level1 (tag_id)"
				+ ")";
		try {
			db.execSQL(pub_tag_level1);
			db.execSQL(user_tag);
			db.execSQL(create_sql);
			for(String index : AppConfig.pub_tag_level1){
				ContentValues values = new ContentValues();
				values.put("tag_name", index);
				db.insert("pub_tag_level1", null, values);
			}
			for (String index : AppConfig.user_tag_items) {
				ContentValues values = new ContentValues();
				values.put("tag_name", index);
				db.insert("user_tag", null, values);
			}
			
			db.execSQL(profile);
			db.execSQL(user_tag_link);
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("fail");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
