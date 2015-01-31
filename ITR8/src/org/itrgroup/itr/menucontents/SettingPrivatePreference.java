package org.itrgroup.itr.menucontents;


import org.itrgroup.itr.R;
import org.itrgroup.itr.utils.FragmentUtils;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

public class SettingPrivatePreference extends PreferenceActivity{
	
	private FragmentUtils utils = new FragmentUtils();
	private ActionBar bar = null;
	private static CharSequence title = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		PrivateFragment privateFragment = new PrivateFragment();
		utils.setHomeActionBar(bar, this, title);
		utils.iniFragment(privateFragment, this);
	}
	
	public static class PrivateFragment extends PreferenceFragment{
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.private_preference);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			utils.setIntent(this, ArcMenuSetting.class);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
