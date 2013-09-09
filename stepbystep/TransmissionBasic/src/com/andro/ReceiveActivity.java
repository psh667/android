package com.andro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive);
        
        // 정보를 추출할 인텐트의 생성 
        Intent it    = getIntent();

        ///// 인텐트로부터 정보추출: 시작 ///// 
        String str_name = it.getStringExtra("it_name");
        String str_sex = it.getStringExtra("it_sex");
        String str_sms  = it.getStringExtra("it_sms");
        ///// 인텐트로부터 정보추출: 끝 ///// 

        ///// 추출정보 출력: 시작 /////
        // 성명 출력 
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        tv_name.setText(str_name);

        //  성별 출력
        TextView tv_sex = (TextView)findViewById(R.id.tv_sex);
        tv_sex.setText(str_sex);

        // SMS 수신여부 출력
        TextView tv_receive = (TextView)findViewById(R.id.tv_receive);
        tv_receive.setText(str_sms);
        ///// 추출정보 출력: 끝 /////
    }
}