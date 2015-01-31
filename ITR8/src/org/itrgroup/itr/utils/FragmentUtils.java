package org.itrgroup.itr.utils;

import org.itrgroup.itr.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;


public class FragmentUtils{

	public FragmentUtils() {
		// TODO Auto-generated constructor stub
	}
	public void iniFragment(Fragment f,Activity a){
		FragmentManager manager = a.getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(android.R.id.content, f);
		//transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public void setHomeActionBar(ActionBar ab,Activity a,CharSequence title){
		ab = a.getActionBar();
		ab.setIcon(R.drawable.back_icon);
		ab.setTitle(title);
		ab.setHomeButtonEnabled(true);
		
	}
	
	public void setIntent(Context action,Class<?> target){
		Intent intent = new Intent(action, target);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		action.startActivity(intent);
	}
}
