package org.itrgroup.itr.adapter;

import org.itrgroup.itr.main.MainFragment;
import org.itrgroup.itr.main.MsgFragment;
import org.itrgroup.itr.main.ProfileFragment;
import org.itrgroup.itr.main.SearchFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private MainFragment mainFragment = new MainFragment();
	private MsgFragment msgFragment = new MsgFragment();
	private ProfileFragment profileFragment = new ProfileFragment();
	private SearchFragment searchFragment = new SearchFragment();
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Main Page fragment activity
			return mainFragment;
		case 1:
			// Messages fragment activity
			return msgFragment;
		case 2:
			// Profile fragment activity
			return profileFragment;
		case 3:
			// Search fragment activity
			return searchFragment;
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
