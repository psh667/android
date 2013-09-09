package com.andro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CustRegActivity extends Activity {    
	public void onCreate(Bundle savedInstanceState) {        
		super.onCreate(savedInstanceState); 
		
		// 현재 객체에 TextView 객체 생성 
		TextView textview = new TextView(this);    
		// 출력할 문자 설정 
		textview.setText("고객등록 화면");
		// 현재 객체에 TextView 객체에서 설정한 문자 출력  
		setContentView(textview);    
	}
}