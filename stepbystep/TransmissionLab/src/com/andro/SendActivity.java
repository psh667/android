package com.andro;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.view.View.OnClickListener;
import android.content.Intent;

public class SendActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ///// 버튼 클릭 대기: 시작
        // 버튼 ID 인식
        Button btn = (Button)findViewById(R.id.button_send);
        // 버튼 클릭 대기 
        btn.setOnClickListener(this);
        ///// 버튼 클릭 대기: 끝 
    }
        
    public void onClick(View v) {
        ///// 입력 정보 추출: 시작 
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
        CheckBox   chk_sms = (CheckBox)findViewById(R.id.checkbox_sms);
        String str_sms = "";
        if (chk_sms.isChecked()) {
        	str_sms = (String)chk_sms.getText();	
        }
        ///// 입력 정보 추출: 끝 

        // 호출할 클래스 지정 
        Intent it    = new Intent(this, ReceiveActivity.class);
    
        ///// 전송할 정보를 인텐트에 저장: 시작
        it.putExtra("it_name", str_name);
        it.putExtra("it_sex", str_sex);
        it.putExtra("it_sms",  str_sms);
        ///// 전송할 정보를 인텐트에 저장: 끝
  
        // 호출할 클래스를 액비티비로 실행 
        startActivity(it);

        // 현재 액티비티 종료
        finish();
    }
}