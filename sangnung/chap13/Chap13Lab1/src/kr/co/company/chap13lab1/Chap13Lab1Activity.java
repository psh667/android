package kr.co.company.chap13lab1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.Toast;

public class Chap13Lab1Activity extends Activity {
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			Object path = message.obj;
			if (message.arg1 == RESULT_OK && path != null) {
				Toast.makeText(getApplicationContext(),
						" " + path.toString() + "을 다운로드하였음.", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(getApplicationContext(), "다운로드 실패",
						Toast.LENGTH_LONG).show();
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onClick(View view) {
		Intent intent = new Intent(this, DownloadService.class);
		Messenger messenger = new Messenger(handler);
		intent.putExtra("MESSENGER", messenger);
		intent.setData(Uri.parse("http://www.naver.com/index.html"));
		intent.putExtra("urlpath", "http://www.naver.com/index.html");
		startService(intent);
	}

}
