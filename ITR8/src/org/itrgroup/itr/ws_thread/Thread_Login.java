package org.itrgroup.itr.ws_thread;

import java.io.IOException;
import java.util.ArrayList;

import org.itrgroup.itr.utils.AppConfig;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class Thread_Login extends Thread{

	public Handler login_handler;
	//接受login类的handler，在处理完后用来向他发送消息
	private Handler activity_handler = null;
	//webservice的地址，只需要改ip和端口
	private static final String SERVICE_URL = AppConfig.WebService_IP + "DBConnection"
			+"/WSLoginPort";
	private static final String SERVICE_NS = "http://dbConnection/";
	private int final_result = 0;
	private int profile_id = 0;
	private String username = null;
	
	public Thread_Login() {
		// TODO Auto-generated constructor stub
	}
	
	public Thread_Login(Handler activity_handler){
		this.activity_handler = activity_handler;
	}
	public void run(){
		Looper.prepare();
		login_handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what == 0x123){
					Bundle message = msg.getData();
					String email = message.getString("email");
					String password = message.getString("password");
					//连接webservice的方法
					final_result = execute(email, password);
					Message result_msg = new Message();
					result_msg.what = 0x321;
					Bundle bundle = new Bundle();
					bundle.putInt("result", final_result);
					bundle.putString("username", username);
					bundle.putInt("profile_id", profile_id);
					result_msg.setData(bundle);
					activity_handler.sendMessage(result_msg);
				}
			}
			
		};
		Looper.loop();
	}
	
	//0 表示失败
	//1 表示成功
	private int execute(String email,String password){
		int re =  0;
		//连接webservice的方法以及调用方法解析结果的方法
		final HttpTransportSE transport = new HttpTransportSE(SERVICE_URL);
		transport.debug = true;
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject object = new SoapObject(SERVICE_NS, "verifyUser");
		object.addProperty("arg0", email);
		object.addProperty("arg1", password);
		envelope.bodyOut = object;
		try {
			transport.call(null, envelope);
			if(envelope.getResponse() != null){
				SoapObject result_object = (SoapObject)envelope.bodyIn;
				String ress = envelope.bodyIn.toString();
				re = Integer.parseInt(result_object.getProperty(0).toString());
				if(re == 1){
					profile_id = Integer.parseInt(result_object.getProperty(1).toString());
					username = result_object.getProperty(2).toString();
				}
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
		
		//if(re){ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//没有办法获得用户名
			//InsertProfile(username, email);
		//}
		return re;
	}
	
//	private void InsertProfile(String username, String email){
//		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(AppConfig.DATABASE_PATH, null);
//		ContentValues values = new ContentValues();
//		values.put("email", email);
//		values.put("username", username);
//		database.insert("profile", null, values);
//
//		database.close();
//	}
	
}

