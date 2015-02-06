package org.itrgroup.itr.ws_thread;

import java.io.IOException;
import java.util.ArrayList;

import org.itrgroup.itr.login.RegisterActivity;
import org.itrgroup.itr.main.MainActivity;
import org.itrgroup.itr.utils.AppConfig;
import org.itrgroup.itr.utils.IniDatabaseHelper;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class Thread_Register extends Thread{

	public Handler register_handler = null;
	private int final_result = 3;
	private int tag_result = 0;
	private Handler activity_handler;
	private int profile_id = -1;
	
	private static final String SERVICE_URL = AppConfig.WebService_IP + "DBConnection"
			+"/WSRegisterPort";
	private static final String SERVICE_NS = "http://dbConnection/";
	
	private static final String SERVICE_URL_CT = AppConfig.WebService_IP + "DBConnection"
			+"/WSCreateTablePort";
	public Thread_Register() {
		// TODO Auto-generated constructor stub
	}
	
	public Thread_Register(Handler handler){
		activity_handler = handler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Looper.prepare();
		register_handler = new Handler(){
			
			String username;
			String email;
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				//super.handleMessage(msg);
				//第一页的注册
				if(msg.what == 0x111){
					Bundle bundle = msg.getData();
					username = bundle.getString("username");
					String password = bundle.getString("password");
					email = bundle.getString("email");
					String user_head = bundle.getString("user_head");
					final_result = execute(user_head,username, password, email);
					Message message = new Message();
					message.arg1 = final_result;
					message.what = 0x101;
					activity_handler.sendMessage(message);
					//当注册成功的时候，自动为用户创建表
					//为用户初始化本地数据库
					if(final_result == 2){
						createTable(username);
//						createLocalTable(username);
					}
				}
				
				//处理标签插入的消息
				if(msg.what == 0x112){
					Bundle bundle2 = msg.getData();
					ArrayList<Integer> seletedItems = new ArrayList<Integer>();
					seletedItems = bundle2.getIntegerArrayList("interests");
					tag_result = tagInsert(seletedItems);
					Message message2 = new Message();
					message2.arg1 = tag_result;
					//懒得用bundle了就先用这个算了。。。。
					message2.arg2 = profile_id;
					message2.what = 0x102;
					activity_handler.sendMessage(message2);
//					if(tag_result == 1){
//						InsertProfile(username,email,seletedItems);
//					}
				}
			}
			
		};
		//super.run();
		Looper.loop();
	}
	
	private int execute(String user_head,String username,String password,String email){
		final HttpTransportSE transport = new HttpTransportSE(SERVICE_URL);
		transport.debug = true;
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject object = new SoapObject(SERVICE_NS, "registerUser");
		object.addProperty("arg0", user_head);
		object.addProperty("arg1",username);
		object.addProperty("arg2", password);
		object.addProperty("arg3", email);
		
		envelope.bodyOut = object;
		
		try {
			transport.call(null, envelope);
			if(envelope.getResponse() != null){
				SoapObject result_object = (SoapObject)envelope.bodyIn;
				String ress = envelope.bodyIn.toString();
				int num = result_object.getPropertyCount();
				if(num != 1){
					profile_id = Integer.parseInt(result_object.getProperty(1).toString());
				}
				return Integer.parseInt(result_object.getProperty(0).toString());
			}
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 3;
	}

	//0--插入出错
	//1--插入成功
	private int tagInsert(ArrayList<Integer> selectedItems){
		final HttpTransportSE transport = new HttpTransportSE(SERVICE_URL);
		transport.debug = true;
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject object = new SoapObject(SERVICE_NS,"tagInsert");
		//本来使用数组，但是数组要序列化
		object.addProperty("arg0", profile_id);
		for(int i=0;i<selectedItems.size();i++){
			object.addProperty("arg0",selectedItems.get(i).intValue());
		}
		envelope.bodyOut = object;
		try {
			transport.call(null, envelope);
			if(envelope.getResponse()!=null){
				SoapObject reObject = (SoapObject)envelope.bodyIn;
				return Integer.parseInt(reObject.getProperty(0).toString());
			}
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	private void createTable(String userName){
		final HttpTransportSE transport = new HttpTransportSE(SERVICE_URL_CT);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject object = new SoapObject(SERVICE_NS, "createTable");
		object.addProperty("arg0", userName);
		envelope.bodyOut = object;
		try {
			transport.call(null, envelope);
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	private void createLocalTable(String userName){
//		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(AppConfig.DATABASE_PATH, null);
//		String create_sql = "CREATE TABLE " + userName + "Received" + " ("
//				+ "Pub_inforId integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
//				+ "Pub_userName varchar(50) NOT NULL DEFAULT '',"
//				+ "Pub_userHead char(255) DEFAULT NULL,"
//				+ "Inf_time datetime NOT NULL,"
//				+ "Inf_loc tinytext,"
//				+ "Inf_content text NOT NULL,"
//				+ "Pub_tag_level1 smallint NOT NULL,"
//				+ "Pub_tag_level2 text NOT NULL,"
//				+ "Vote_num smallint NOT NULL DEFAULT '0',"
//				+ "Com_num smallint NOT NULL DEFAULT '0',"
//				+ "Share_num smallint NOT NULL DEFAULT '0',"
//				+ "FOREIGN KEY(Pub_tag_level1) REFERENCES Pub_tag_level1 (tag_id)"
//				+ ")";
//		//很奇怪，用这样的语句不需要最后加分号
//		try {
//			database.execSQL(create_sql);
//		} catch (SQLException e) {
//			// TODO: handle exception
//			System.out.println("创建失败");
//			e.printStackTrace();
//		}finally{
//			database.close();
//		}
//	}
	
	private void InsertProfile(String username, String email, ArrayList<Integer> seletedItems){
		System.out.println(email);
		System.out.println(AppConfig.DATABASE_PATH 
				+ "/" + email + ".db3");
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(AppConfig.DATABASE_PATH 
				+ "/" + email + ".db3" , null);
		System.out.println(email);
		ContentValues values = new ContentValues();
		values.put("email", email);
		values.put("username", username);
		database.insert("profile", null, values);
		
		for(int index : seletedItems){
			ContentValues selected = new ContentValues();
			selected.put("profile_id", profile_id);
			selected.put("tag_id", index);
			database.insert("user_tag_link", null, selected);
		}
		
		database.close();
	}
	
}
