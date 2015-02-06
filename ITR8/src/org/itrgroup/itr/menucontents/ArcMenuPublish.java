package org.itrgroup.itr.menucontents;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.itrgroup.itr.R;
import org.itrgroup.itr.adapter.SpinnerAdapter;
import org.itrgroup.itr.main.MainActivity;
import org.itrgroup.itr.model.MainContentModel;
import org.itrgroup.itr.utils.AppConfig;
import org.itrgroup.itr.utils.FragmentUtils;
import org.itrgroup.itr.utils.IniDatabaseHelper;
import org.itrgroup.itr.ws_thread.Thread_Pub;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ArcMenuPublish extends Activity{

	
	private FragmentUtils utils = new FragmentUtils();
	private ActionBar actionBar = null;
	private Spinner mSpinner;
	private SharedPreferences sp;
	private Handler pub_handler = null;
	private Thread_Pub pub_thread = null;
	//�ж�һ����ǩ
	private int choose_tag = 0;
	//��д������ǩ
	private EditText sec_tag = null;
	//����������
	private EditText publish_content = null;
	//ָ�����û���
	private String userName = "";
	//ָ�����䣬�ҵ����ݿ�
	private String email = "";
	//ָ���ص�
	private String location = "CS";
	//ָ�����û�ͷ��--���ڹ���msgmodel����
	private int head = R.drawable.avatar_3;
	//����ģ�����ݿ�洢ͷ��
	private String user_head = "avatar_1.png";
	private String date_db;
	private String date_msg;
	private String str_sec_tag = "";
	private String str_publish_content;
	private int share_num;
	private int comment_num;
	private int vote_num;
	
	//һ����ǩSpinner���tu'pian
	//������һ������ѡ�񡱣���ӦidΪ0��ͼƬΪ��
	int[] drawableIds = {0,R.drawable.spinner_club, R.drawable.spinner_idea,
				R.drawable.spinner_fun, R.drawable.spinner_study, R.drawable.spinner_recruit};
	int[] spinnerTxt_Id={R.string.blank,R.string.activity,R.string.creation,
				R.string.play,R.string.study,R.string.hire};
	String[] spinner_data = {"����","����","����","ѧϰ","��Ƹ"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//���ڻ�õ�¼�ߵ��û���
		sp = this.getSharedPreferences("userInfo", this.MODE_WORLD_READABLE);
				
		pub_handler = new Pub_Handler();
		pub_thread = new Thread_Pub(pub_handler);
		pub_thread.start();
		
		setContentView(R.layout.main_publish);
		//�����õ���ע��ʱ���û����û����������������⣺
		//1. ע���û������������ģ����Ŀ��������ݿ��������
		//2. ��¼�û���ȡ����ע���������ܻ�ȡ�����䣻
		//����ֻ��ע�ᣬ�˴���Ҫ�޸ġ�
		userName = sp.getString("REGIST_USERNAME", "");
		email = sp.getString("LOGIN_EMAIL", "");
		sec_tag = (EditText)findViewById(R.id.sec_tag);
		publish_content = (EditText)findViewById(R.id.publish_content);
		
		//�����ǹ���actionBar������
	     actionBar=getActionBar();
	     //actionBar.show();
	     actionBar.setDisplayShowTitleEnabled(false);
	    // actionBar.setLogo(R.drawable.blank);	
	     actionBar.setLogo(R.drawable.back_icon);
	    // actionBar.setDisplayHomeAsUpEnabled(true); 
	     actionBar.setHomeButtonEnabled(true);
	     View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.pub_actionbar_view, null); 
	     TextView textView = (TextView) actionbarLayout.findViewById(R.id.actionBar);
	     textView.setText("������Ϣ");
	     actionBar.setDisplayShowCustomEnabled(true); 
	     actionBar.setCustomView(actionbarLayout);
	     
	     //�����ǹ���һ����ǩSpinner������
	     SpinnerAdapter spinnerAdapter=new SpinnerAdapter(
	    		 this.getApplicationContext(),drawableIds,spinnerTxt_Id);
	     mSpinner = (Spinner) findViewById(R.id.spinner);
	     mSpinner.setAdapter(spinnerAdapter);
	     mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				choose_tag = (int)id;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
	    	 
		});
	     
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 switch (item.getItemId()) {  
	        case android.R.id.home:  
	            Intent intent = new Intent(this, MainActivity.class);  
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
	            startActivity(intent);  
	            return true; 

	        case R.id.pub_send:
	        	execute_pub();
	        	return true;
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pub_actionbar_view, menu);
        return true;
	}
	//�ڵ����pub_send�����
	private void execute_pub(){
		if(choose_tag == 0){
			Toast.makeText(this, "��ѡ��һ����ǩ", Toast.LENGTH_SHORT).show();
		}
		else{
			if(sec_tag.getText().toString().equals("")){
				Toast.makeText(this, "������ǩ����Ϊ��", Toast.LENGTH_SHORT).show();
			}
			else{
				String level_two = sec_tag.getText().toString();
				String[] results = level_two.split(" ");
				//���ݵĽ���ʹ������ݿ���Ľ��
				
				for(String tag:results){
					str_sec_tag = str_sec_tag + "#" + tag +" ";
				}
				if(publish_content.getText().toString().equals("")){
					Toast.makeText(this, "˵��ɶ��", Toast.LENGTH_SHORT).show();
				}
				else{
					//���ݵĽ��
					Date date = new Date();
					str_publish_content = publish_content.getText().toString();
					SimpleDateFormat date_format_db = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					date_db = date_format_db.format(date);
					SimpleDateFormat date_formate_msg = new SimpleDateFormat("yyyy-MM-dd hh:mm");
					date_msg = date_formate_msg.format(date);
					share_num = 0;
					comment_num = 0;
					vote_num = 0;
					Bundle data = new Bundle();
					data.putString("email", email);
					data.putString("userName", userName);
					data.putString("user_head", user_head);
					data.putString("Inf_time", date_db);
					data.putString("Inf_loc", location);
					data.putString("Inf_content", str_publish_content);
					data.putInt("Pub_tag_level1", choose_tag);
					data.putString("Pub_tag_level2", str_sec_tag);
					data.putInt("vote", vote_num);
					data.putInt("com", comment_num);
					data.putInt("share", share_num);
					Message msg = new Message();
					msg.what = 0x113;
					msg.setData(data);
					pub_thread.pub_handler.sendMessage(msg);
					//�����ݳɹ��������ݿ��Ժ󽫻���з�������
				}
			}
		}
	}

