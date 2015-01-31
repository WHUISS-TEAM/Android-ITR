package org.itrgroup.itr.menucontents;


import org.itrgroup.itr.R;
import org.itrgroup.itr.main.MainActivity;
import org.itrgroup.itr.utils.FragmentUtils;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

//一共设置5个列表项，每一个列表项点击以后都对应一个activity，每个activity中要加载一个preferencefragment，
//总共需要5个xml文件定义preference布局
public class ArcMenuSetting extends PreferenceActivity {

	private FragmentUtils utils = new FragmentUtils();
	private ActionBar bar = null;
	private static CharSequence title = "设置";
	public static int[] icon_resource = new int[]{R.drawable.my_account,R.drawable.new_message,R.drawable.my_private
			,R.drawable.universal,R.drawable.about};
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LaunchFragment launchFragment = new LaunchFragment();
		utils.iniFragment(launchFragment, this);
		utils.setHomeActionBar(bar, this, title);
		
	}
	
	public static class LaunchFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.launch_preference);
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			int count = preferenceScreen.getPreferenceCount();
			for(int i=0;i<count;i++){
				Preference p = preferenceScreen.getPreference(i);
				p.setIcon(getResources().getDrawable(icon_resource[i]));
			}
		}
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			utils.setIntent(this, MainActivity.class);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
