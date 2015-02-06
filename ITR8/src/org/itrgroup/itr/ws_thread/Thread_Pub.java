package org.itrgroup.itr.ws_thread;

import java.io.IOException;

import org.itrgroup.itr.utils.AppConfig;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class Thread_Pub extends Thread{

	private String email;
	public Handler pub_handler = null;
	//接受pub类的handler，在处理完后用来向他发送消息
	private Handler activity_handler = null;
	//webservice的地址，只需要改ip和端口
	private static final String SERVICE_URL = AppConfig.WebService_IP + "DBConnection"
			+"/WSPublishPort";
	private static final String SERVICE_NS = "http://dbConnection/";
	
	public Thread_Pub() {
		// TODO Auto-generated constructor stub
	}
	public Thread_Pub(Handler handler){
		activity_handler = handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Looper.prepare();
		pub_handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what == 0x113){
					Bundle bundle = msg.getData();
					email = bundle.getString("email");
					String userName = bundle.getString("userName");
					String user_head = bundle.getString("user_head");
					String date = bundle.getString("Inf_time");
					String location = bundle.getString("Inf_loc");
					String str_publish_content = bundle.getString("Inf_content");
					int choose_tag = bundle.getInt("Pub_tag_level1");
					String str_sec_tag = bundle.getString("Pub_tag_level2");
					int vote = bundle.getInt("vote");
					int com = bundle.getInt("com");
					int share = bundle.getInt("share");
					Message response = new Message();
					response.arg1 = 
					publish(userName,user_head, date, location, str_publish_content, choose_tag, str_sec_tag, vote, com, share);
//					response.arg2 = 
//					local_publish(userName, user_head, date, location, str_publish_content, choose_tag, str_sec_tag, vote, com, share);
					response.what = 0x103;
					activity_handler.sendMessage(response);
					
				}
				super.handleMessage(msg);
			}
		};
		super.run();
		Looper.loop();
	}
	
	//0--发布失败
	//1--发布成功
	private int publish(String userName,String user_head,String date,String location,String str_publish_content,
			int choose_tag,String str_sec_tag,int vote,int com,int share){
		final HttpTransportSE transport = new HttpTransportSE(SERVICE_URL);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject object = new SoapObject(SERVICE_NS, "Publish");
		object.addProperty("arg0", userName);
		object.addProperty("arg1", user_head);
		object.addProperty("arg2", date);
		object.addProperty("arg3", location);
		object.addProperty("arg4", str_publish_content);
		object.addProperty("arg5", choose_tag);
		object.addProperty("arg6", str_sec_tag);
		object.addProperty("arg7", vote);
		object.addProperty("arg8", com);
		object.addProperty("arg9", share);
		envelope.bodyOut = object;
		try {
			transport.call(null, envelope);
			if(envelope.getResponse() != null){
				SoapObject result_object = (SoapObject)envelope.bodyIn;
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
		return 0;
	}
	
	//结果同上
//	private int local_publish(String userName,String user_head,String date,String location,String str_publish_content,
//			int choose_tag,String str_sec_tag,int vote,int com,int share){
//		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(AppConfig.DATABASE_PATH + "/" + email + ".db3", null);
//		ContentValues values = new ContentValues();
//		values.put("Pub_userName", userName);
//		values.put("Pub_userHead", user_head);
//		values.put("Inf_time", date);
//		values.put("Inf_loc", location);
//		values.put("Inf_content", str_publish_content);
//		values.put("Pub_tag_level1", choose_tag);
//		values.put("Pub_tag_level2", str_sec_tag);
//		values.put("Vote_num", vote);
//		values.put("Com_num", com);
//		values.put("Share_num", share);
//		
//		try {
//			if((int)database.insert("Received", null, values) != -1){
//				return 1;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}finally{
//			database.close();
//		}
//		return 0;
//	}
	
}
