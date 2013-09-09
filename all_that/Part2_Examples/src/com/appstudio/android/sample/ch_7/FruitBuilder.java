package com.appstudio.android.sample.ch_7;

import com.appstudio.android.sample.ch_7.FruitBuilder;

public class FruitBuilder{
	String apple;
	String orange;
	String grape;
	String banana;
	String melon;
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
	public FruitBuilder setApple(String apple) {
		this.apple = apple;
		return this;
	}
	public FruitBuilder setOrange(String orange) {
		this.orange = orange;
		return this;
	}
	public FruitBuilder setGrape(String grape) {
		this.grape = grape;
		return this;
	}
	public FruitBuilder setBanana(String banana) {
		this.banana = banana;
		return this;
	}
	public FruitBuilder setMelon(String melon) {
		this.melon = melon;
		return this;
	}
	public Fruit build(){
		return new Fruit(this);
	}
}