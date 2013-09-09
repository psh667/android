package org.androidtown.intent.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 인텐트를 이용해 새로운 액티비티를 띄우고 다시 돌아오는 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class MainActivity extends Activity {

	/**
	 * 요청 코드 정의
	 */
	public static final int REQUEST_CODE_ANOTHER = 1001;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showBtn = (Button) findViewById(R.id.showBtn);
		
		// 버튼을 눌렀을 때 새로운 액티비티를 띄워줍니다.
		showBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
    			// 인텐트 객체를 만드는 방법 #1
				
				// 인텐트 객체를 만듭니다.
    			Intent intent = new Intent(getBaseContext(), AnotherActivity.class);
    			
    			// 액티비티를 띄워주도록 startActivityForResult() 메소드를 호출합니다.
   				startActivityForResult(intent, REQUEST_CODE_ANOTHER);
			
   				// 인텐트 객체를 만드는 방법 #2
        		//Intent intent = new Intent();
    			//ComponentName name = new ComponentName("org.androidtown.intent.basic", "org.androidtown.intent.basic.AnotherActivity");
    			//intent.setComponent(name);
    			
    			//startActivityForResult(intent, REQUEST_CODE_ANOTHER);
    			
			}
		});

    }

    /**
     * 새로운 액티비티에서 돌아올 때 자동 호출되는 메소드
     */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_ANOTHER) {
			Toast toast = Toast.makeText(getBaseContext(), "onActivityResult() 메소드가 호출됨. 요청코드 : " + requestCode + ", 결과코드 : " + resultCode, Toast.LENGTH_LONG);
			toast.show();

			if (resultCode == RESULT_OK) {
				String name = data.getExtras().getString("name");
				toast = Toast.makeText(getBaseContext(), "응답으로 전달된 name : " + name, Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
