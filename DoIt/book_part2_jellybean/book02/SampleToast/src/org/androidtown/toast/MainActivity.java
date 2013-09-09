package org.androidtown.toast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 토스트의 위치를 원하는 곳에 두고 보여주는 방법을 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {
	
	EditText edit01;
	EditText edit02;
	Button showBtn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        edit01 = (EditText) findViewById(R.id.edit01);
        edit02 = (EditText) findViewById(R.id.edit02);

        // 토스트 보이기 버튼을 눌렀을 때 토스트의 위치를 지정하고 보여줍니다.
		showBtn = (Button) findViewById(R.id.showBtn);
		showBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					Toast toastView = Toast.makeText(getApplicationContext(),
							"Hello Android!",
							Toast.LENGTH_LONG);
					
					int xOffset = Integer.valueOf(edit01.getText().toString());
					int yOffset = Integer.valueOf(edit02.getText().toString());
					
					// 입력된 x, y offset 값을 이용해 위치를 지정합니다.
					toastView.setGravity(Gravity.CENTER, xOffset, yOffset);
					
					toastView.show();

				} catch (NumberFormatException ex) {
					Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
