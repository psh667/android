package com.androidside.titlebardemoa4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class TitleBarDemoA4 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);        
        setContentView(R.layout.main);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("커스텀 타이틀");
    }
}