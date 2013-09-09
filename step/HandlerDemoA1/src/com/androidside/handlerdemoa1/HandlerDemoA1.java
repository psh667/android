package com.androidside.handlerdemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//사용자 화면이 멈추는 현상이 발생함
public class HandlerDemoA1 extends Activity {
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
                Toast.makeText(HandlerDemoA1.this, "테스트 버튼이 클릭되었습니다.",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void doit() {
        //0.1초씩 대기하면서 100번 카운트(총 10초 동안 실행됨)
        for (int i = 1; i <= 100; i++) {
            try {
                text.setText(""+i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

/*private void doit() {
    //0.1초씩 대기하면서 100번 카운트(총 10초 동안 실행됨)
    new Thread() { 
        @Override
        public void run() {
            for (int i = 1; i <= 100; i++) {
                try {
                    text.setText(""+i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }.start();        
}*/

/*private void doit() {
    //0.1초씩 대기하면서 100번 카운트(총 10초 동안 실행됨)
    new Thread() { 
        @Override
        public void run() {
            for (int i = 1; i <= 100; i++) {
                try {
                    final int var = i;
                    HandlerDemoA1.this.runOnUiThread(new Runnable() {                        
                        @Override
                        public void run() {
                            text.setText(""+var);                            
                        }
                    });
                    
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }.start();        
}*/
}