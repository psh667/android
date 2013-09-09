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
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerListActivity extends Activity implements OnClickListener {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   
        setContentView(R.layout.query);
        LinearLayout layout = (LinearLayout) findViewById(R.id.customers);
        
        try {
        	// DBManager 객체 생성(DB 존재 않으면 생성)
        	DBManager dbmgr = new DBManager(this);
	        
        	// DB 연결
	        SQLiteDatabase sdb = dbmgr.getReadableDatabase();
	        // SQL문 실행 결과를 cursor 객체로 받음 
	        String sql = "select name, sex, sms, interest " + 
	                     "  from customers ";
	        Cursor cursor = sdb.rawQuery(sql, null);
	        
	        int i=0;
	        
	        // cursor 객체로 할당된 members 테이블 데이터를 한 행씩 이동하면서 출력함
	        while(cursor.moveToNext()) {
	        	// 행의 첫 번째 열(0), ..., 네 번째 열(3)을 각각 추출함  
	        	String name     = cursor.getString(0);
	        	String sex      = cursor.getString(1);
	        	String sms      = cursor.getString(2);
	        	String interest = cursor.getString(3);
	        	
	            TextView tv_list = new TextView(this);

	        	// TextView로 데이터를 추가하면서 출력함
	        	tv_list.append(name);
	        	tv_list.setTextSize(20);
	        	tv_list.setTextColor(Color.rgb(255, 255, 0));
	        	tv_list.setBackgroundColor(Color.rgb(0, 0, 255));
	            layout.addView(tv_list);
	            
	            tv_list.setId(i);
	            tv_list.setOnClickListener(this);
	            tv_list.setTag(name);
	        	  
	            TextView tv_list2 = new TextView(this);

	        	// TextView로 데이터를 추가하면서 출력함
	        	tv_list2.append(sex  + "\n");
	        	tv_list2.append(sms  + "\n");
	        	tv_list2.append(interest);
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
    }

    // '등록' 버튼이 클릭되었을 때
    public void onClick(View v) {
    	Intent it = new Intent(); 
    	// 현재 클래스(this)에서 호출할 클래스(JoinLabActivity.class) 지정 
		it    = new Intent(this, CustomerDetailActivity.class);
        // 입력한 성명의 값을 저장 
        it.putExtra("it_name", (String)v.getTag());
		
		// 인텐트에서 지정한 액티비티 실행 
		startActivity(it);
		// 현재 엑티비티 종료 
		finish();
    }    
}