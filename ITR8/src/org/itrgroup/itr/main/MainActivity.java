package org.itrgroup.itr.main;

import org.itrgroup.itr.R;
import org.itrgroup.itr.adapter.TabsPagerAdapter;
import org.itrgroup.itr.arcbutton.ArcMenu;
import org.itrgroup.itr.login.LoginActivity;
import org.itrgroup.itr.menucontents.ArcMenuContacts;
import org.itrgroup.itr.menucontents.ArcMenuPublish;
import org.itrgroup.itr.menucontents.ArcMenuSetting;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {



	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private static int REQUEST_CODE_0 = 0;
	private Bundle bundle = null;
	
	//用于设置自动登陆
	private SharedPreferences sp;
	
	// 4 tabs icon
	private int[] ICONS = new int[] {
            R.drawable.tab_main,
            R.drawable.tab_msg,
            R.drawable.tab_profile,
            R.drawable.tab_search,
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		
		sp = this.getSharedPreferences("userInfo", this.MODE_WORLD_READABLE);
		
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//hide actionbar
		actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

		// Load tabs into main view
		for (int i=0; i < ICONS.length; i++) {
			actionBar.addTab(actionBar.newTab()
					.setIcon(ICONS[i])
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	
	public void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new OnClickListener() {

                @Override
                public void onClick(View v) {
                	switch (position) {
                	case 0:
                    	Intent intent0 = new Intent(MainActivity.this,ArcMenuPublish.class);
                    	startActivityForResult(intent0, REQUEST_CODE_0);
                    	break;
                	case 1:
                		Intent intent1=new Intent(MainActivity.this,ArcMenuContacts.class);
                		startActivity(intent1);
                		break;
                	case 2:
                		//退出登录状态回到登录界面
                		Editor editor = sp.edit();
                		editor.putBoolean("IS_CHECKED", false);
                		editor.commit();
                		startActivity(new Intent(MainActivity.this,LoginActivity.class));
                		
                		Toast toast = Toast.makeText(MainActivity.this, "公共数据库暂未开发", Toast.LENGTH_SHORT);
                    	break;
					case 3:
						Intent intent3 = new Intent(MainActivity.this,ArcMenuSetting.class);
						startActivity(intent3);

						break;

					}
                   
                }
            });
        }
    }
	
	//新增加的方法，对应于后面的用requestcode启动activity
	//也可以用来判断传递来的intent来自哪个activity
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0 == REQUEST_CODE_0){
			if(arg1 == 0){
				bundle = arg2.getExtras();
			}
		}
	}
	//用这个传递信息到fragment中
	//本来使用setArguments，但是在fragment已经放在fragment manager以后再使用此方法会报错
	public Bundle getBundle(){
		return bundle;
	}
	
	public void setBundle(Bundle bundle){
		this.bundle = bundle;
	}
	
	
}
