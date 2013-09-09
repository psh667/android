package com.andro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class CustomerModActivity extends Activity implements OnClickListener {
	
	private DBManager dbmgr;
	Spinner spinner;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_form);
 
        spinner = (Spinner) findViewById(R.id.spinner_interest);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(            
        	this, R.array.interest_array, android.R.layout.simple_spinner_item);    
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
        spinner.setAdapter(adapter); 
        
        // ID가 Button_Store인 버튼 인식 및 클릭 대기, OnClick()메소드 실행   
        Button btn_previous = (Button)findViewById(R.id.button_previous);
        btn_previous.setOnClickListener(this);
        
        // ID가 Button_Store인 버튼 인식 및 클릭 대기, OnClick()메소드 실행   
        Button btn_store = (Button)findViewById(R.id.button_store);
        btn_store.setOnClickListener(this);
        
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
	        String sql = "select name, sex, sms, interest " + 
	                     "  from customers " +
	        		     " where name = '" + str_name + "' ";
	        Cursor cursor = sdb.rawQuery(sql, null);
	        
	        // cursor 객체로 할당된 members 테이블 데이터를 한 행씩 이동하면서 출력함
	       if (cursor.moveToNext()) {
	        	// 행의 첫 번째 열(0), ..., 네 번째 열(3)을 각각 추출함  
	        	String name     = cursor.getString(0);
	        	String sex      = cursor.getString(1);
	        	String sms      = cursor.getString(2);
	        	String interest = cursor.getString(3);
	        	
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
    			
    			// 고객의 관심부야와 일치하는 스피너 아이템 선택
    			String spinner_item_name;
    			for (int i=0; i<spinner.getCount(); i++) {
    				// i번째 스피너 아이템 값
    				spinner_item_name = (String)spinner.getItemAtPosition(i);
    				// 고객의 관심부야와 i번째 스피너 아이템 값이 일치하면 i번째 스피너 아이템을 선택
    			    if (interest.equals(spinner_item_name)) 
    				    spinner.setSelection(i);
    			}
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
    	
    	if (v.getId() == R.id.button_store) {

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
			
			// 관심분야 추출 
			String str_interest = spinner.getSelectedItem().toString();
			
			
	        try {
	        	// DB객체 생성(DB가 존재하지 않으면 생성함)
	            dbmgr = new DBManager(this);
	            
	            SQLiteDatabase sdb;
	            
	            // DB연결
	            sdb = dbmgr.getWritableDatabase();
	            // members 테이블에 추출정보 추가
	            String sql = "update customers " +
	                         "   set sex  = '" + str_sex  + "', " +
	                         "       sms  = '" + str_sms  + "', " +
	            		     "       interest = '" + str_interest + "' " +
	                         " where name = '" + str_name + "' ";
	            sdb.execSQL(sql);
	            // DB닫음
	            dbmgr.close();
	        } catch (SQLiteException e) {
	        	// 예외처리(생략)
	        } // try   	
        
    	}
    	
    	// 현재 클래스(This)에서 호출할 클래스(QueryActivity.class) 지정 
		Intent it    = new Intent(this, TabViewActivity.class);
		// 이텐트에서 지정한 액티비티 실행  
		startActivity(it);
		// 현재 액티비티 종료
		finish();
    }    
}