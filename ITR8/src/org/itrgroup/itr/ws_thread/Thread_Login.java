package org.itrgroup.itr.ws_thread;

import java.io.IOException;

import org.itrgroup.itr.utils.AppConfig;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.SharedPreferences;
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
	private boolean final_result = false;
	
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
					String db_name = message.getString("db_name");
					//连接webservice的方法
					final_result = execute(email, password, db_name);
					Message result_msg = new Message();
					result_msg.what = 0x321;
					Bundle bundle = new Bundle();
					bundle.putBoolean("result", final_result);
					result_msg.setData(bundle);
					activity_handler.sendMessage(result_msg);
				}
			}
			
		};
		Looper.loop();
	}
	
	private boolean execute(String email,String password,String db_name){
		boolean re =  false;
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
				re = Boolean.parseBoolean(result_object.getProperty(0).toString());
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
		return re;
	}
}

