package org.itrgroup.itr.menucontents;

import java.util.ArrayList;
import java.util.List;

import org.itrgroup.itr.R;
import org.itrgroup.itr.adapter.ContactsExpandAdapter;
import org.itrgroup.itr.main.MainActivity;
import org.itrgroup.itr.model.ContactModel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class ArcMenuContacts extends Activity {

	
	 private ExpandableListView mListView = null; 
	 private ContactsExpandAdapter mAdapter = null; 
	 private List<List<ContactModel>> mData = new ArrayList<List<ContactModel>>(); 
	 private ActionBar actionBar;
	 
	 
	 private int[] mGroupArrays = new int[] {  
	            R.array.same_major, 
	            R.array.teamate,  
	            R.array.interests };
	 
	 private int[] mDetailIds = new int[] {  
	            R.array.same_major_detail, 
	            R.array.teamate_detail,  
	            R.array.interests_detail }; 
	 
	 private int[][] mImageIds = new int[][] { 
	            { R.drawable.avatar_4,  
	              R.drawable.avatar_2,  
	              R.drawable.avatar_3,
	              R.drawable.avatar_5, 
	              R.drawable.avatar_1 }, 
	            { R.drawable.avatar_2,  
		          R.drawable.avatar_4,  
		          R.drawable.avatar_3,
		          R.drawable.avatar_5, 
		          R.drawable.avatar_1}, 
	            { R.drawable.avatar_2, 
	              R.drawable.avatar_3} }; 
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.contacts_layout);
		 initData(); 
	     mListView = (ExpandableListView) findViewById(R.id.listView);	 
	     
	     //以下是关于actionBar的设置
	     actionBar=getActionBar();
	     //actionBar.show();
	     actionBar.setDisplayShowTitleEnabled(false);
	    // actionBar.setLogo(R.drawable.blank);	
	     actionBar.setLogo(R.drawable.back_icon);
	    // actionBar.setDisplayHomeAsUpEnabled(true); 
	     actionBar.setHomeButtonEnabled(true);
	     View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.actionbar_view, null); 
	     TextView textView = (TextView) actionbarLayout.findViewById(R.id.actionBar);
	     textView.setText("联系人");
	     actionBar.setDisplayShowCustomEnabled(true); 
	     actionBar.setCustomView(actionbarLayout);
	     
	     //以下是设置listView的代码	     
	     mListView.setGroupIndicator(null); 
	     mAdapter = new ContactsExpandAdapter(this, mData); 
	     mListView.setAdapter(mAdapter); 
	     //必须要写这个，否则点击listView没反应。
	     mListView.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS); 
	     //以下是点击List的反应
	     mListView.setOnChildClickListener(new OnChildClickListener(){ 
	    	 public boolean onChildClick(ExpandableListView parent, View v,
	 				int groupPosition, int childPosition, long id) {
	 			String s1=null;
	 		    String s2=null;
	 		    ContactModel item = mAdapter.getChild(groupPosition, childPosition); 
	 			s1=item.getName();
	 			s2=item.getDetail();
	 			Toast.makeText(ArcMenuContacts.this, "name:" + s1 + " detail:" + s2, Toast.LENGTH_SHORT).show();
	 			return true; 
	 		    	 }
	     });
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
		   //使用xml文件中添加item菜单的方法
		 	MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	        // Inflate the menu; 使用代码添加菜单
	        //super.onCreateOptionsMenu(menu);
	        //MenuItem add=menu.add(0,0,0,"添加");
	        //绑定到ActionBar 
	        //add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	        return true;
	    }
	
	 private void initData() { 
	        for (int i = 0; i < mGroupArrays.length; i++) { 
	            List<ContactModel> list = new ArrayList<ContactModel>(); 
	            String[] childs = getStringArray(mGroupArrays[i]); 
	            String[] details = getStringArray(mDetailIds[i]); 
	            for (int j = 0; j < childs.length; j++) { 
	            	ContactModel item = new ContactModel(mImageIds[i][j], childs[j], details[j]); 
	                list.add(item); 
	            } 
	            mData.add(list); 
	        } 
	    } 
	 
	    private String[] getStringArray(int resId) { 
	        return getResources().getStringArray(resId); 
	    }

		
		
		public boolean onOptionsItemSelected(MenuItem item) {  
		    switch (item.getItemId()) {  
		        case android.R.id.home:  
		            Intent intent = new Intent(this, MainActivity.class);  
		            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
		            startActivity(intent);  
		            return true;  
		        default:  
		            return super.onOptionsItemSelected(item);  
		    }  
		}

	
}
