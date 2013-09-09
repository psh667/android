package com.andro;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

public class CustomerDelActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 정보를 추출할 인텐트의 생성 
        Intent it = getIntent();

        ///// 인텐트로부터 정보추출: 시작  
        // 인텐트로부터 send_name의 값을 추출함 
        String str_name = it.getStringExtra("it_name");
        
        String sql = "";
        
        try {
        	// DB객체 생성(DB가 존재하지 않으면 생성함)
        	DBManager dbmgr = new DBManager(this);
        
            SQLiteDatabase sdb;
            
            // DB연결
            sdb = dbmgr.getWritableDatabase();
            // members 테이블에 추출정보 추가
            sql = "delete from customers " +
                  " where name = '" + str_name + "' ";  
            sdb.execSQL(sql);
            // DB닫음
            dbmgr.close();
        } catch (SQLiteException e) {
        	// 예외처리(생략)
        }    	
    	
    	// 현재 클래스(This)에서 호출할 클래스(QueryActivity.class) 지정 
		Intent it2    = new Intent(this, TabViewActivity.class);
		// 이텐트에서 지정한 액티비티 실행  
		startActivity(it2);
		// 현재 액티비티 종료
		finish();
    }
}