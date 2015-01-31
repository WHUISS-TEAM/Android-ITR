package org.itrgroup.itr.utils;

public class Person_info {


	public int head_image;
	public String name;
	public String describtion;
	public String[] label;
	public String school;
	public String college;
	public String major;
	public String myClass;
	public String dorm;
	public String year;
	public String sex;
	public String age;
	public String[] interest;
	public String[] experience;
	public String[] party;
	
	//在设置界面的account需要的信息
	public String account;
	public String phone_num;
	public String e_mail;
	
	
	
	public Person_info() {
		super();
	}

	

	public Person_info(String account, String phone_num, String e_mail) {
		super();
		this.account = account;
		this.phone_num = phone_num;
		this.e_mail = e_mail;
	}



	public Person_info(int head_image,String name, String describe, String[] label,
			String school, String college, String major, String myClass,
			String dorm, String year, String sex, String age,
			String[] interest, String[] experience, String[] party) {
		super();
		this.head_image = head_image;
		this.name = name;
		this.describtion = describe;
		this.label = label;
		this.school = school;
		this.college = college;
		this.major = major;
		this.myClass = myClass;
		this.dorm = dorm;
		this.year = year;
		this.sex = sex;
		this.age = age;
		this.interest = interest;
		this.experience = experience;
		this.party = party;
	}
	
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescribtion() {
		return describtion;
	}



	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}



	public String[] getLabel() {
		return label;
	}



	public void setLabel(String[] label) {
		this.label = label;
	}



	public String getSchool() {
		return school;
	}



	public void setSchool(String school) {
		this.school = school;
	}



	public String getCollege() {
		return college;
	}



	public void setCollege(String college) {
		this.college = college;
	}



	public String getMajor() {
		return major;
	}



	public void setMajor(String major) {
		this.major = major;
	}



	public String getMy_class() {
		return myClass;
	}



	public void setMy_class(String myClass) {
		this.myClass = myClass;
	}



	public String getDorm() {
		return dorm;
	}



	public void setDorm(String dorm) {
		this.dorm = dorm;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}



	public String getAge() {
		return age;
	}



	public void setAge(String age) {
		this.age = age;
	}



	public String[] getInterest() {
		return interest;
	}



	public void setInterest(String[] interest) {
		this.interest = interest;
	}



	public String[] getExperience() {
		return experience;
	}



	public void setExperience(String[] experience) {
		this.experience = experience;
	}



	public String[] getParty() {
		return party;
	}



	public void setParty(String[] party) {
		this.party = party;
	}



	public String getAccount() {
		return account;
	}



	public void setAccount(String account) {
		this.account = account;
	}



	public String getPhone_num() {
		return phone_num;
	}



	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}



	public String getE_mail() {
		return e_mail;
	}



	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public int getHead_image() {
		return head_image;
	}



	public void setHead_image(int head_image) {
		this.head_image = head_image;
	}

	
	
	
}
