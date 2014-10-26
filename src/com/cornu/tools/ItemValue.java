package com.cornu.tools;

import java.io.Serializable;

import android.content.ContentValues;

public class ItemValue implements Serializable{
	public static int NEW=1;
	public static int SOLVED=2;
	int id;
	String type="";
	String subject="";
	String book="";
	int page=0;
	int ordernum=0;
	String description="";
	int state;
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ContentValues getContentValues(){
		ContentValues value=new ContentValues();
		value.put("type", this.getType());
		value.put("subject", this.getSubject());
		value.put("book", this.getBook());
		value.put("page", this.getPage());
		value.put("ordernum", this.getOrdernum());
		value.put("description", this.getDescription());
		value.put("state", this.getState());
		return value;
		
	}
}
