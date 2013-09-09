package com.andro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

public class TextAdvLabActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 액티비티 라벨 출력
        setTitle("고객목록");

        // main.xml에 정의된 LinearLayout 인식(ID: customers)
        LinearLayout layout = (LinearLayout)findViewById(R.id.customers);
        //  LinearLayout의 배경색(흰색)
        layout.setBackgroundColor(Color.argb(255, 255, 255, 255));
        
        // 성명과 기타정보를 출력할  TextView 객체 선언 
        TextView tv_name;
        TextView tv_etc;
        
        ///// 고객 1의 정보: 시작 /////
        // 고객 1의 성명을 LinearLayout 내에 추가
        tv_name = new TextView(this); // TextView 객체 생성
        tv_name.append("홍길동"); // 문자 출력 
        tv_name.setTextSize(20); // 문자 크기
        tv_name.setTextColor(Color.argb(255, 255, 255, 0)); // 문자 색
        tv_name.setBackgroundColor(Color.argb(100, 0, 0, 255)); // 문자 배경색
        layout.addView(tv_name); // TextView 객체를 LinearLayout 객체에 추가        
        // 고객 1의 기타정보를 LinearLayout 내에 추가
        tv_etc = new TextView(this);
        tv_etc.append("서울\n");
        tv_etc.append("02-555-1234");
        tv_etc.setTextColor(Color.argb(255, 0, 0, 0));
        layout.addView(tv_etc); 
        ///// 고객 1의 정보: 끝 /////
        
        ///// 고객 2의 정보: 시작 /////
        // 고객 2의 성명을 LinearLayout 내에 추가
        tv_name = new TextView(this);
        tv_name.append("심청");
        tv_name.setTextSize(20);
        tv_name.setTextColor(Color.argb(255, 255, 255, 0));
        tv_name.setBackgroundColor(Color.argb(100, 0, 0, 255));
        layout.addView(tv_name);
        // 고객 2의 기타정보를 LinearLayout 내에 추가
        tv_etc = new TextView(this);
        tv_etc.append("강원도\n");
        tv_etc.append("033-777-1234");
        tv_etc.setTextColor(Color.argb(255, 0, 0, 0));
        layout.addView(tv_etc);           
        ///// 고객 2의 정보: 끝 /////
    }
}