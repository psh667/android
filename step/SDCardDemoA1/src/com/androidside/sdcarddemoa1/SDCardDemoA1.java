package com.androidside.sdcarddemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SDCardDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TextView text = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        
        button.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View arg0) {
                if (isSdcard()) {
                    text.setText("SDCard 사용 가능");
                } else {
                    text.setText("SDCard 사용 불가능");
                }                
            }
        });
    }
    
    public boolean isSdcard() {
        String storagestate = Environment.getExternalStorageState();
        return storagestate.equals(Environment.MEDIA_MOUNTED);
    }
}