//-------------------------------���ֻ��һ�������ɹ�����ô�죿����
//һ�ߵ����ݿ���������ݣ�����һ��û�У�����ظ����в����϶�����������ظ�
//15.2.2
	private class Pub_Handler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 0x103){
				if(msg.arg1 == 0){
					Toast.makeText(ArcMenuPublish.this, "����ʧ��", Toast.LENGTH_SHORT).show();
				}
				if(msg.arg1 == 1){
					new LocalPub().start();
					MainContentModel new_msg = new MainContentModel(userName, date_msg, spinner_data[choose_tag-1], 
							str_sec_tag, str_publish_content, head, share_num, comment_num, vote_num);
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putSerializable("new_msg", new_msg);
					intent.putExtras(bundle);
					setResult(0, intent);
					Toast.makeText(ArcMenuPublish.this, "�����ɹ�", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		}
	}
	
	private class LocalPub extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			local_publish(userName, user_head, date_db, location, str_publish_content, choose_tag, str_sec_tag, vote_num, comment_num, share_num);
		}
	}
	
	private int local_publish(String userName,String user_head,String date,String location,String str_publish_content,
			int choose_tag,String str_sec_tag,int vote,int com,int share){
		SQLiteDatabase database = new IniDatabaseHelper(this, email + ".db3", null, 1).getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("Pub_userName", userName);
		values.put("Pub_userHead", user_head);
		values.put("Inf_time", date);
		values.put("Inf_loc", location);
		values.put("Inf_content", str_publish_content);
		values.put("Pub_tag_level1", choose_tag);
		values.put("Pub_tag_level2", str_sec_tag);
		values.put("Vote_num", vote);
		values.put("Com_num", com);
		values.put("Share_num", share);
		
		try {
			if((int)database.insert("Received", null, values) != -1){
				return 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			database.close();
		}
		return 0;
	}
	
}