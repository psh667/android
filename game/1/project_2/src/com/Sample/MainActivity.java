package com.Sample;

import java.util.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.Button01).setOnClickListener(myButtonClick);
        findViewById(R.id.Button02).setOnClickListener(myButtonClick);
        findViewById(R.id.Button03).setOnClickListener(myButtonClick);
        findViewById(R.id.Button04).setOnClickListener(myButtonClick);
    } // End of onCreate
    
    //----------------------------------------
    //   Button OnClickListener
    //----------------------------------------
    Button.OnClickListener myButtonClick = new Button.OnClickListener() {
		public void onClick(View v) {
			int n = Integer.parseInt(v.getTag().toString());
			String s;
	        int r = new Random().nextInt(4) + 1;  // 1~4 사이의 난수
	        
	        
/*	        
			switch (v.getId()) {	// 버튼의 id 구하기
			case R.id.Button01 :
				n = 1;
				break;
			case R.id.Button02 :
				n = 2;
				break;
			case R.id.Button03 :
				n = 3;
				break;
			case R.id.Button04 :
				n = 4;
			}
*/
			
			if (n == r)
				s = "축하합니다. 당첨되셨습니다!";
			else
				s = "안타깝습니다.\n다음 기회에 다시 도전하세요~";
			
			((TextView) findViewById(R.id.TextView01)).setText(s);
		}
    };
    
} // End of Activity
