package com.Project3;

import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
	String tYut[] = {"¡Û¡Û¡Û¡Û¡Û", "¡Ü¡Ü¡Ü¡Ü¡Ü"};
	String Yut[] = {"À·", "µµ", "°³", "°É", "¸ð"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.Button01).setOnClickListener(myButtonClick);
    }
    
    //----------------------------------------
    //   Button OnClickListener
    //----------------------------------------
    Button.OnClickListener myButtonClick = new Button.OnClickListener() {
		public void onClick(View v) {
			Random rnd = new Random();
			int n1 = 1 - rnd.nextInt(10) / 6;
			int n2 = 1 - rnd.nextInt(10) / 6;
			int n3 = 1 - rnd.nextInt(10) / 6;
			int n4 = 1 - rnd.nextInt(10) / 6;
			int n = n1 + n2 + n3 + n4;
			
			((TextView) findViewById(R.id.TextView01)).setText(tYut[n1]);
			((TextView) findViewById(R.id.TextView02)).setText(tYut[n2]);
			((TextView) findViewById(R.id.TextView03)).setText(tYut[n3]);
			((TextView) findViewById(R.id.TextView04)).setText(tYut[n4]);
			((TextView) findViewById(R.id.TextView05)).setText(Yut[n]);
		}
	};
}