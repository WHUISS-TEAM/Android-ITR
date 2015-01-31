package org.itrgroup.itr.main;

import org.itrgroup.itr.R;
import org.itrgroup.itr.utils.IconForProfileFragment;
import org.itrgroup.itr.utils.NoScrollGridView;
import org.itrgroup.itr.utils.Person_info;

import android.R.drawable;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

	//The instantiation of this fragment layout file
	private View root_layout = null;
	MainActivity mainActivity = null;
	//The instantiation of this fragment Gridlayout file
	private GridLayout layout = null;
	private LayoutInflater layoutInflater = null;
	private static int COLUMN_NUM = 0;
	private int text_size_one = 17;
	private int text_size_two = 15;
	
	//Various filling data
	private String name = "River";
	private String describe = "软件工程专业新生";
	private String[] label = new String[]{"#软件工程","#游玩","#吉他社","#国际班","#英语"};
	private int head_image;
	
	private String school = "武汉大学";
	private String college = "国际软件学院";
	private String major = "软件工程";
	private String myClass = "国际一班";
	private String dorm = "C3-206";
	private String year = "2013";
	
	private String sex = "男";
	private String age = "19";
	private String[] interest = new String[]{"游戏","旅行","羽毛球","排球","美食","音乐"};
	
	private String[] experience = new String[]{"武大社团节后勤","国际软件学院元旦晚会","武汉大学创新创业项目","武汉大学金秋艺术节"};
	
	private String[] party = new String[]{"国际软件学院学生会文艺部","校社联外联部","青鸟吉他协会","武汉大学公益协会"};
	
	private Person_info person = new Person_info(head_image, name, describe, label, school, college, major, myClass, dorm, year, sex, age, interest, experience, party);
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mainActivity = (MainActivity)getActivity();
		layoutInflater = LayoutInflater.from(mainActivity);
				
		root_layout = layoutInflater.inflate(R.layout.profilexml, null);
		
		layout = (GridLayout)root_layout.findViewById(R.id.root);

		
		//Show every gridlayout
		getRowOne();
		getRowTwo();
		getRowThree();
		getRowFour();
		getRowFive();
		
		return root_layout;
	}
	
	private View getView(int resource){
		View layoutView = layoutInflater.inflate(resource, null);
		return layoutView;
	}
	
	private void getRowOne(){
		int row = 0;
		int column = COLUMN_NUM;
		GridLayout.Spec rowSpec = GridLayout.spec(row);
		GridLayout.Spec columnSpec = GridLayout.spec(column);
		GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,columnSpec);
		View myView = getView(R.layout.profile_rowone);
		head_image = R.drawable.new_head;
		ImageView image = (ImageView)myView.findViewById(R.id.picone);
		image.setImageResource(head_image);
		TextView textName = (TextView)myView.findViewById(R.id.name);
		textName.setText(name);
		textName.setClickable(true);
		//registerForContextMenu(textName);
		TextView textDescribe = (TextView)myView.findViewById(R.id.describe);
		textDescribe.setText(describe);
		textDescribe.setClickable(true);
		registerForContextMenu(textDescribe);
		//GridView gridOne = (GridView)myView.findViewById(R.id.gridone);
		//gridOne.setAdapter(new GridBaseAdapterOne());
		NoScrollGridView gridOne = (NoScrollGridView)myView.findViewById(R.id.gridone);
		gridOne.setAdapter(new GridBaseAdapterOne());
		layout.addView(myView, params);
	}
	
	private void getRowTwo(){
		int row = 1;
		int column = COLUMN_NUM;
		GridLayout.Spec rowSpec = GridLayout.spec(row);
		GridLayout.Spec columnSpec = GridLayout.spec(column);
		GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,columnSpec); 
		View myView = getView(R.layout.profile_rowtwo);
		TextView schoolText = (TextView)myView.findViewById(R.id.school);
		schoolText.setText(person.school + " - " + person.college);
		TextView majorText = (TextView)myView.findViewById(R.id.major);
		majorText.setText(person.major);
		TextView classText = (TextView)myView.findViewById(R.id.myclass);
		classText.setText(person.myClass);
		TextView dormText = (TextView)myView.findViewById(R.id.dorm);
		dormText.setText(person.dorm);
		TextView yearText = (TextView)myView.findViewById(R.id.year);
		yearText.setText(person.year);
		layout.addView(myView, params);
	}
	
	private void getRowThree(){
		int row = 2;
		int column = COLUMN_NUM;
		GridLayout.Spec rowSpec = GridLayout.spec(row);
		GridLayout.Spec columnSpec = GridLayout.spec(column);
		GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
		View myView = getView(R.layout.profile_rowthree);
		TextView sexText = (TextView)myView.findViewById(R.id.sex);
		sexText.setText(person.sex);
		TextView ageText = (TextView)myView.findViewById(R.id.age);
		ageText.setText(person.age);
		//GridView interestGrid = (GridView)myView.findViewById(R.id.interests);
		//interestGrid.setAdapter(new GridBaseAdapterThree());
		NoScrollGridView interestGrid = (NoScrollGridView)myView.findViewById(R.id.interests);
		interestGrid.setAdapter(new GridBaseAdapterThree());
		layout.addView(myView,params);
	}
	
	private void getRowFour(){
		int row = 3;
		int column = COLUMN_NUM;
		GridLayout.Spec rowSpec = GridLayout.spec(row);
		GridLayout.Spec columnSpec = GridLayout.spec(column);
		GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
		View myView = getView(R.layout.profile_rowfour);
		NoScrollGridView experienceGrid = (NoScrollGridView)myView.findViewById(R.id.experience);
		experienceGrid.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView item = new TextView(mainActivity);
				item.setText((String)getItem(position));
				item.setEllipsize(TruncateAt.END);
				item.setSingleLine();
				item.setGravity(Gravity.LEFT);
				item.setTextSize(TypedValue.COMPLEX_UNIT_SP, text_size_two);
				item.setTextColor(getResources().getColor(R.color.small_text_color));
				registerForContextMenu(item);
				return item;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return person.experience[position];
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				if(person.experience.length <= 4){
					return person.experience.length;
				}
				else 
					return 4;
				
			}
		});
		layout.addView(myView,params);
	}
	
	private void getRowFive(){
		int row = 4;
		int column = COLUMN_NUM;
		GridLayout.Spec rowSpec = GridLayout.spec(row);
		GridLayout.Spec columnSpec = GridLayout.spec(column);
		GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
		View myView = getView(R.layout.profile_rowfive);
		NoScrollGridView partyGrid = (NoScrollGridView)myView.findViewById(R.id.party);
		partyGrid.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView item = new TextView(mainActivity);
				item.setText((String)getItem(position));
				item.setSingleLine();
				item.setEllipsize(TruncateAt.END);
				item.setGravity(Gravity.LEFT);
				item.setTextSize(TypedValue.COMPLEX_UNIT_SP, text_size_two);
				item.setTextColor(getResources().getColor(R.color.small_text_color));
				registerForContextMenu(item);
				return item;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return person.party[position];
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				if(person.party.length <= 4){
					return person.party.length;
				}
				else
					return 4;
				
			}
		});
		layout.addView(myView, params);
	}
	
	
	private class GridBaseAdapterOne extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(person.label.length <= 6){
				return person.label.length;
			}
			else 
				return 6;
			
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return person.label[arg0];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView itemText = new TextView(mainActivity);
			itemText.setText((String)getItem(position));
			itemText.setSingleLine(true);
			itemText.setEllipsize(TruncateAt.END);
			itemText.setTextSize(TypedValue.COMPLEX_UNIT_SP, text_size_two);
			itemText.setTextColor(getResources().getColor(R.color.small_text_color));
			itemText.setTypeface(null, Typeface.ITALIC);
			registerForContextMenu(itemText);
			return itemText;
		}
		
	}
	
	private class GridBaseAdapterThree extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(person.interest.length <= 6){
				return person.interest.length;
			}
			else return 6;
			
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return person.interest[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout linearLayout = new LinearLayout(mainActivity);
			linearLayout.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
			TextView item = new TextView(mainActivity);
			item.setText((String)getItem(position));
			item.setSingleLine();
			item.setEllipsize(TruncateAt.END);
			item.setTextSize(TypedValue.COMPLEX_UNIT_SP, text_size_two);
			item.setTextColor(getResources().getColor(R.color.small_text_color));
			item.setTypeface(null,Typeface.ITALIC);
			item.setPadding(5, 0, 0, 0);
			registerForContextMenu(item);
			IconForProfileFragment iconresource = new IconForProfileFragment();
			ImageView icon = new ImageView(mainActivity);
			icon.setImageResource(iconresource.match_icon_with_name((String)getItem(position)));
			linearLayout.addView(icon);
			linearLayout.addView(item);
			//registerForContextMenu(item);
			return linearLayout;
		}
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		TextView text = (TextView)v;
		String content = (String) text.getText();
		menu.setHeaderIcon(drawable.ic_menu_info_details);
		menu.add(content);
	}


}

