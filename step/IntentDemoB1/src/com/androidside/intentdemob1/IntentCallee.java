package com.androidside.intentdemob1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntentCallee extends Activity implements View.OnClickListener {   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intentcallee);
        
        Button button = (Button) findViewById(R.id.Close);
        button.setOnClickListener(this);
        
        this.setData();
    }

    public void setData() {
        Intent intent = getIntent();
        
        String name = intent.getExtras().get("name").toString();
        String age = intent.getExtras().get("age").toString();        
        
        TextView nameText = (TextView) findViewById(R.id.Name);        
        nameText.setText(name);
        
        TextView ageText = (TextView) findViewById(R.id.Age);
        ageText.setText(age);        
    }

    public void onClick(View v) {
        finish();
    }
}
