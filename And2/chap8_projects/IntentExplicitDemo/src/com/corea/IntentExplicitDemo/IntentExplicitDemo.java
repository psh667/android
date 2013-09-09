package com.corea.IntentExplicitDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class IntentExplicitDemo extends Activity implements OnClickListener{
    
    static final int[] BUTTONS = {
        R.id.start_activity,
        R.id.start_activity_for_result
    };
	 
    private static final String TAG = "IntentActivity";
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        for(int buttonId: BUTTONS) {
            Button button = (Button)findViewById(buttonId);
            button.setOnClickListener(this);
        }
    }
    
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
        case R.id.start_activity: 
            initiateActivity();
            break;
        case R.id.start_activity_for_result: 
        	initiateActivityForResult();
            break;
        default:
        	Log.e(TAG, "Unkowon Button:"+v.toString());
        	break;
        }
    }

    private void initiateActivity() {
        Intent intent = new Intent(getApplicationContext(),
                                   AnotherActivity.class);
        startActivity(intent);	
    }
    
   // Activity의 실행요구 코드
    private static final int ANOTHER_ACTIVITY = 1;

    // Activity를 실행하다
    private void initiateActivityForResult() {
        Intent intent = new Intent(getApplicationContext(),
                                   AnotherActivity.class);
        //두 번째 전달인자에 실행요구 코드를 지정하다
        startActivityForResult(intent, ANOTHER_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int reqCode,
                                    int resCode,
                                    Intent resIntent) {
        //되돌아 온  실행요구 코드에서 어느 Activity가 종료했는지를 판단하다
        if (reqCode == ANOTHER_ACTIVITY) {
            TextView textView = (TextView)findViewById(R.id.result);
            if (resCode != RESULT_OK) {
            	 //결과가 OK가 아니라면 에러를 표시
                textView.setText("Error.");
            } else {
            	//인텐트로 설정된 텍스트를 호출
                String result = resIntent.getExtras().getString(
                		AnotherActivity.TEXT_RESULT);
                //텍스트를 텍스트 뷰로 표시
                textView.setText(result);
            }
        } else {
            super.onActivityResult(reqCode, resCode, resIntent);
        }
    }
}