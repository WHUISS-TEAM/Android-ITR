package org.itrgroup.itr.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.itrgroup.itr.R;
import org.itrgroup.itr.main.MainActivity;
import org.itrgroup.itr.utils.IniDatabaseHelper;
import org.itrgroup.itr.ws_thread.Thread_Login;

import android.R.integer;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
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

	//���������Զ���½
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
		//���handler�������activity��handeler�����������̴߳���������Ϣ��������һ��bool���͵�ֵ��
		final Handler handler = new LoginHandler();
		//��������������õ��߳�
		login_Thread = new Thread_Login(handler);
		login_Thread.start();
		email_text = (EditText)findViewById(R.id.email);
		password_text = (EditText)findViewById(R.id.password);
		login_button = (Button)findViewById(R.id.login_button);
		register_button = (Button)findViewById(R.id.regist_button);
		
		//�����¼�����Զ���������
		sp = this.getSharedPreferences("userInfo", this.MODE_WORLD_READABLE);
		email_text.setText(sp.getString("LOGIN_EMAIL", ""));
		password_text.setText(sp.getString("LOGIN_PASSWORD", ""));
		
		login_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(email_text.getText().toString().equals("")){
					Toast.makeText(LoginActivity.this, "���䲻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				else{
					email = email_text.getText().toString();
					
					if(password_text.getText().toString().equals("")){
						Toast.makeText(LoginActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					}else{
						try {
							//��������MD5���ܵķ���
							MessageDigest digest = MessageDigest.getInstance("MD5");
							digest.update(password_text.getText().toString().getBytes());
							//ChangeByteToString��֮��ķ���
							//��Ϊת�����õ���byte���飬��������ת��Ϊһ��ʮ�����ƴ�����ַ�
							//����Ҫ��byte����ת��Ϊstring
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
						msg.setData(bundle);
						//�����߳��е�handle message
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
	
	
	//MD5��byte����ת��Ϊstring
		public static String changeByteToString(byte[] target){
			String results = "";
			String temp = "";
			for(int i=0;i<target.length;i++){
				//������ת��Ϊ16���ƴ����string
				temp = Integer.toHexString(target[i] & 0xff);
				if(temp.length()==1){
					//��0����
					temp = 0 + temp;
				}
				results += temp;
			}
			
			return results;
		} 
		
	//login���handle massage��������
	public class LoginHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 0x321){
				Bundle bundle = msg.getData();
				int result = 0;
				int profile_id;
				String username;
				result = bundle.getInt("result");
				profile_id = bundle.getInt("profile_id");
				username = bundle.getString("username");
				System.out.println(profile_id + "~~~~" + username);
				
				if(result == 1){
					//���Ϊtrue�������˺����룬�����Զ���½��������ҳ��activity
					//setFlag��������������ģʽ
					//FLAG_ACTIVITY_CLEAR_TASK���������µ�activity֮ǰ�����еĶ���յ�
					//����ͺ���һ��ʹ��
					SharedPreferences users = getSharedPreferences("user_list", 0);
					//��������û��ǵ�һ���ڸ�����ϵ�½���򷵻�false
					boolean before = users.getBoolean(email, false);
					//���Ϊfalse����Ϊ���û�����һЩ��ʼ������
					//��Ӧ����ע����ɺ��Զ���ע���û�����Ϊtrue
					if(!before){
						new IniDatabaseHelper(LoginActivity.this, email + ".db3", null, 1).getReadableDatabase().close();
						users.edit().putBoolean(email, true).commit();
					}
					
					new LoginLocalInsert(profile_id,username).start();
					
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
					Toast.makeText(LoginActivity.this, "�˺Ż��������", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
	private class LoginLocalInsert extends Thread{
		private int profile_id;
		private String str_username;
		public LoginLocalInsert(int profile_id, String str_username) {
			// TODO Auto-generated constructor stub
			this.profile_id = profile_id;
			this.str_username = str_username;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			SQLiteDatabase database = 
			new IniDatabaseHelper(LoginActivity.this, email + ".db3", null, 1).getReadableDatabase();
			ContentValues values = new ContentValues();
			values.put("email", email);
			values.put("username", str_username);
			values.put("profile_id", profile_id);
			database.insert("profile", null, values);
			
			database.close();
		}
	}
	
}