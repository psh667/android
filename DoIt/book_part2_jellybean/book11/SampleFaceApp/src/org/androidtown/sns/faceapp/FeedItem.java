package org.androidtown.sns.faceapp;

public class FeedItem {

	private String name;
	private String date;
	private String message;
	private String picture;

	public FeedItem(String inName, String inDate, String inMessage, String inPicture) {
		name = inName;
		date = inDate;
		message = inMessage;
		picture = inPicture;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}



}
