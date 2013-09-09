package com.andro;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.view.View.OnClickListener;

public class EventAdvLabActivity extends Activity implements OnClickListener {
	// 스피너 객체 선언
	Spinner spinner;

	private TextView mDateDisplay;    
	private Button mPickDate;    
	private int mYear;    
	private int mMonth;    
	private int mDay;    
	static final int DATE_DIALOG_ID = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 스피너 출력: 시작 
        spinner = (Spinner) findViewById(R.id.spinner_interest);
        ArrayAdapter<CharSequence> adapter = 
        	ArrayAdapter.createFromResource(            
        	this, R.array.interest_array, android.R.layout.simple_spinner_item);    
        adapter.setDropDownViewResource(
        	android.R.layout.simple_spinner_dropdown_item);    
        spinner.setAdapter(adapter); 
        // 스피너 출력: 끝  
        
        //// Date Picker: 시작  ////////////
        // capture our View elements        
        mDateDisplay = (TextView) findViewById(R.id.edit_birthday);        
        mPickDate = (Button) findViewById(R.id.pickDate);        
        // add a click listener to the button        
        mPickDate.setOnClickListener(new View.OnClickListener() {            
        	public void onClick(View v) {                
        		showDialog(DATE_DIALOG_ID);            
        	}        
        });        
        // get the current date        
        final Calendar c = Calendar.getInstance();        
        mYear = c.get(Calendar.YEAR);        
        mMonth = c.get(Calendar.MONTH);        
        mDay = c.get(Calendar.DAY_OF_MONTH);        
        // display the current date (this method is below)        
        updateDisplay();        
        //// Date Picker: 끝  ////////////
        
        ///// 버튼 클릭 대기: 시작
        // ID가 button_send(main.xml)인 버튼의 초기화 
        Button btn = (Button)findViewById(R.id.button_send);
        // 버튼 클릭 대기(클릭 시 onClick() 메소드가 호출됨) 
        btn.setOnClickListener(this);
        ///// 버튼 클릭 대기 띄움: 끝
    }
	     
    // updates the date in the TextView    
    private void updateDisplay() {        
    	mDateDisplay.setText(            
    			new StringBuilder()                    
    			// Month is 0 based so add 1   
    			.append(mYear).append("-")
    			.append(mMonth + 1).append("-")                    
    			.append(mDay).append(" "));
    }   
    
    // the callback received when the user "sets" the date in the dialog    
    private DatePickerDialog.OnDateSetListener mDateSetListener =            
    		new DatePickerDialog.OnDateSetListener() {                
    	public void onDateSet(DatePicker view, int year,
    			int monthOfYear, int dayOfMonth) {                    
    		mYear = year;                    
    		mMonth = monthOfYear;                    
    		mDay = dayOfMonth;                    
    		updateDisplay();                
    		}            
    	};    
    
    @Override
    protected Dialog onCreateDialog(int id) {    
    	switch (id) {    
	    	case DATE_DIALOG_ID:        
	    		return new DatePickerDialog(this,                    
	    				mDateSetListener,                    
	    				mYear, mMonth, mDay);    
    	}    
    	return null;
   	}    
    
	// 버튼 클릭 시 실행 
	// @Override
    public void onClick(View v) {
    
	    ///// 전송 버튼 클릭 시, 사용자가 입력한 정보를 알림창에 띄움: 시작 
        // 성명 추출
        EditText et_name = (EditText)findViewById(R.id.edit_name);
        String str_name = et_name.getText().toString();

        // 성별 추출        
        RadioGroup rg_sex = (RadioGroup)findViewById(R.id.radiogroup_sex);
        RadioButton rb_male = (RadioButton)findViewById(R.id.radio_male);
        RadioButton rb_female = (RadioButton)findViewById(R.id.radio_female);
        String str_sex = "";
        if (rg_sex.getCheckedRadioButtonId() == R.id.radio_male) {
            // str_sex = "남";	
            str_sex = rb_male.getText().toString();	
        }
        if (rg_sex.getCheckedRadioButtonId() == R.id.radio_female) {
            // str_sex = "여";	
            str_sex = rb_female.getText().toString();	
        }

        // 수신여부 추출 
        CheckBox   chk_sms = (CheckBox)findViewById(R.id.checkbox_sms);
        String str_sms = "";
        if (chk_sms.isChecked()) {
            str_sms = (String)chk_sms.getText();	
        }

        // 관심분야 추출 
        String spn_interest = spinner.getSelectedItem().toString();
        
        // 생일 추출
        EditText et_birthday = (EditText)findViewById(R.id.edit_birthday);
        String str_birthday = et_birthday.getText().toString();
        
        // 알림창 띄움 
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("알림창");
        alert.setMessage("성명: " + str_name + "\n성별: " + str_sex + "\n수신여부: " + str_sms + "\n관심분야: " + spn_interest + "\n생일: " + str_birthday);
        alert.setIcon(R.drawable.ic_launcher);
        alert.setPositiveButton("확인", null);
        alert.show();
        ///// 전송 버튼 클릭 시, 사용자가 입력한 정보를 알림창에 띄움: 끝 
    }    
}