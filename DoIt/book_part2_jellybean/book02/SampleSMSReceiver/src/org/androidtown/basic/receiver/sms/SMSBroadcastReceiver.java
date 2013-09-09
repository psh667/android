package org.androidtown.basic.receiver.sms;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * SMS 수신을 위한 브로드캐스트 수신자입니다.
 * 
 * @author Mike
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

	/**
	 * 로깅을 위한 태그
	 */
	public static final String TAG = "SMSBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive() 메소드 호출됨.");

        // SMS 수신 시의 메시지인지 다시 한번 확인합니다.
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Log.i(TAG, "SMS를 수신하였습니다.");

            // 우선순위가 낮은 다른 SMS 수신 앱들이 문자를 전달받지 못하도록 전달을 취소합니다.
            abortBroadcast();

            // SMS 메시지를 파싱합니다.
            Bundle bundle = intent.getExtras();
    		Object messages[] = (Object[])bundle.get("pdus");
    		SmsMessage smsMessage[] = new SmsMessage[messages.length];

    		int smsCount = messages.length;
    		for(int i = 0; i < smsCount; i++) {
    			// PDU 포맷으로 되어 있는 메시지를 복원합니다.
    			smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
    		}

    		// SMS 수신 시간 확인
    		Date curDate = new Date(smsMessage[0].getTimestampMillis());
    		Log.i(TAG, "SMS Timestamp : " + curDate.toString());

    		// SMS 발신 번호 확인
    		String origNumber = smsMessage[0].getOriginatingAddress();
    		
    		// SMS 메시지 확인
    		String message = smsMessage[0].getMessageBody().toString();
    		Log.i(TAG, "SMS : " + origNumber + ", " + message);

            // 메시지를 보여줄 액티비티를 띄워줍니다.
            Intent myIntent = new Intent(context, SMSDisplayActivity.class);
            
            // 플래그를 이용합니다.
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            myIntent.putExtra("number", origNumber);
            myIntent.putExtra("message", message);
            myIntent.putExtra("timestamp", curDate.toString());

            context.startActivity(myIntent);

        }

	}

}
