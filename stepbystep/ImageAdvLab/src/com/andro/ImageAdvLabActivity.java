package com.andro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

public class ImageAdvLabActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // main.xml에 있는 LinearLayout 인식
        LinearLayout layout = (LinearLayout)findViewById(R.id.pictures);
        // 레이아웃의 가로, 세로 크기 설정  
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 200);

        // TextView 객체명 지정 
        TextView tv_name;
        TextView tv_desc;
        // ImageView 객체명 지정
        ImageView iv;
        
        ///// 이미지1에 대한 그림과 설명: 시작 ///// 
        // tv_name 객체 초기화 
        tv_name = new TextView(this);
        // 문자 추가 
        tv_name.append("컵");
        // 문자 크기 
        tv_name.setTextSize(20);
        // 문자색
        tv_name.setTextColor(Color.rgb(255, 255, 0));
        // 배경색 
        tv_name.setBackgroundColor(Color.rgb(0, 0, 255));
        // tv_name 객체를 layout 객체에 추가 
        layout.addView(tv_name);
        
        // tv_desc 객체 초기화 
        tv_desc = new TextView(this);
        // 문자 추가 
        tv_desc.append("책상 위의 컵과 노트");
        // tv_desc 객체를 layout 객체에 추가 
        layout.addView(tv_desc);  
        
        // iv 객체 초기화
        iv = new ImageView(this);
        // 이미지 소스 지정
        iv.setBackgroundResource(R.drawable.img1);
        // 이미지 크기 
        iv.setLayoutParams(params);
        // iv 객체를 layout 객체에 추가
        layout.addView(iv);
        ///// 이미지1에 대한 그림과 설명: 끝 ///// 
        
        ///// 이미지2에 대한 그림과 설명: 시작 ///// 
        // tv_name 객체 초기화 
        tv_name = new TextView(this);
        // 문자 추가 
        tv_name.append("과일");
        // 문자 크기 
        tv_name.setTextSize(20);
        // 문자색
        tv_name.setTextColor(Color.rgb(255, 255, 0));
        // 배경색 
        tv_name.setBackgroundColor(Color.rgb(0, 0, 255));
        // tv_name 객체를 layout 객체에 추가 
        layout.addView(tv_name);
        
        // tv_desc 객체 초기화 
        tv_desc = new TextView(this);
        // 문자 추가 
        tv_desc.append("쟁반 안의 탐스런 과일");
        // tv_desc 객체를 layout 객체에 추가 
        layout.addView(tv_desc);  
        
        // iv 객체 초기화
        iv = new ImageView(this);
        // 이미지 소스 지정
        iv.setBackgroundResource(R.drawable.img2);
        // 이미지 크기 
        iv.setLayoutParams(params);
        // iv 객체를 layout 객체에 추가
        layout.addView(iv);
        ///// 이미지2에 대한 그림과 설명: 끝 ///// 
    }
}