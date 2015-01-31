package org.itrgroup.itr.adapter;

import java.util.List;

import org.itrgroup.itr.R;
import org.itrgroup.itr.model.MainContentModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainContentAdapter extends BaseAdapter{
	
	private Context context;
	private List<MainContentModel> contentList;
	private int[] colors = new int[] { 0xFF1E90FF, 0xFFFF0000, 0xFF66CD00, 0xFFFFFF00, 0xFF8B008B };
	
	public MainContentAdapter(Context c,List<MainContentModel> l){
		context=c; 
		contentList=l; 
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return contentList.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		//return 0
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rootView = LayoutInflater.from(context).inflate(
                R.layout.content_item, null);
		
		ImageView user_avatar = (ImageView)rootView.findViewById(R.id.user_avatar);  //用户头像
		TextView user_name = (TextView)rootView.findViewById(R.id.user_name);		 //用户名
		TextView publish_time = (TextView)rootView.findViewById(R.id.publish_time);  //发布时间
		ImageView fir_tag = (ImageView)rootView.findViewById(R.id.fir_tag);  //一级标签
		TextView publish_content = (TextView)rootView.findViewById(R.id.publish_content);	 //发布内容
		TextView sec_tag = (TextView)rootView.findViewById(R.id.sec_tag);	  //二级标签
		Button share = (Button)rootView.findViewById(R.id.share);	//分享按钮
		Button comment = (Button)rootView.findViewById(R.id.comment);	//评论按钮
		Button vote = (Button)rootView.findViewById(R.id.vote);	//投票按钮
		
		user_name.setText(contentList.get(position).userName);
		publish_time.setText(contentList.get(position).pubTime);
		fir_tag.setBackgroundResource(contentList.get(position).fir_tag_img);
		publish_content.setText(contentList.get(position).pubContent);
		sec_tag.setText(contentList.get(position).sec_tag);
		user_avatar.setBackgroundResource(contentList.get(position).avatar_Name);
		share.setText("分享(" + contentList.get(position).share_num +")");
		comment.setText("评论(" + contentList.get(position).comment_num + ")");
		vote.setText("赞同(" + contentList.get(position).vote_num + ")");
		
		return rootView;
	}
	
}
