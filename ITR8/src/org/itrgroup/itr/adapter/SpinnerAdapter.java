package org.itrgroup.itr.adapter;

import org.itrgroup.itr.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter{
	private Context context;
	private int[] spinnerImgs_Id;
	private int[] spinnerTxt_Id;
public SpinnerAdapter(Context c,int[] i_1,int[] i_2){
	context=c;
	spinnerImgs_Id=i_1;
	spinnerTxt_Id=i_2;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return spinnerImgs_Id.length;
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
		// TODO Auto-generated method stub
		View contentView = LayoutInflater.from(context).inflate(
                R.layout.spinner_layout, null);
		ImageView spinner_images=(ImageView)contentView.findViewById(R.id.spinner_image);
		TextView spinner_text=(TextView)contentView.findViewById(R.id.spinner_text);
		spinner_images.setBackgroundResource(spinnerImgs_Id[position]);
		spinner_text.setText(spinnerTxt_Id[position]);
		return contentView;
	}
}
