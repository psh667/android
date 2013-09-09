package org.androidtown.basic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * SMS 수신을 위해 정의한 브로드캐스트 수신자
 * 
 * @author Mike
 *
 */
public class MySMSBroadcastReceiver extends BroadcastReceiver {

	/**
	 * 메시지가 수신되었을 때 자동으로 호출되는 메소드입니다.
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("MySMSBroadcastReceiver", "onReceive");
		
        // SMS 액션으로 수신된 메시지인지 확인합니다. 여러가지 메시지를 수신할 때 각각을 구분하기 위해 사용합니다.
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Log.d("MySMSBroadcastReceiver", "SMS 메시지가 수신되었습니다.");
        
            // 우선 순위가 뒤에 있는 수신자들이 받지 못하도록 브로드캐스팅 전달을 취소합니다.
            abortBroadcast();
            
            // 메인 액티비티를 띄워줍니다. 메인 액티비티가 실행되어 있지 않을 경우를 위해 FLAG_ACTIVITY_NEW_TASK 플래그를 사용합니다.
            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
            
        }
	}
}
