package org.androidtown.event.keyevent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * BACK 키를 눌렀을 때 발생하는 키 이벤트를 처리하는 방법을 알 수 있는 액티비티
 * 
 * @author Mike
 *
 */
public class AnotherActivity extends Activity {

	/**
	 * 다른 액티비티를 띄우기 위해 정의한 요청 코드
	 */
	public static final int REQUEST_CODE_ANOTHER = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another);

        // 버튼을 눌렀을 때 메인 액티비티로 돌아갈 수 있도록 메소드 호출
        Button returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		close();
        	}
        });
   
    }

    /**
     * BACK 키를 눌렀을 때 이 액티비티를 닫고 돌아갈 수 있도록 메소드 호출
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK) {
        	close();
        	
			return true;
		}
		
        return false;
    }
    
    /**
     * 메인 액티비티로 돌아가도록 하는 메소드 정의
     */
    private void close() {
		// 결과값을 전달하기 위한 인텐트 객체 만들기
		Intent resultIntent = new Intent();        		
        resultIntent.putExtra("name", "mike");

        // 결과값 전달
        setResult(Activity.RESULT_OK, resultIntent);
        
        // 이 액티비티 없애기
        finish();
    }
    
}
