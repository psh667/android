package com.appstudio.android.sample.ch_7;

import com.appstudio.android.sample.ch_7.Fruit;
import com.appstudio.android.sample.ch_7.FruitBuilder;

public class FruitBuilderTest {
	public static void main(String[] args){
		//빌터 패턴 이용하지 않을 경우
		FruitNonBuilder oldFruit = new FruitNonBuilder(
				"apple",
				"orange",
				"grape",
				"banana",
				"melon"
				);
		
		
		//빌더 패턴 이용할 경우
		FruitBuilder builder = 
				new FruitBuilder().setApple("apple").setOrange("orange").setBanana("banana").setMelon("melon");
		Fruit newFruit = builder.build();
	}
}
