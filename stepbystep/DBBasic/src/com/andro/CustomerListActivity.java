package com.andro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerListActivity extends Activity implements OnClickListener {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);
        
        // 액티비티 라벨 출력 
        setTitle("고객목록");
        
        // 고객정보를 추가할 레이아웃을 인식함  
        LinearLayout layout = (LinearLayout) findViewById(R.id.customers);
        
        int i=0;
        
        try {
        	// DBManager 객체 생성(DB 존재 않으면 생성)
        	DBManager dbmgr = new DBManager(this);
	        
        	// DB 연결
	        SQLiteDatabase sdb = dbmgr.getReadableDatabase();
	        // SQL문 실행 결과를 cursor 객체로 받음 
	        Cursor cursor = sdb.rawQuery("select name, sex, sms from customers", null);
	        
	        // cursor 객체로 할당된 customers 테이블 데이터를 한 행씩 이동하면서 출력함
	        while(cursor.moveToNext()) {
	        	// 행의 첫 번째 열(0), 두 번째 열(1), 세 번째 열(2)을 각각 추출함  
	        	String name     = cursor.getString(0);
	        	String sex      = cursor.getString(1);
	        	String sms      = cursor.getString(2);
	        	
	        	// 레이아웃에 추가할 고객정보(성명)를 위한 텍스트뷰 생성
	            TextView tv_list = new TextView(this);

	        	// 텍스트뷰에 고객성명 할당
	        	tv_list.append(name);
	        	tv_list.setTextSize(20);
	        	tv_list.setTextColor(Color.rgb(255, 255, 0));
	        	tv_list.setBackgroundColor(Color.rgb(0, 0, 255));
	        	// 고객성명을 레이아웃에 추가하여 출력앗
	            layout.addView(tv_list);
	        	  
	        	// 레이아웃에 추가할 고객정보(성별, 수신여부)를 위한 텍스트뷰 생성
	            TextView tv_list2 = new TextView(this);

	        	// 텍스트뷰에 고객의 성별과 수신여부 할당
	        	tv_list2.append(sex  + "\n");
	        	tv_list2.append(sms  + "\n");
	        	// 고객성명을 레이아웃에 추가하여 출력앗
	            layout.addView(tv_list2);
	            
	        	i++;
	        }
	        
	        // 등록된 고객이 없는 경우의 설명
	        if (i == 0) {
	            TextView tv_desc = new TextView(this);
	            tv_desc.append("등록된 고객이 없습니다!");
	            layout.addView(tv_desc);	        	
	        }
	        	
	        // cursor 객체 닫음
	        cursor.close();
	        // dbmgr 객체 닫음
	        dbmgr.close();
        
        } catch (SQLiteException e) {
        	// DB 접속 또는 조회 시 에러 발생할 때 
            TextView tv_err = new TextView(this);
            // tv_desc.append("등록된 고객이 없습니다!");
            tv_err.append(e.getMessage());
            layout.addView(tv_err);	        	
        }
        
        // ID가 button_join_form(main.xml)인 버튼 초기화
        Button btn = (Button)findViewById(R.id.button_join_form);
        // '등록' 버튼 클릭 대기
        btn.setOnClickListener(this);
    }

    // '등록' 버튼이 클릭되었을 때
    public void onClick(View v) {
    	// 현재 클래스(this)에서 호출할 클래스(JoinLabActivity.class) 지정 
		Intent it    = new Intent(this, CustomerRegActivity.class);
		// 인텐트에서 지정한 액티비티 실행 
		startActivity(it);
		// 현재 엑티비티 종료 
		finish();
    }    
}