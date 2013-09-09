package com.corea.ServiceDemo;

import com.corea.ServiceDemo.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//서비스의 시작을 요구하는 Activity
public class ServiceDemo extends Activity {

    ComponentName mService;  //기동한 서비스의 이름
    TextView mTextView;      //서비스의  상태를 표시한다

    /*
     *서비스의 시작버튼과 종료버튼을 등록한다
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextView = (TextView) findViewById(R.id.text_view_id);
        Button start = (Button) findViewById(R.id.start_button_id);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                initiateService();
            }
        });
        Button stop = (Button) findViewById(R.id.stop_button_id);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                terminateService();
            }
        });
    }

    /*
     * 같은 어플리케이션 내의 HelloService를 시작한다
     * startService에서는 기동한   서비스의 이름이 반환된다
     */
    private void initiateService() {
        mService =
                startService(new Intent(this, MyService.class));
        //mTextView.append(mService.toShortString()+" started.\n");
        mTextView.append("MyService started.\n");
    }

    /*
     * 기동한 서비스를 정지한다
     * startService에서 취득한   서비스의 이름을 설정한 인텐트를 사용한다
     */
    private void terminateService() {
        if (mService == null) {
            mTextView.append("No requested service.\n");
            return;
        }
        Intent i = new Intent();
        i.setComponent(mService);
        if (stopService(i))
            //mTextView.append(mService.toShortString()+" is stopped.\n");
            mTextView.append("MyService stopped.\n");
        else
            //mTextView.append(mService.toShortString()+" is alrady stopped.\n");
            mTextView.append("MyService already stopped.\n");
    }
}
