package org.androidtown.basic.receiver.sms;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * SMS를 수신하면 화면에 그 내용을 보여주는 방법을 알 수 있습니다.
 * 브로드캐스트 수신자를 이용하면서 수신 SMS의 내용을 화면에 보여주는 방법을 알 수 있습니다.
 * 
 * 이 메인 액티비티는 화면에 보여주기 위한 것이 아닙니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
