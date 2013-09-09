package org.androidtown.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 첫번째 버튼 객체 참조
        Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "시작 버튼이 눌렸어요.",
                           Toast.LENGTH_LONG).show();
            
            Intent myIntent = new Intent(getApplicationContext(), NewActivity.class);
            startActivity(myIntent);
            
          }
        });
        
        // 두번째 버튼 객체 참조
        Button start02Btn = (Button) findViewById(R.id.start02Btn);
        start02Btn.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, 								Uri.parse("http://m.naver.com"));
            startActivity(myIntent);
          }
        });    

        // 세번째 버튼 객체 참조
        Button start03Btn = (Button) findViewById(R.id.start03Btn);
        start03Btn.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, 
                                Uri.parse("tel:010-7788-1234"));
            startActivity(myIntent);
          }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
