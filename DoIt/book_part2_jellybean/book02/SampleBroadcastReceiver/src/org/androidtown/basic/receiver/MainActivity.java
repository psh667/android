package org.androidtown.basic.receiver;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;

/**
 * SMS를 수신하는 브로드캐스트 수신자입니다.
 * 수신자를 별도의 클래스로 정의한 후 등록하여 사용하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 브로드캐스트 수신자로 정의한 클래스의 인스턴스 객체를 생성합니다.
        MySMSBroadcastReceiver myReceiver = new MySMSBroadcastReceiver();

        // SMS를 받기 위한 인텐트필터(SMS_RECEIVED)를 생성합니다.
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        // 수신자를 코드에서 등록합니다. 매니페스트에 등록하면 이 부분은 생략 가능합니다.
        //registerReceiver(myReceiver, filter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
