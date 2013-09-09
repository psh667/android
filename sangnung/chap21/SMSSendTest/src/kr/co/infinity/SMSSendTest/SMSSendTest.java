package kr.co.infinity.SMSSendTest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SMSSendTest extends Activity {
	private EditText receiver, message;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		receiver = (EditText) findViewById(R.id.receiver);
		message = (EditText) findViewById(R.id.message);
	}

	public void onClick(View v) {
		Uri n = Uri.parse("smsto: " + receiver.getText());
		Intent intent = new Intent(Intent.ACTION_SENDTO, n);
		String t = message.getText().toString();
		intent.putExtra("sms_body", t);
		startActivity(intent);

	}
}
