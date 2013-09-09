package org.androidtown.event.keyevent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 버튼을 누르면 다른 액티비티를 띄워주고 그 액티비티에서 BACK 키를 눌렀을 때 돌아오는 방법을 알 수 있는 프로젝트
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	/**
	 * 다른 액티비티를 띄우기 위해 정의한 요청 코드
	 */
	public static final int REQUEST_CODE_ANOTHER = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 클릭 이벤트 처리
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// 다른 액티비티 띄우기
    			Intent intent = new Intent(getBaseContext(), AnotherActivity.class);
   				startActivityForResult(intent, REQUEST_CODE_ANOTHER);
        	}
        });

        // 버튼 배경색을 빨간색으로 변경
        startButton.setBackgroundColor(0xffff0000);
        
        // 버튼 글자를 하얀색으로 변경
        startButton.setTextColor(0xffffffff);

    }


    /**
     * 다른 액티비티에서 응답을 받았을 때 처리
     */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_ANOTHER) {
			Toast toast = Toast.makeText(getBaseContext(), "onActivityResult called with code : " + resultCode, Toast.LENGTH_LONG);
			toast.show();

			if (resultCode == Activity.RESULT_OK) {
				String name = data.getExtras().getString("name");
				toast = Toast.makeText(getBaseContext(), "다른 액티비티에서 전달받은 이름 : " + name, Toast.LENGTH_LONG);
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
