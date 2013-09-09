package kr.co.company.edittext;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText edittext = (EditText) findViewById(R.id.edittext);
		edittext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// 이벤트가 키다운이고 엔터키가 입력되면
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
					&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// 토스트 메시지를 출력한다.
					Toast.makeText(getApplicationContext(), 
						edittext.getText(),
						Toast.LENGTH_SHORT).show();
					return true;
				}
				return false;
			}
		});
	}
}