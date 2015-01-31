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
		contentList.add(new MainContentModel("destiny ╮", "2014.3.13 20:20", "社团",
				"#文学社  #加社团  #联系方式", "我想加入文学社，谁能告诉我怎么能加入啊？？最好能有相关负责人的联系方式，感谢！！", 
				R.drawable.avatar_1,246,258,369));
		contentList.add(new MainContentModel("柴者", "2014.3.13 23:03", "创意",
				"#创新项目  #合作伙伴  #创意  #APP #设计", "我想申请一个创新项目，名字叫ITR(Idea To Reality)，意思是把想法记录在一个APP内，这个APP能帮我寻找到合适的合作伙伴...", 
				R.drawable.avatar_2,223,458,549));
		contentList.add(new MainContentModel("albert", "2014.3.13 20:03", "游玩",
				"#自行车  #东胡  #周末  #玩  #找人", "这周末有人想骑自行车绕东胡一圈吗？有的可以发我私信，走起~", 
				R.drawable.avatar_3,253,854,235));
		contentList.add(new MainContentModel("左岸木笔", "2014.3.15 14:34", "学习",
				"#学习  #线性代数  #周末  #考题  #学霸", "有木有学霸同学周末可以教我线代作业怎么写，或者把去年的考试题目发给我...谢谢呐",
				R.drawable.avatar_4,354,315,458));
		contentList.add(new MainContentModel("梦幻之夜", "2014.3.13 20:20", "学习",
				"#实训  #项目  #C++ #大神", "在做学校的C++实训项目，好难啊，有人可以指点我一下吗?",
				R.drawable.avatar_5,245,864,234));
		contentList.add(new MainContentModel("秀玉红茶坊", "2014.3.13 20:20", "招聘",
				"#招聘  #兼职  #秀玉红茶坊", "秀玉红茶坊本月想找几个大学生做兼职，福利不错，有意愿的大学生可以联系我。: )",
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
