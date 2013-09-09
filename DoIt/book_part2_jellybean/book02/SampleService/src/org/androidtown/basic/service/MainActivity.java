package org.androidtown.basic.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 서비스 객체를 만들고 시작하는 방법을 알 수 있습니다.
 * 
 * 서비스는 애플리케이션 구성요소이므로 매니페스트에 등록하는 것을 잊지말아야 합니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button startButton = (Button) findViewById(R.id.startButton);
		
		// 버튼을 눌렀을 때 서비스 시작
        startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 인텐트 객체를 만듭니다.
		        Intent myIntent = new Intent(getBaseContext(), MyService.class);
		        
		        // 서비스를 시작합니다.
		        startService(myIntent);

			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
