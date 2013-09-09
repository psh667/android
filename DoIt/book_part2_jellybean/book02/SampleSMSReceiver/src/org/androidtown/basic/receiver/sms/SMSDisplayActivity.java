package org.androidtown.basic.receiver.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * SMS를 수신했을 때 화면에 보여지는 액티비티입니다.
 * 
 * @author Mike
 */
public class SMSDisplayActivity extends Activity {

	Button titleButton;
	Button closeButton;
	TextView messageTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 타이틀 영역을 안보이게 합니다.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.sms_display);

        // 레이아웃의 객체들을 참조합니다.
        titleButton = (Button) findViewById(R.id.titleButton);
        closeButton = (Button) findViewById(R.id.closeButton);
        messageTextView = (TextView) findViewById(R.id.messageTextView);

        // 닫기 버튼을 누르면 화면을 닫습니다.
        closeButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
 
   	 	// 전달된 인텐트를 처리합니다.
   	 	Intent passedIntent = getIntent();
   	 	if (passedIntent != null) {
   	 		processIntent(passedIntent);
   	 	}
    }

    /**
     * 이 액티비티가 SINGLE_TOP 플래그로 되어 있으면 이 메소드로 인텐트가 전달됩니다.
     */
	protected void onNewIntent(Intent intent) {
		// 전달된 인텐트를 처리합니다.
		processIntent(intent);

		super.onNewIntent(intent);
	}

	/**
	 * 전달된 인텐트를 처리합니다.
	 * 
	 * @param intent
	 */
    private void processIntent(Intent intent) {
    	// 발신 번호
    	String number = intent.getStringExtra("number");
    	// 수신 내용
    	String message = intent.getStringExtra("message");
    	
    	// 시간
    	String timestamp = intent.getStringExtra("timestamp");

    	if (number != null) {
	    	titleButton.setText(number + " 에서 문자 수신");
	    	messageTextView.setText("[" + timestamp + "] " + message);

	    	messageTextView.invalidate();
    	}
    }

}