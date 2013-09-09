package com.androidside.intentdemoc1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntentCaller2 extends Activity implements View.OnClickListener {
    private final int CALLER_REQUEST = 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intentcaller2);
        
        Button button = (Button) findViewById(R.id.Input);
        button.setOnClickListener(this);
    }

    public void onClick(View v) {        
        Intent intent = new Intent(this, IntentCallee2.class);
        startActivityForResult(intent, CALLER_REQUEST);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == CALLER_REQUEST) {
            if (resultCode == RESULT_OK) {                
                String name = intent.getExtras().get("name").toString();
                String age = intent.getExtras().get("age").toString();
                
                TextView nameText = (TextView) findViewById(R.id.Name);        
                nameText.setText(name);
                
                TextView ageText = (TextView) findViewById(R.id.Age);
                ageText.setText(age+"");   
            }
        }
    }
}
