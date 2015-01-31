package org.itrgroup.itr.model;

public class SearchHotNewsModel {
	public int avatar_Name;
	public String pubContent;
	public int fir_tag;
	public String vote_num;
	

public SearchHotNewsModel(int avatar_Name,String pubContent,int fir_tag,String vote_num){
	super();
	this.avatar_Name = avatar_Name;
	this.pubContent=pubContent;
	this.fir_tag=fir_tag;
	this.vote_num=vote_num;
	
}

}