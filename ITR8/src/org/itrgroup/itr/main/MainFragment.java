package org.itrgroup.itr.main;

import java.util.ArrayList;
import java.util.List;

import org.itrgroup.itr.R;
import org.itrgroup.itr.adapter.MainContentAdapter;
import org.itrgroup.itr.arcbutton.ArcMenu;
import org.itrgroup.itr.model.MainContentModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;



public class MainFragment extends Fragment {
	
	//Sub-button of the Circle button
	private static final int[] ITEM_DRAWABLES = { R.drawable.button_publish, R.drawable.button_contacts,
		R.drawable.button_database, R.drawable.button_setting };
	private List<MainContentModel> contentList = null;
	private ListView pubContentList = null;
	private MainContentAdapter mContentAdapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		//rootView.findViewById is avaliable on fragment
		//get arcMenu from layout file
		ArcMenu arcMenu = (ArcMenu)rootView.findViewById(R.id.arc_menu);
		//Initial arcMenu
		((MainActivity)getActivity()).initArcMenu(arcMenu, ITEM_DRAWABLES);
		
		//ListView contains all the published contents of main view
		pubContentList = (ListView)rootView.findViewById(R.id.pubContentList);
		
		//model
		contentList=new ArrayList<MainContentModel>();
		contentList.add(new MainContentModel("destiny �r", "2014.3.13 20:20", "����",
				"#��ѧ��  #������  #��ϵ��ʽ", "���������ѧ�磬˭�ܸ�������ô�ܼ��밡�������������ظ����˵���ϵ��ʽ����л����", 
				R.drawable.avatar_1,246,258,369));
		contentList.add(new MainContentModel("����", "2014.3.13 23:03", "����",
				"#������Ŀ  #�������  #����  #APP #���", "��������һ��������Ŀ�����ֽ�ITR(Idea To Reality)����˼�ǰ��뷨��¼��һ��APP�ڣ����APP�ܰ���Ѱ�ҵ����ʵĺ������...", 
				R.drawable.avatar_2,223,458,549));
		contentList.add(new MainContentModel("albert", "2014.3.13 20:03", "����",
				"#���г�  #����  #��ĩ  #��  #����", "����ĩ�����������г��ƶ���һȦ���еĿ��Է���˽�ţ�����~", 
				R.drawable.avatar_3,253,854,235));
		contentList.add(new MainContentModel("��ľ��", "2014.3.15 14:34", "ѧϰ",
				"#ѧϰ  #���Դ���  #��ĩ  #����  #ѧ��", "��ľ��ѧ��ͬѧ��ĩ���Խ����ߴ���ҵ��ôд�����߰�ȥ��Ŀ�����Ŀ������...лл��",
				R.drawable.avatar_4,354,315,458));
		contentList.add(new MainContentModel("�λ�֮ҹ", "2014.3.13 20:20", "ѧϰ",
				"#ʵѵ  #��Ŀ  #C++ #����", "����ѧУ��C++ʵѵ��Ŀ�����Ѱ������˿���ָ����һ����?",
				R.drawable.avatar_5,245,864,234));
		contentList.add(new MainContentModel("�����跻", "2014.3.13 20:20", "��Ƹ",
				"#��Ƹ  #��ְ  #�����跻", "�����跻�������Ҽ�����ѧ������ְ��������������Ը�Ĵ�ѧ��������ϵ�ҡ�: )",
				R.drawable.avatar_6,254,975,248));

		//An adapter to handle this messages
		mContentAdapter=new MainContentAdapter(getActivity(),contentList);
		pubContentList.setAdapter(mContentAdapter);
		
		return rootView;
	}
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			Bundle bundle = ((MainActivity)getActivity()).getBundle();
			if(bundle!=null){
				MainContentModel new_msg = (MainContentModel) bundle.getSerializable("new_msg");
				contentList.add(0, new_msg);
				((MainActivity)getActivity()).setBundle(null);
			}
		}
	
}
