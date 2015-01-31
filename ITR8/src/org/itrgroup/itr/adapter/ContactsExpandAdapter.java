
package org.itrgroup.itr.adapter;

import java.util.List;

import org.itrgroup.itr.R;
import org.itrgroup.itr.model.ContactModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * The ExpandableAdapter for this listView.
 *
 */
public class ContactsExpandAdapter extends BaseExpandableListAdapter  {

	 private Context mContext; 
	 private LayoutInflater mInflater = null; 
	 private String[] mGroupStrings = null; 
	 private List<List<ContactModel>> mData = null; 
	
	
	 public ContactsExpandAdapter(Context ctx, List<List<ContactModel>> list) { 
	        mContext = ctx; 
	        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	        mGroupStrings = mContext.getResources().getStringArray(R.array.groups); 
	        mData = list; 
	    } 
	 
	 public void setData(List<List<ContactModel>> list) { 
	        mData = list; 
	    } 
	 
	 @Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mData.get(groupPosition).size();
	}

	@Override
	public List<ContactModel> getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mData.get(groupPosition);
	}

	@Override
	public ContactModel getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//mGroupIcon是groupView前面的那个箭头
		if (convertView == null) { 
            convertView = mInflater.inflate(R.layout.group_item_layout, null); 
        } 
		GroupViewHolder holder = new GroupViewHolder(); 
        holder.mGroupIcon=(ImageView)convertView.findViewById(R.id.listIcon);
        if(isExpanded){
        	holder.mGroupIcon.setBackgroundResource(R.drawable.arrow_down);
        }else{
        	holder.mGroupIcon.setBackgroundResource(R.drawable.arrow);
        }
        holder.mGroupName = (TextView) convertView 
                .findViewById(R.id.group_name); 
        holder.mGroupName.setText(mGroupStrings[groupPosition]); 
        holder.mGroupCount = (TextView) convertView 
                .findViewById(R.id.group_count); 
        holder.mGroupCount.setText("[" + mData.get(groupPosition).size() + "]"); 
        return convertView; 
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 if (convertView == null) { 
	            convertView = mInflater.inflate(R.layout.child_item_layout, null); 
	        } 
	        ChildViewHolder holder = new ChildViewHolder(); 
	        holder.mIcon = (ImageView) convertView.findViewById(R.id.img); 
	        holder.mIcon.setBackgroundResource(getChild(groupPosition, 
	                childPosition).getImageId()); 
	        holder.mChildName = (TextView) convertView.findViewById(R.id.item_name); 
	        holder.mChildName.setText(getChild(groupPosition, childPosition) 
	                .getName()); 
	        holder.mDetail = (TextView) convertView.findViewById(R.id.item_detail); 
	        holder.mDetail.setText(getChild(groupPosition, childPosition) 
	                .getDetail()); 
	        return convertView; 
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	 private class GroupViewHolder { 
	        TextView mGroupName; 
	        TextView mGroupCount; 
	        ImageView mGroupIcon;
	    } 
	 
	 private class ChildViewHolder { 
	        ImageView mIcon; 
	        TextView mChildName; 
	        TextView mDetail; 
	    } 

}
