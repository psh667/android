package org.androidtown.networking.xmlrpc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SampleIBMLActivity.class);
				startActivity(intent);
			}
        });

        Button button02 = (Button) findViewById(R.id.button02);
        button02.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SampleIBMLActivity2.class);
				startActivity(intent);
			}
        });

        Button button03 = (Button) findViewById(R.id.button03);
        button03.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SampleIBMLActivity3.class);
				startActivity(intent);
			}
        });

        Button button04 = (Button) findViewById(R.id.button04);
        button04.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SampleIBMLActivity4.class);
				startActivity(intent);
			}
        });

        Button button05 = (Button) findViewById(R.id.button05);
        button05.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SampleIBMLActivity5.class);
				startActivity(intent);
			}
        });

        Button button06 = (Button) findViewById(R.id.button06);
        button06.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SampleIBMLActivity6.class);
				startActivity(intent);
			}
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
