package com.Project4;

import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
	int imgYut[] = {R.drawable.yut_0, R.drawable.yut_1}; 
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

			((ImageView) findViewById(R.id.ImageView01)).setImageResource(imgYut[n1]);
			((ImageView) findViewById(R.id.ImageView02)).setImageResource(imgYut[n2]);
			((ImageView) findViewById(R.id.ImageView03)).setImageResource(imgYut[n3]);
			((ImageView) findViewById(R.id.ImageView04)).setImageResource(imgYut[n4]);
			((TextView) findViewById(R.id.TextView05)).setText(Yut[n]);
		}
	};
}