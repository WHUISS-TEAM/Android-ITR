package org.itrgroup.itr.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.itrgroup.itr.R;
import org.itrgroup.itr.main.MainActivity;
import org.itrgroup.itr.ws_thread.Thread_Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;


public class LoginActivity extends Activity {


	private EditText email_text = null;
	private EditText password_text = null;
	private Button login_button = null;
	private Button register_button = null;
	private String email = "";
	private String password = "";
	private Thread_Login login_Thread = null;
	//数据库的名字
	private static final String db_name = "itr";
	//用于设置自动登陆
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		final ScrollView myScroll = (ScrollView) findViewById(R.id.scroll);
		//Hide Scroll Bar
		myScroll.setOnTouchListener( new OnTouchListener(){ 
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        return true; 
		    }
		});
		//这个handler是这里的activity的handeler，用来接收线程处理完后的消息（这里是一个bool类型的值）
		final Handler handler = new LoginHandler();
		//这个是连接网络用的线程
		login_Thread = new Thread_Login(handler);
		login_Thread.start();
		email_text = (EditText)findViewById(R.id.email);
		password_text = (EditText)findViewById(R.id.password);
		login_button = (Button)findViewById(R.id.login_button);
		register_button = (Button)findViewById(R.id.regist_button);
		
		//如果登录过则自动保存密码
		sp = this.getSharedPreferences("userInfo", this.MODE_WORLD_READABLE);
		email_text.setText(sp.getString("LOGIN_EMAIL", ""));
		password_text.setText(sp.getString("LOGIN_PASSWORD", ""));
		
		login_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(email_text.getText().toString().equals("")){
					Toast.makeText(LoginActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
				}
				else{
					email = email_text.getText().toString();
					
					if(password_text.getText().toString().equals("")){
						Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					}else{
						try {
							//将密码用MD5加密的方法
							MessageDigest digest = MessageDigest.getInstance("MD5");
							digest.update(password_text.getText().toString().getBytes());
							//ChangeByteToString是之后的方法
							//因为转化后获得的是byte数组，两个数字转化为一个十六进制代表的字符
							//所以要将byte数组转化为string
							password = changeByteToString(digest.digest());
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Message msg = new Message();
						msg.what = 0x123;
						Bundle bundle = new Bundle();
						bundle.putString("email", email);
						bundle.putString("password",password);
						bundle.putString("db_name",db_name);
						msg.setData(bundle);
						//启用线程中的handle message
						login_Thread.login_handler.sendMessage(msg);
					}
				}
			}
		});
		
		register_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			}
		});
	}
	
	
	//MD5的byte数组转化为string
		public static String changeByteToString(byte[] target){
			String results = "";
			String temp = "";
			for(int i=0;i<target.length;i++){
				//将数字转化为16进制代表的string
				temp = Integer.toHexString(target[i] & 0xff);
				if(temp.length()==1){
					//补0操作
					temp = 0 + temp;
				}
				results += temp;
			}
			
			return results;
		} 
		
	//login类的handle massage操作定义
	public class LoginHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 0x321){
				Bundle bundle = msg.getData();
				boolean result = true;
				result = bundle.getBoolean("result");
				if(result){
					//结果为true，保存账号密码，设置自动登陆并启动主页的activity
					//setFlag用来设置启动的模式
					//FLAG_ACTIVITY_CLEAR_TASK：在启动新的activity之前将已有的都清空掉
					//必须和后者一起使用
					
					Editor editor = sp.edit();
					editor.putString("LOGIN_EMAIL", email);
					editor.putString("LOGIN_PASSWORD",""+password_text.getText());
					editor.putBoolean("IS_CHECKED", true);
					editor.commit();
					
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
				else{
					Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
}