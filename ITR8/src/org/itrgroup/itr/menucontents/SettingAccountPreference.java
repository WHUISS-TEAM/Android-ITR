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

public class SettingAccountPreference extends PreferenceActivity{

	private ActionBar bar = null;
	private FragmentUtils utils = new FragmentUtils();
	private static CharSequence title = "";
	
	private static int[] key = new int[]{R.string.account_preference_account_num,R.string.account_preference_phone_num,R.string.account_preference_email};
	private static String[] info = new String[]{"albertlee","","liyulei1995314@gmail.com"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AccountFragment accountFragment = new AccountFragment();
		utils.iniFragment(accountFragment,this);
		utils.setHomeActionBar(bar,this,title);
	}
	
	public static class AccountFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub		
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.account_preference);
			PreferenceScreen preferenceScreen = getPreferenceScreen();
			for(int i=0;i<3;i++){
				Preference preference = preferenceScreen.findPreference(getResources().getString(key[i]));
				if(info[i].equals("")){
					preference.setSummary("Î´°ó¶¨");
				}
				else
				preference.setSummary(info[i]);
			}
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


