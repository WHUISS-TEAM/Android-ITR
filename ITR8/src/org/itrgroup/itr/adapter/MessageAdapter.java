package org.itrgroup.itr.adapter;

import java.util.List;

import org.itrgroup.itr.R;
import org.itrgroup.itr.model.MessageModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MessageAdapter extends BaseAdapter {
    
	private Context context;
     private List<MessageModel> messageList;
     
     public MessageAdapter(Context c,List<MessageModel> l){
    	 context=c;
         messageList=l;       
     }
     
 	@Override
 	public int getCount() {
 		// TODO Auto-generated method stub
 		return messageList.size();
 	}

 	@Override
 	public Object getItem(int position) {
 		// TODO Auto-generated method stub
 		return null;
 	}

 	@Override
 	public long getItemId(int position) {
 		// TODO Auto-generated method stub
 		return position;
 	}

 	@Override
 	public View getView(int position, View convertView, ViewGroup parent) {
 		 View rootView=LayoutInflater.from(context).inflate(
 				 R.layout.message_item, null);
 
 			
 			 ImageView msg_icon = (ImageView)rootView.findViewById(R.id.msg_icon);
 			 TextView  msg_name = (TextView)rootView.findViewById(R.id.msg_name);
 			 TextView  msg_text = (TextView)rootView.findViewById(R.id.msg_text);
 			 TextView  msg_time = (TextView)rootView.findViewById(R.id.msg_time);
 		
 			msg_icon.setBackgroundResource(messageList.get(position).icon);
 			msg_name.setText(messageList.get(position).userName);
 			msg_text.setText(messageList.get(position).message);
 			msg_time.setText(messageList.get(position).time);
 			 
 			 
 		 return rootView;
 	}
       
 }

     
