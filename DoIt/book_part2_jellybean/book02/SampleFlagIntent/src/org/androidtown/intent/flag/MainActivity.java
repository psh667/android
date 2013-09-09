package org.androidtown.intent.flag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 인텐트를 이용해 새로운 액티비티를 띄울 때 플래그를 사용하는 방법과 효과에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class MainActivity extends Activity {

	Button showBtn;
	TextView txtMsg;
	String msg;

	/**
	 * 요청 코드 정의
	 */
	public static final int REQUEST_CODE_ANOTHER = 1001;
	
	/**
	 * 시작 횟수
	 */
	public static int startCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		txtMsg = (TextView) findViewById(R.id.txtMsg);
		showBtn = (Button) findViewById(R.id.showBtn);
		
		// 버튼을 눌렀을 때 새로운 액티비티를 띄워줍니다.
		showBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
   				startCount++;
   				
    			// 인텐트 객체를 만듭니다.
    			Intent intent = new Intent(getBaseContext(), AnotherActivity.class);
    			
    			// startCount 값을 넣어줍니다.
    			intent.putExtra("startCount", startCount);
    			
    			// 액티비티를 띄워주도록 startActivityForResult() 메소드를 호출합니다.
   				startActivityForResult(intent, REQUEST_CODE_ANOTHER);
			}
		});

		// 전달받은 인텐트를 처리합니다.
		processIntent();	
    }

    @Override
	protected void onNewIntent(Intent intent) {
    	// 전달받은 인텐트를 처리합니다.
    	processIntent();
		
		super.onNewIntent(intent);
	}

    /**
     * 전달받은 인텐트를 처리하는 메소드 정의
     */
    private void processIntent() {
		// 인텐트 객체를 확인합니다.
	    Intent receivedIntent = getIntent();
	    startCount = receivedIntent.getIntExtra("startCount", 0);
	
	    // 텍스트뷰에 startCount 값을 보여줍니다.
	    msg = "전달된 startCount : " + startCount;
	    txtMsg.setText(msg);
    }

    /**
     * 새로운 액티비티에서 돌아올 때 자동 호출되는 메소드
     */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_ANOTHER) {
			Toast toast = Toast.makeText(getBaseContext(), "onActivityResult() 메소드가 호출됨. 요청코드 : " + requestCode + ", 결과코드 : " + resultCode, Toast.LENGTH_LONG);
			toast.show();
		}

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
