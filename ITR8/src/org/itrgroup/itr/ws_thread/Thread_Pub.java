package org.itrgroup.itr.ws_thread;

import java.io.IOException;

import org.itrgroup.itr.utils.AppConfig;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class Thread_Pub extends Thread{

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
					String userName = bundle.getString("userName");
					String date = bundle.getString("Inf_time");
					String location = bundle.getString("Inf_loc");
					String str_publish_content = bundle.getString("Inf_content");
					int choose_tag = bundle.getInt("Label_lavel1");
					String str_sec_tag = bundle.getString("Label_level2");
					int vote = bundle.getInt("vote");
					int com = bundle.getInt("com");
					int share = bundle.getInt("share");
					Message response = new Message();
					response.arg1 = 
					publish(userName, date, location, str_publish_content, choose_tag, str_sec_tag, vote, com, share);
					response.what = 0x103;
					activity_handler.sendMessage(response);
					
				}
				super.handleMessage(msg);
			}
		};
		super.run();
		Looper.loop();
	}
	
	private int publish(String userName,String date,String location,String str_publish_content,
			int choose_tag,String str_sec_tag,int vote,int com,int share){
		final HttpTransportSE transport = new HttpTransportSE(SERVICE_URL);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject object = new SoapObject(SERVICE_NS, "Publish");
		object.addProperty("arg0", userName);
		object.addProperty("arg1", date);
		object.addProperty("arg2", location);
		object.addProperty("arg3", str_publish_content);
		object.addProperty("arg4", choose_tag);
		object.addProperty("arg5", str_sec_tag);
		object.addProperty("arg6", vote);
		object.addProperty("arg7", com);
		object.addProperty("arg8", share);
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
}
