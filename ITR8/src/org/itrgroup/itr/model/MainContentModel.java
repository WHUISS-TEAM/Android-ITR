package org.itrgroup.itr.model;

import java.io.Serializable;

import org.itrgroup.itr.R;

import android.R.integer;



//使之可序列化
public class MainContentModel implements Serializable{
	public String userName;
	public String pubTime;
	public String fir_tag;
	public int fir_tag_img;
	public String sec_tag;
	public String pubContent;
	public int avatar_Name;
	public int share_num;
	public int comment_num;
	public int vote_num;
	//tagColor_num是不同的一级标签对应的颜色号，社团对应的是1，创意对应的是2，游玩对应3，学习对应4，招聘对应5
	

	public MainContentModel(String userName, String pubTime, String fir_tag,
			String sec_tag, String pubContent, int avatar_Name, int share_num, int comment_num, int vote_num) {
		super();
		this.userName = userName;
		this.pubTime = pubTime;
		this.fir_tag = fir_tag;
		this.sec_tag = sec_tag;
		this.pubContent = pubContent;
		this.avatar_Name = avatar_Name;
		this.share_num = share_num;
		this.comment_num = comment_num;
		this.vote_num = vote_num;
		this.fir_tag_img = setFirtagimg(fir_tag);
	}
	
	public int setFirtagimg(String fir_tag){
		if(fir_tag=="社团"){
			return R.drawable.firsttag_1;
		}
		if(fir_tag=="创意"){
			return R.drawable.firsttag_2;
		}
		if(fir_tag=="游玩"){
			return R.drawable.firsttag_3;
		}
		if(fir_tag=="学习"){
			return R.drawable.firsttag_4;
		}
		if(fir_tag=="招聘"){
			return R.drawable.firsttag_5;
		}
		
		return R.drawable.firsttag_2;
	}
}
