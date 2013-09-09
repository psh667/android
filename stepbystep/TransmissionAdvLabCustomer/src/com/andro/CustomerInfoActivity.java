package com.andro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CustomerInfoActivity extends Activity implements OnClickListener {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        // 액티비티 생성 
        super.onCreate(savedInstanceState);

        // receive.xml의 레이아웃 출력
        setContentView(R.layout.receive);
        
        // 정보를 추출할 인텐트의 생성 
        Intent it    = getIntent();

        ///// 인텐트로부터 정보추출: 시작  
        String str_name = it.getStringExtra("it_name");
        String str_sex = it.getStringExtra("it_sex");
        String str_sms  = it.getStringExtra("it_sms");
        String str_interest  = it.getStringExtra("it_interest");
        String str_birthday  = it.getStringExtra("it_birthday");
        ///// 인텐트로부터 정보추출: 끝  

        ///// 추출정보 출력: 시작
        // 성명 출력 
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        tv_name.setText(str_name);

        // 성별 출력
        TextView tv_sex = (TextView)findViewById(R.id.tv_sex);
        tv_sex.setText(str_sex);

        // SMS 수신여부 
        TextView tv_receive = (TextView)findViewById(R.id.tv_receive);
        tv_receive.setText(str_sms);
        
        // 관심분야 
        TextView tv_interest = (TextView)findViewById(R.id.tv_interest);
        tv_interest.setText(str_interest);

        // 생일
        TextView tv_birthday = (TextView)findViewById(R.id.tv_birthday);
        tv_birthday.setText(str_birthday);        
        ///// 추출정보 출력: 끝
        
        // "이전" 버튼 클릭 대기  
        Button btn = (Button)findViewById(R.id.button_prev);
        btn.setOnClickListener(this);
    }
	
	public void onClick(View v) {
		// 호출 액티비티 지정 
		Intent it    = new Intent(this, CustomerRegFormActivity.class);

		// 호출할 액티비티의 실행 
		startActivity(it);

		// 현재 액티비티 종료 
		finish();
    }
}