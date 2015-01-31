package org.itrgroup.itr.adapter;

import java.util.List;

import org.itrgroup.itr.R;
import org.itrgroup.itr.model.SearchHotNewsModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchHotNewsAdapter extends BaseAdapter{
	private Context context;
	private List<SearchHotNewsModel> newsList;
public SearchHotNewsAdapter(Context c,List<SearchHotNewsModel> l){
	context=c;
	newsList=l;
	
}
public int getCount() {
	// TODO Auto-generated method stub
	return newsList.size();
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
            R.layout.news_item, null);
		// TODO Auto-generated method stub
	ImageView news_avatar=(ImageView)rootView.findViewById(R.id.news_avatar);
	TextView news_text=(TextView)rootView.findViewById(R.id.news_text);
	ImageView news_tag=(ImageView)rootView.findViewById(R.id.news_tag);
	TextView news_vote=(TextView)rootView.findViewById(R.id.news_vote);
	news_avatar.setBackgroundResource(newsList.get(position).avatar_Name);
	news_text.setText(newsList.get(position).pubContent);
	news_tag.setBackgroundResource(newsList.get(position).fir_tag);
	news_vote.setText(newsList.get(position).vote_num);
	
		return rootView;
	}
	

}
