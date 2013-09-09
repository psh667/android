package com.androidbook;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class Alerts
{
	public static void showAlert(String message, Context ctx) 
	{
		// 뷰를 로딩함
		LayoutInflater li = LayoutInflater.from(ctx); 
	    View view = li.inflate(R.layout.alert_layout, null); 
	
	    // 빌더를 생성하고 뷰 지정
	    AlertDialog.Builder builder = new AlertDialog.Builder(ctx); 
	    builder.setTitle("경고창"); 
	    builder.setView(view); 
	
	    // 버튼과 리스너 추가
	    EmptyListener pl = new EmptyListener(); 
		builder.setPositiveButton("OK", pl);
	
	    // 대화창 생성
	    AlertDialog ad = builder.create(); 
	
	    // 생성된 대화창을 표시
	    ad.show();
	}
}
