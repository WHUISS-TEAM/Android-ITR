package org.itrgroup.itr.adapter;



import org.itrgroup.itr.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class ImagesAdapter extends BaseAdapter{
private Context context;
private int[] imagesId;
public ImagesAdapter(Context c,int[]  i){
	context=c;
	imagesId=i;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imagesId.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imagesId[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View contentView = LayoutInflater.from(context).inflate(
                R.layout.search_cell, null);
		ImageView images=(ImageView)contentView .findViewById(R.id.imageView1);
		images.setBackgroundResource(imagesId[position]);
		return contentView ;
	}
	

}
