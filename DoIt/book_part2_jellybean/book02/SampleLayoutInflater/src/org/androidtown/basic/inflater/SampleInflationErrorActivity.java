package org.androidtown.basic.inflater;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 레이아웃 인플레이션 이전에 객체를 참조할 때 에러가 발생하는 것을 확인할 수 있습니다.
 * 
 * @author Mike
 */
public class SampleInflationErrorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
        
        setContentView(R.layout.activity_main);

    }

}
