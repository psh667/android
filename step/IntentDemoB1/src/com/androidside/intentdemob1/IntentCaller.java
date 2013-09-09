package com.androidside.intentdemob1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IntentCaller extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intentcaller);
        
        Button button = (Button) findViewById(R.id.Send);
        button.setOnClickListener(this);            
    }

    public void onClick(View v) {
        EditText nameEdit = (EditText) findViewById(R.id.Name);
        EditText ageEdit = (EditText) findViewById(R.id.Age);
        
        Intent intent = new Intent(this, IntentCallee.class);        
        intent.putExtra("name", nameEdit.getText());
        intent.putExtra("age", ageEdit.getText());
        startActivity(intent);
    }
}