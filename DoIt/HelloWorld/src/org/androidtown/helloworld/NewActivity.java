package org.androidtown.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);
        
        // 버튼 객체 참조
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "돌아가기 버튼이 눌렸어요.",
                           Toast.LENGTH_LONG).show();
            finish();
          }
        });    
    }

}
