package org.itrgroup.itr.utils;

import org.itrgroup.itr.R;


public class IconForProfileFragment {
	private int[] icon_resource = new int[]{R.drawable.profile_icon_food,R.drawable.i_literature,R.drawable.i_movie
			,R.drawable.profile_icon_music,R.drawable.i_soccer,R.drawable.profile_icon_travel,R.drawable.i_writting
			,R.drawable.profile_icon_game,R.drawable.i_math,R.drawable.profile_icon_volleyball,R.drawable.profile_icon_badminton};
	private String[] icon_name_resource = new String[]{"美食","文学","电影","音乐","足球","旅行","文学创作"
			,"游戏","数学","排球","羽毛球"};
	
	
	public int match_icon_with_name(String text){
		int resource = 0;
		for(int i=0;i<icon_name_resource.length;i++){
			if(text.equals(icon_name_resource[i])){
				resource = icon_resource[i];
				break;
			}
		}
		return resource;
	}
}
