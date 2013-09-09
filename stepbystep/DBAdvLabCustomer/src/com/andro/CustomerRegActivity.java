package com.andro;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class CustomerRegActivity extends Activity implements OnClickListener {
	
	private DBManager dbmgr;
	Spinner spinner;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_form);
 
        spinner = (Spinner) findViewById(R.id.spinner_interest);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(            
        	this, R.array.interest_array, android.R.layout.simple_spinner_item);    
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
        spinner.setAdapter(adapter); 
        
     // ID가 Button_Store인 버튼 인식 및 클릭 대기, OnClick()메소드 실행   
        Button btn = (Button)findViewById(R.id.button_store);
        btn.setOnClickListener(this);
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
		
		// 관심분야 추출 
		String str_interest = spinner.getSelectedItem().toString();
		
        try {
        	// DB객체 생성(DB가 존재하지 않으면 생성함)
            dbmgr = new DBManager(this);
            
            SQLiteDatabase sdb;
            
            // DB연결
            sdb = dbmgr.getWritableDatabase();
            // members 테이블에 추출정보 추가
            sdb.execSQL("insert into customers values('" + str_name + "', '" + str_sex + "', '" + str_sms + "', '" + str_interest + "');");
            // DB닫음
            dbmgr.close();
        } catch (SQLiteException e) {
        	// 예외처리(생략)
        }    	
    	
    	// 현재 클래스(This)에서 호출할 클래스(QueryActivity.class) 지정 
		Intent it    = new Intent(this, TabViewActivity.class);
		// 이텐트에서 지정한 액티비티 실행  
		startActivity(it);
		// 현재 액티비티 종료
		finish();
    }    
}