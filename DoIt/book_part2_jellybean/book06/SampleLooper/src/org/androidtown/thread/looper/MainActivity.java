package org.androidtown.thread.looper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 루퍼를 사용하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	TextView textView01, textView02;
	EditText editText01, editText02;

	/**
	 * 메인 스레드의 핸들러
	 */
	MainHandler mainHandler;
	
	/**
	 * 새로 만든 스레드
	 */
	ProcessThread thread1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new MainHandler();
		thread1 = new ProcessThread();

        textView01 = (TextView) findViewById(R.id.textView01);
        textView02 = (TextView) findViewById(R.id.textView02);
        editText01 = (EditText) findViewById(R.id.editText01);
        editText02 = (EditText) findViewById(R.id.editText02);

        // 버튼 이벤트 처리
        Button processBtn = (Button) findViewById(R.id.processBtn);
        processBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		String inStr = editText01.getText().toString();
        		Message msgToSend = Message.obtain();
        		msgToSend.obj = inStr;

        		thread1.handler.sendMessage(msgToSend);
        	}
        });

        thread1.start();
    }

    /**
     * 새로 정의한 스레드
     */
    class ProcessThread extends Thread {
    	// 새로운 스레드를 위한 핸들러
    	ProcessHandler handler;

    	public ProcessThread() {
    		handler = new ProcessHandler();
    	}

    	public void run() {
    		// 루퍼 사용
    		Looper.prepare();
    		Looper.loop();
    	}

    }

    class ProcessHandler extends Handler {
    	public void handleMessage(Message msg) {
    		Message resultMsg = Message.obtain();
    		resultMsg.obj = msg.obj + " Mike!!!";

    		mainHandler.sendMessage(resultMsg);
    	}
    }

    class MainHandler extends Handler {
    	public void handleMessage(Message msg) {
    		String str = (String) msg.obj;
    		editText02.setText(str);
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
