package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.telephony.*;
import android.view.*;
import android.widget.*;

public class SendSms extends Activity {
	TextView mNum;
	TextView mText;
	TextView mSent;
	TextView mDelivery;
	final static String ACTION_SENT =  "ACTION_MESSAGE_SENT";
	final static String ACTION_DELIVERY =  "ACTION_MESSAGE_DELIVERY";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendsms);

		mNum = (TextView)findViewById(R.id.smsnum);
		mText = (TextView)findViewById(R.id.smstext);
		mSent = (TextView)findViewById(R.id.sentok);
		mDelivery = (TextView)findViewById(R.id.deliveryok);

		findViewById(R.id.sendsms).setOnClickListener(mClickListener);
	}

	// BR 등록 및 해제
	protected void onResume() {
		super.onResume();
		registerReceiver(mSentBR, new IntentFilter(ACTION_SENT));
		registerReceiver(mDeliveryBR, new IntentFilter(ACTION_DELIVERY));
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(mSentBR);
		unregisterReceiver(mDeliveryBR);
	}

	// 메시지 보내기
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sendsms:
				SmsManager sms = SmsManager.getDefault();
				String num = mNum.getText().toString();
				String text = mText.getText().toString();

				if (num.length() == 0 || text.length() == 0) {
					return;
				}

				mSent.setText("송신 대기...");
				mDelivery.setText("상대방 수신 대기...");

				PendingIntent SentIntent = PendingIntent.getBroadcast(
						SendSms.this, 0, new Intent(ACTION_SENT), 0);
				PendingIntent DeliveryIntent = PendingIntent.getBroadcast(
						SendSms.this, 0, new Intent(ACTION_DELIVERY), 0);
				sms.sendTextMessage(num, null, text, SentIntent, DeliveryIntent);
				break;
			}
		}
	};

	// 송신 확인
	BroadcastReceiver mSentBR = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			if (getResultCode() == Activity.RESULT_OK) {
				mSent.setText("메시지 송신 성공");
			} else {
				mSent.setText("메시지 송신 실패");
			}
		}
	};

	// 수신 확인
	BroadcastReceiver mDeliveryBR = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			if (getResultCode() == Activity.RESULT_OK) {
				mDelivery.setText("상대방 수신 성공");
			} else {
				mDelivery.setText("상대방 수신 실패");
			}
		}
	};
}
