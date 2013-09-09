package com.androidside.framelayoutdemob1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FrameLayoutDemoB1 extends Activity implements View.OnClickListener {
    TextView redText;
    TextView greenText;
    TextView blueText;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.red).setOnClickListener(this);
        findViewById(R.id.green).setOnClickListener(this);
        findViewById(R.id.blue).setOnClickListener(this);     
        
        redText = (TextView) findViewById(R.id.redText);
        greenText = (TextView) findViewById(R.id.greenText);
        blueText = (TextView) findViewById(R.id.blueText);
    }

    @Override
    public void onClick(View v) {
        redText.setVisibility(View.INVISIBLE);
        greenText.setVisibility(View.INVISIBLE);
        blueText.setVisibility(View.INVISIBLE);
        
        if (v.getId() == R.id.red) redText.setVisibility(View.VISIBLE);
        if (v.getId() == R.id.green) greenText.setVisibility(View.VISIBLE);
        if (v.getId() == R.id.blue) blueText.setVisibility(View.VISIBLE);
    }
}