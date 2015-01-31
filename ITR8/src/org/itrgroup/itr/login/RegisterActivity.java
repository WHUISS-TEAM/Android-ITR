package org.itrgroup.itr.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.itrgroup.itr.R;
import org.itrgroup.itr.main.MainActivity;
import org.itrgroup.itr.utils.AppConfig;
import org.itrgroup.itr.ws_thread.Thread_Register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class RegisterActivity extends Activity {

	private EditText email = null;
	private EditText username = null;
	private EditText password = null;
	private EditText assure_password = null;
	private String str_email;
	private String str_username;
	private String str_password;
	private Button next = null;
	private int register_result = -1;
	//Checkbox
	private final ArrayList<Integer> seletedItems=new ArrayList<Integer>();
	private AlertDialog dialog;
	//���������Զ���½
	private SharedPreferences sp;
	
	private Thread_Register register_thread = null;
	private Handler activity_handler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		final ScrollView myScroll = (ScrollView) findViewById(R.id.scroll2);
		//Hide Scroll Bar
		myScroll.setOnTouchListener( new OnTouchListener(){ 
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        return true; 
		    }
		});
		
		sp = this.getSharedPreferences("userInfo", this.MODE_WORLD_READABLE);
		
		activity_handler = new Register_Handler();
		register_thread = new Thread_Register(activity_handler);
		register_thread.start();
		
		email = (EditText)findViewById(R.id.register_email);
		username = (EditText)findViewById(R.id.register_username);
		password = (EditText)findViewById(R.id.register_password);
		assure_password = (EditText)findViewById(R.id.register_password2);
		next = (Button)findViewById(R.id.next_button);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(register_result == 2){
					seletedItems.clear();
					SelectInterests();
				}else{
					if(email.getText().toString().equals("")){
						Toast.makeText(RegisterActivity.this, "���䲻��Ϊ��", Toast.LENGTH_SHORT).show();
					}
					else{
						str_email = email.getText().toString();
						if(username.getText().toString().equals("")){
							Toast.makeText(RegisterActivity.this, "�ǳƲ���Ϊ��", Toast.LENGTH_SHORT).show();
						}
						else{
							str_username = username.getText().toString();
							if(!password.getText().toString().equals(assure_password.getText().toString())){
								Toast.makeText(RegisterActivity.this, "�������벻һ��", Toast.LENGTH_SHORT).show();
							}
							else{
								try {
									MessageDigest digest = MessageDigest.getInstance("MD5");
									digest.update(password.getText().toString().getBytes());
									str_password = changeByteToString(digest.digest());
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Message msg = new Message();
								Bundle bundle = new Bundle();
								bundle.putString("username", str_username);
								bundle.putString("password", str_password);
								bundle.putString("email", str_email);
								msg.setData(bundle);
								msg.what = 0x111;
								register_thread.register_handler.sendMessage(msg);
							}
						}
					}
				}
			}
		});
	}
	
	//ѡ����Ȥ�ĸ�ѡ��
	public void SelectInterests(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("�����Щ���ݸ���Ȥ�أ����ǽ��������ѡ��������Ϣ��(������ѡ��3����ǩ )");
        builder.setMultiChoiceItems(AppConfig.items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int indexSelected,
                 boolean isChecked) {
             if (isChecked) {
            	 //����һ��ѡ���ѡʱִ��
            	 if(seletedItems.size()<=4){
            		 seletedItems.add(indexSelected + 1);
            	 }else{
            		 Toast.makeText(RegisterActivity.this, "���ֻ��ѡ��4����ǩ : )", Toast.LENGTH_SHORT).show();
                	 ((AlertDialog) dialog).getListView().setItemChecked(indexSelected, false);
            	 }
             } else if (seletedItems.contains(indexSelected)) {
            	 //����һ��ѡ�ȡ����ѡʱִ��
                 seletedItems.remove(Integer.valueOf(indexSelected));
             }
         }
     }).setPositiveButton("���", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int id) {
        	 if(seletedItems.size()<3){
        		 Toast.makeText(RegisterActivity.this, "������ѡ��3����ǩ : )", Toast.LENGTH_SHORT).show();
        	 }else{
        		Message msg_interests = new Message();
				Bundle bundle_interests = new Bundle();
				bundle_interests.putIntegerArrayList("interests", seletedItems);
				msg_interests.setData(bundle_interests);
				//���ñ�ǩʱ����ͬ����register�߳�
				msg_interests.what = 0x112;
				register_thread.register_handler.sendMessage(msg_interests);
        	 }
         }
     })
     .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int id) {
         }
     });

        dialog = builder.create();//AlertDialog dialog; create like this outside onClick
        dialog.show();
	}
	
	//MD5��byte����ת��Ϊstring
	public static String changeByteToString(byte[] target){
		String results = "";
		String temp = "";
		for(int i=0;i<target.length;i++){
			//������ת��Ϊ16���ƴ�����string
			temp = Integer.toHexString(target[i] & 0xff);
			if(temp.length()==1){
				//��0����
				temp = 0 + temp;
			}
			results += temp;
		}
		
		return results;
	} 
	
	public class Register_Handler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 0x101){
				register_result = msg.arg1;
				switch(register_result){
					case 0:
						Toast.makeText(RegisterActivity.this, "�����Ѿ���ע��", Toast.LENGTH_SHORT).show();
						break;
					case 1:
						Toast.makeText(RegisterActivity.this, "�û����ѱ�ע��", Toast.LENGTH_SHORT).show();
						break;
					case 2:
						Toast.makeText(RegisterActivity.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
						seletedItems.clear();
						SelectInterests();
						break;
					case 3:
						Toast.makeText(RegisterActivity.this, "ע��ʧ��", Toast.LENGTH_SHORT).show();
				}
			}
			if(msg.what == 0x102){
				int tag_result = msg.arg1;
				switch (tag_result) {
				case 0:
					Toast.makeText(RegisterActivity.this, "��ǩ����ʧ�ܣ�������", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(RegisterActivity.this, "��ǩѡ��ɹ�", Toast.LENGTH_SHORT).show();
					
					//�����˺�����
					Editor editor = sp.edit();
					editor.putString("LOGIN_EMAIL", ""+email.getText());
					editor.putString("LOGIN_PASSWORD",""+password.getText());
					editor.putBoolean("IS_CHECKED", true);
					editor.commit();
					
					Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					break;
				}
			}
		}
		
	}

}
