package com.andro;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EventBasicActivity extends Activity implements OnClickListener {
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
    
        ///// 알림창을 띄움: 시작 /////
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("알림창");
        alert.setMessage("전송 버튼을 누르셨네요!");
        alert.setIcon(R.drawable.ic_launcher);
        alert.setPositiveButton("확인", null);
        alert.show();
    	///// 알림창을 띄움: 끝 /////
    }    
}