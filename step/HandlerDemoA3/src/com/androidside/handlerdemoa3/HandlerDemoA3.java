package com.androidside.handlerdemoa3;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//사용자 화면이 멈추지 않고 동작함
public class HandlerDemoA3 extends Activity {
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
                Toast.makeText(HandlerDemoA3.this, "테스트 버튼이 클릭되었습니다.",
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
    
    int i = 0;
    private void loop() {
        // 0.1초씩 대기하면서 100번 카운트(총 10초 동안 실행됨)
        for (i = 1; i <= 100; i++) {
            try {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("" + i);            
                    }         
                });
                
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
    
    Handler handler = new Handler();
}