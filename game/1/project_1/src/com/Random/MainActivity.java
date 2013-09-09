package com.Random;

import java.util.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
	// 전역 변수 선언
    int Counter;					// 사용자가 압력한 횟수
    int n;							// 난수 발생용               	
    EditText edit;					// 사용자가 입력하는 컨트롤
    TextView tResult;				// 처리 결과를 입력할 컨트롤
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 변수 초기화
        Counter = 0;
        n = new Random().nextInt(501) + 500;
        edit = (EditText) findViewById(R.id.EditText01);
        tResult = (TextView) findViewById(R.id.TextView02);
        findViewById(R.id.Button01).setOnClickListener(myButtonClick);
    } // onCreate 끝
    
    //----------------------------------------
    //   Button OnClickListener
    //----------------------------------------
    Button.OnClickListener myButtonClick = new Button.OnClickListener() {
		public void onClick(View v) {
			String s;
			Counter++;
			int p = Integer.parseInt(edit.getText().toString());
			if (p < 500 || p > 1000) s = "입력한 값이 500~1000을 벗어났습니다";
			else if (p == n) s = Counter + "번째에 맞추셨습니다";
			else if (p > n) s = p + "보다 작은 값입니다";
			else s = p + "보다 큰 값입니다";
			tResult.setText(s);
		}
    };

} // Activity의 끝


