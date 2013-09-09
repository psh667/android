package org.androidtown.ui.bitmap.selector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * 비트맵 Selector를 이용해 비트맵 버튼을 만드는 방법을 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 타이틀 부분 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        // 버튼 이벤트 처리
        Button arrowLeftBtn = (Button)findViewById(R.id.arrowLeftBtn);
        arrowLeftBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(getApplicationContext(), "버튼이 눌렸어요.", Toast.LENGTH_SHORT).show();
        	}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
