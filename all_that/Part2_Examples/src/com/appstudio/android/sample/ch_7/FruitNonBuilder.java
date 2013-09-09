package com.appstudio.android.sample.ch_7;

public class FruitNonBuilder {
	private String apple;
	private String orange;
	private String grape;
	private String banana;
	private String melon;
	
	public FruitNonBuilder(String apple, String orange, String grape,
			String banana, String melon) {
		super();
		this.apple = apple;
		this.orange = orange;
		this.grape = grape;
		this.banana = banana;
		this.melon = melon;
	}
	public String getApple() {
		return apple;
	}
	public void setApple(String apple) {
		this.apple = apple;
	}
	public String getOrange() {
		return orange;
	}
	public void setOrange(String orange) {
		this.orange = orange;
	}
	public String getGrape() {
		return grape;
	}
	public void setGrape(String grape) {
		this.grape = grape;
	}
	public String getBanana() {
		return banana;
	}
	public void setBanana(String banana) {
		this.banana = banana;
	}
	public String getMelon() {
		return melon;
	}
	public void setMelon(String melon) {
		this.melon = melon;
	}
	@Override
	public String toString() {
		return "FruitNonBuilder [apple=" + apple + ", orange=" + orange
				+ ", grape=" + grape + ", banana=" + banana + ", melon="
				+ melon + "]";
	}
	
}


