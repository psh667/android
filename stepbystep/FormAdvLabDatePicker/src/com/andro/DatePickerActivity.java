package com.andro;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerActivity extends Activity {
	private TextView mDateDisplay;    
	private int mYear;    
	private int mMonth;    
	private int mDay;    
	static final int DATE_DIALOG_ID = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
}