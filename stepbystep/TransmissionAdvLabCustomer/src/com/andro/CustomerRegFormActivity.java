package com.andro;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class CustomerRegFormActivity extends Activity implements OnClickListener {
	private TextView mDateDisplay;    
	private int mYear;    
	private int mMonth;    
	private int mDay;    
	static final int DATE_DIALOG_ID = 0;	
	Spinner spinner;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ///// 스피너 출력: 시작 
        // (1) strings.xml에 정의한 string 배열(interest_array)를 ArrayAdapter로  바인딩하고 스피너의 모양을 지정함 
        ArrayAdapter<CharSequence> adapter = 
        	ArrayAdapter.createFromResource(this, R.array.interest_array, android.R.layout.simple_spinner_item);  
        // (2) ArrayAdapter객체에 할당된 데이터들을 스피너가 클릭될 때 보여줄 스피너 아이템의 출력형식을 지정함  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // (3) main.xml의 스피너 id 인식
        spinner = (Spinner) findViewById(R.id.spinner_interest);
        // (4) 스피너에 ArrayAdapter를 연결함 
        spinner.setAdapter(adapter);        
        ///// 스피너 출력: 끝 
        
        //// Date Picker: 시작  ////////////
        // (1) main.xml의 레이아웃에 배치된 날짜 입력을 위한 TextView 인식          
        mDateDisplay = (TextView) findViewById(R.id.edit_birthday);        
        // (2) 인식된 TextView에 click listener 추가
        mDateDisplay.setOnClickListener(new View.OnClickListener() {  
        	// (5) 클릭되면 실행  
        	public void onClick(View v) {   
        		// (6) 날짜 설정을 위한 다이어로그 출력
        		showDialog(DATE_DIALOG_ID);            
        	}        
        });        
        // (3) 현재 날짜 인식         
        final Calendar c = Calendar.getInstance();        
        mYear = c.get(Calendar.YEAR);        
        mMonth = c.get(Calendar.MONTH);        
        mDay = c.get(Calendar.DAY_OF_MONTH);        
        // (4) 인식된 날짜를  출력        
        updateDisplay();        
        //// Date Picker: 끝  ////////////
        
        ///// 버튼 클릭 대기: 시작
        // ID가 button_send(main.xml)인 버튼의 초기화 
        Button btn = (Button)findViewById(R.id.button_send);
        // 버튼 클릭 대기(클릭 시 onClick() 메소드가 호출됨) 
        btn.setOnClickListener(this);
        ///// 버튼 클릭 대기 띄움: 끝
        
    }
    
    // (7) 다이어로그 출력시 DatePicker 다이어로그 출력 
    @Override
    protected Dialog onCreateDialog(int id) {    
    	switch (id) {    
	    	case DATE_DIALOG_ID:        
	    		return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);    
    	}    
    	return null;
   	}    
    
    // (8) 다이어로그에 있는 날짜를 설정(set)하면 실행됨
    private DatePickerDialog.OnDateSetListener mDateSetListener =            
    	new DatePickerDialog.OnDateSetListener() {                
    	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {                    
    		    mYear = year;                    
    		    mMonth = monthOfYear;                    
    		    mDay = dayOfMonth;
    		    // 사용자가 지정한 날짜를 출력 
    		    updateDisplay();                
    		}            
    	};    
    
    // 설정된 날짜를 TextView에 출력     
    private void updateDisplay() {   
    	//main.xml의 레이아웃에 배치된 날짜 입력 TextView에 인식된 날짜 출력     
    	mDateDisplay.setText(            
    		new StringBuilder()                    
    		// 월은 시스템에서 0~11로 인식하기 때문에 1을 더해줌
    		.append(mYear).append("-")
    		.append(mMonth + 1).append("-")                    
    		.append(mDay).append(" "));
    }   
    
    
	// 버튼 클릭 시 실행 
	// @Override
    public void onClick(View v) {
    
	    ///// 입력 정보 추출: 시작 
        // 성명 추출
        EditText et_name = (EditText)findViewById(R.id.edit_name);
        String str_name = et_name.getText().toString();

        // 성별 추출        
        RadioGroup rg_sex = (RadioGroup)findViewById(R.id.radiogroup_sex);
        RadioButton rb_male = (RadioButton)findViewById(R.id.radio_male);
        RadioButton rb_female = (RadioButton)findViewById(R.id.radio_female);
        String str_sex = "";
        if (rg_sex.getCheckedRadioButtonId() == R.id.radio_male) {
            str_sex = rb_male.getText().toString();	
        }
        if (rg_sex.getCheckedRadioButtonId() == R.id.radio_female) {
            str_sex = rb_female.getText().toString();	
        }

        // 수신여부 추출 
        CheckBox   chk_sms = (CheckBox)findViewById(R.id.checkbox_sms);
        String str_sms = "";
        if (chk_sms.isChecked()) {
            str_sms = (String)chk_sms.getText();	
        }
        
        // 관심분야 추출 
        String str_interest = spinner.getSelectedItem().toString();
        
        // 생일 추출
        EditText et_birthday = (EditText)findViewById(R.id.edit_birthday);
        String str_birthday = et_birthday.getText().toString();
        ///// 입력 정보 추출: 끝 

        // 호출할 클래스 지정 
        Intent it    = new Intent(this, CustomerInfoActivity.class);
    
        ///// 전송할 정보를 인텐트에 저장: 시작
        it.putExtra("it_name", str_name);
        it.putExtra("it_sex", str_sex);
        it.putExtra("it_sms",  str_sms);
        it.putExtra("it_interest",  str_interest);
        it.putExtra("it_birthday",  str_birthday);
        ///// 전송할 정보를 인텐트에 저장: 끝
  
        // 호출할 클래스를 액비티비로 실행 
        startActivity(it);

        // 현재 액티비티 종료
        finish();
    }        
}