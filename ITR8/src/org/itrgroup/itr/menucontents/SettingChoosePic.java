package org.itrgroup.itr.menucontents;

import org.itrgroup.itr.R;
import org.itrgroup.itr.utils.FragmentUtils;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

public class SettingChoosePic extends PreferenceActivity{
	
	private FragmentUtils utils = new FragmentUtils();
	private ActionBar bar = null;
	private String title = "";
	private static int[] icon = new int[]{R.drawable.get_background_from_system,R.drawable.get_background_from_album,R.drawable.get_from_take_photo};
	
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		utils.setHomeActionBar(bar, this, title);
		ChoosePicFragment choosePicFragment = new ChoosePicFragment();
		utils.iniFragment(choosePicFragment, this);
	}



	public static class ChoosePicFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.choose_pic);
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			int count = preferenceScreen.getPreferenceCount();
			for(int i=0;i<count;i++){
				Preference p = preferenceScreen.getPreference(i);
				p.setIcon(icon[i]);
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			utils.setIntent(this, SettingUniversalPreference.class);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}


