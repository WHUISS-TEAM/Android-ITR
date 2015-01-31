package org.itrgroup.itr.main;

import java.util.ArrayList;
import java.util.List;

import org.itrgroup.itr.R;
import org.itrgroup.itr.adapter.ImagesAdapter;
import org.itrgroup.itr.adapter.SearchHotNewsAdapter;
import org.itrgroup.itr.adapter.SpinnerAdapter;
import org.itrgroup.itr.model.SearchHotNewsModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;



public class SearchFragment extends Fragment {
	private Spinner mSpinner;
	private GridView gridView1;
	private GridView gridView2;
	private ImageButton imageButton;
	
	
	int[] images1 ={ R.drawable.avatar_1, R.drawable.avatar_2, R.drawable.avatar_3,
			R.drawable.avatar_4, R.drawable.avatar_5, R.drawable.avatar_6 };
	int[] images2={ R.drawable.avatar_10, R.drawable.avatar_11, R.drawable.avatar_12,
			R.drawable.avatar_13, R.drawable.avatar_7, R.drawable.avatar_8 };
	int[] drawableIds = { R.drawable.spinner_user, R.drawable.spinner_club, R.drawable.spinner_idea,
			R.drawable.spinner_fun, R.drawable.spinner_study, R.drawable.spinner_recruit};
	int[] spinnerTxt_Id={R.string.users,R.string.activity,R.string.creation,
			R.string.play,R.string.study,R.string.hire};
	
		
	

	
	
	//@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.fragment_search,null);
		
		
		
		ImagesAdapter iAdapter1=new ImagesAdapter(getActivity()
						.getApplicationContext(),images1);
		ImagesAdapter iAdapter2=new ImagesAdapter(getActivity()
				.getApplicationContext(),images2);
		SpinnerAdapter spinnerAdapter=new SpinnerAdapter(getActivity()
				.getApplicationContext(),drawableIds,spinnerTxt_Id);
	
		mSpinner = (Spinner) rootView.findViewById(R.id.spinner);
		mSpinner.setAdapter(spinnerAdapter);
		gridView1 = (GridView)rootView.findViewById(R.id.gridView1);
		gridView1.setAdapter(iAdapter1);
		gridView2=(GridView)rootView.findViewById(R.id.gridView2);
		gridView2.setAdapter(iAdapter2);
		imageButton=(ImageButton)rootView.findViewById(R.id.imagebutton);
		ListView newsContentList = (ListView)rootView.findViewById(R.id.newsContentList);
		
		List<SearchHotNewsModel> newsList=new ArrayList<SearchHotNewsModel>();
		newsList.add(new SearchHotNewsModel(R.drawable.avatar_1,"樱花节的时候有人一起晚上去逛樱顶吗？去的人请联系我123456789",R.drawable.firsttag_3,"1536人支持"));
		newsList.add(new SearchHotNewsModel(R.drawable.avatar_2,"武汉大学创新创业项目现在正式开始报名，请有意愿的同学于今天下午前找班长报名",R.drawable.firsttag_5,"1123人支持"));
		newsList.add(new SearchHotNewsModel(R.drawable.avatar_6,"秀玉红茶坊诚聘大学生兼职，工时每周16个小时，具体请到群光广场店咨询。",R.drawable.firsttag_2,"8999人支持"));
		
		SearchHotNewsAdapter newsAdapter=new SearchHotNewsAdapter(getActivity(),newsList);
		newsContentList.setAdapter(newsAdapter);
		
		return rootView;
		}


	}


	