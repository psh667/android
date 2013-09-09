package com.andro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class CustomerModActivity extends Activity implements OnClickListener {
	
	private DBManager dbmgr;
	Spinner spinner;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_form);
 
        // ID가 Button_Store인 버튼 인식 및 클릭 대기, OnClick()메소드 실행   
        Button btn = (Button)findViewById(R.id.button_store);
        btn.setOnClickListener(this);
        
        // 등록된 회원정보 출력: 시작  ////////////////////////////
        
        // 정보를 추출할 인텐트의 생성 
        Intent it    = getIntent();

        ///// 인텐트로부터 정보추출: 시작  
        // 인텐트로부터 send_name의 값을 추출함 
        String str_name = it.getStringExtra("it_name");
        
        try {
        	// DBManager 객체 생성(DB 존재 않으면 생성)
        	DBManager dbmgr = new DBManager(this);
	        
        	// DB 연결
	        SQLiteDatabase sdb = dbmgr.getReadableDatabase();
	        // SQL문 실행 결과를 cursor 객체로 받음 
	        String sql = "select name, sex, sms " + 
	                     "  from customers" +
	        		     " where name = '" + str_name + "' ";
	        Cursor cursor = sdb.rawQuery(sql, null);
	        
	        // cursor 객체로 할당된 members 테이블 데이터를 한 행씩 이동하면서 출력함
	       if (cursor.moveToNext()) {
	        	// 행의 첫 번째 열(0), ..., 네 번째 열(3)을 각각 추출함  
	        	String name     = cursor.getString(0);
	        	String sex      = cursor.getString(1);
	        	String sms      = cursor.getString(2);
	        	
	           	// 성명 추출
	        	EditText et_name = (EditText)findViewById(R.id.edit_name);
	    		et_name.setText(name);
	    		
	    		RadioButton rb_sex = null; 
	    		if (sex.equals("남")) 
	    			rb_sex = (RadioButton)findViewById(R.id.radio_male);
	    		else if (sex.equals("여")) 
	    			rb_sex = (RadioButton)findViewById(R.id.radio_female);
    			rb_sex.setChecked(true);
    			
    			// 수신여부 추출 
    			CheckBox   chk_sms = (CheckBox)findViewById(R.id.check_sms);
    			if (sms.equals("SMS")) 
    				chk_sms.setChecked(true);	
	        }
	        	
	        // cursor 객체 닫음
	        cursor.close();
	        // dbmgr 객체 닫음
	        dbmgr.close();
        
        } catch (SQLiteException e) {
        	// DB 접속 또는 조회 시 에러 발생할 때 
        }
                
        // 등록된 회원정보 출력: 끝  ////////////////////////////
    }
    
    public void onClick(View v) {

       	// 성명 추출
    	EditText et_name = (EditText)findViewById(R.id.edit_name);
		String str_name = et_name.getText().toString();
		
		// 성별 추출
		RadioGroup rg_sex = (RadioGroup)findViewById(R.id.radiogroup_sex);
		String str_sex = "";
		if (rg_sex.getCheckedRadioButtonId() == R.id.radio_male) {
		    str_sex = "남";	
		}
		if (rg_sex.getCheckedRadioButtonId() == R.id.radio_female) {
		    str_sex = "여";	
		}
		
		// 수신여부 추출 
		CheckBox   chk_sms = (CheckBox)findViewById(R.id.check_sms);
		String str_sms = "";
		if (chk_sms.isChecked()) {
		    str_sms = (String)chk_sms.getText();	
		}
		
        try {
        	// DB객체 생성(DB가 존재하지 않으면 생성함)
            dbmgr = new DBManager(this);
            
            SQLiteDatabase sdb;
            
            // DB연결
            sdb = dbmgr.getWritableDatabase();
            // members 테이블에 추출정보 추가
            String sql = "update customers " +
                         "   set sex  = '" + str_sex  + "', " +
                         "       sms  = '" + str_sms  + "'  " +
                         " where name = '" + str_name + "' ";
            sdb.execSQL(sql);
            // DB닫음
            dbmgr.close();
        } catch (SQLiteException e) {
        	// 예외처리(생략) 
        }    	
    	
    	// 현재 클래스(This)에서 호출할 클래스(QueryActivity.class) 지정 
		Intent it    = new Intent(this, CustomerDetailActivity.class);
        // 입력한 성명의 값을 저장 
        it.putExtra("it_name", str_name);
		// 이텐트에서 지정한 액티비티 실행  
		startActivity(it);
		// 현재 액티비티 종료
		finish();
    }    
}