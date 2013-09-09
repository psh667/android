package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.telephony.*;
import android.view.*;
import android.widget.*;

public class CallSms extends Activity {
	TextView mNum;
	TextView mText;
	final static String ACTION_SENT =  "ACTION_MESSAGE_SENT";
	final static String ACTION_DELIVERY =  "ACTION_MESSAGE_DELIVERY";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.callsms);

		mNum = (TextView)findViewById(R.id.smsnum);
		mText = (TextView)findViewById(R.id.smstext);

		findViewById(R.id.sendsms).setOnClickListener(mClickListener);
	}

	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sendsms:
				String num = mNum.getText().toString();
				String text = mText.getText().toString();

				Intent intent = new Intent(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("smsto:" + num));
				intent.putExtra("sms_body", text);
				startActivity(intent);
				break;
			}
		}
	};
}
