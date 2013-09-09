package com.andro;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EventLabActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ///// 버튼 클릭 대기: 시작
        // ID가 button_send(main.xml)인 버튼의 초기화 
        Button btn = (Button)findViewById(R.id.button_send);
        // 버튼 클릭 대기(클릭 시 onClick() 메소드가 호출됨) 
        btn.setOnClickListener(this);
        ///// 버튼 클릭 대기 띄움: 끝
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

        // 알림창 띄움 
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("알림창");
        alert.setMessage("성명: " + str_name + "\n성별: " + str_sex + "\n수신여부: " + str_sms);
        alert.setIcon(R.drawable.ic_launcher);
        alert.setPositiveButton("확인", null);
        alert.show();
        ///// 전송 버튼 클릭 시, 사용자가 입력한 정보를 알림창에 띄움: 끝 
    }    
}