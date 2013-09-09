package org.androidtown.intent.call;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 인텐트로 전화걸기 화면을 보여주는 방법을 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	TextView text01;
	EditText edit01;
	Button btnCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		text01 = (TextView) findViewById(R.id.text01);
		edit01 = (EditText) findViewById(R.id.edit01);

		btnCall = (Button) findViewById(R.id.btnCall);

		// 전화걸기 버튼을 눌렀을 때 처리하는 리스너를 객체로 설정
		btnCall.setOnClickListener(new ClickHandler());

	}

    /**
     * 클릭 이벤트를 처리하는 클래스를 별도로 정의
     */
	private class ClickHandler implements OnClickListener {
		public void onClick(View v) {
			try {
				// 입력상자에 입력한 전화번호를 가져옴
				String myData = edit01.getText().toString();
				
				// 인텐트를 만들고 이것을 이용해 액티비티를 띄워줌
				Intent myActivity2 = new Intent(Intent.ACTION_DIAL, Uri.parse(myData));
				startActivity(myActivity2);

			} catch (Exception ex) {
				Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
