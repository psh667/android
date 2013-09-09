package com.appstudio.android.sample.ch_7;

public class Fruit {
	private String apple;
	private String orange;
	private String grape;
	private String banana;
	private String melon;
	public String getApple() {
		return apple;
	}
	public String getOrange() {
		return orange;
	}
	public String getGrape() {
		return grape;
	}
	public String getBanana() {
		return banana;
	}
	public String getMelon() {
		return melon;
	}

	public Fruit(FruitBuilder builder){
		apple = builder.apple;
		orange = builder.orange;
		grape = builder.grape;
		banana = builder.banana;
		melon = builder.melon;
	}
	
}


