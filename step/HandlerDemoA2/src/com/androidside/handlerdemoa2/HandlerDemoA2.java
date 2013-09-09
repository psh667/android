package com.androidside.handlerdemoa2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//사용자 화면이 멈추지 않고 동작함
public class HandlerDemoA2 extends Activity {
    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        text = (TextView) findViewById(R.id.text);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doit();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HandlerDemoA2.this, "테스트 버튼이 클릭되었습니다.",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void doit() {
        new Thread() {
            @Override
            public void run() {
                loop();
            }
        }.start();
    }

    private void loop() {
        // 0.1초씩 대기하면서 100번 카운트(총 10초 동안 실행됨)
        for (int i = 1; i <= 100; i++) {
            try {
                handler.sendEmptyMessage(i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            text.setText("" + msg.what);
        };
    };
}