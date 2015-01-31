package org.itrgroup.itr.model;

import java.io.Serializable;

import org.itrgroup.itr.R;

import android.R.integer;



//ʹ֮�����л�
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
	//tagColor_num�ǲ�ͬ��һ����ǩ��Ӧ����ɫ�ţ����Ŷ�Ӧ����1�������Ӧ����2�������Ӧ3��ѧϰ��Ӧ4����Ƹ��Ӧ5
	

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
		if(fir_tag=="����"){
			return R.drawable.firsttag_1;
		}
		if(fir_tag=="����"){
			return R.drawable.firsttag_2;
		}
		if(fir_tag=="����"){
			return R.drawable.firsttag_3;
		}
		if(fir_tag=="ѧϰ"){
			return R.drawable.firsttag_4;
		}
		if(fir_tag=="��Ƹ"){
			return R.drawable.firsttag_5;
		}
		
		return R.drawable.firsttag_2;
	}
}
