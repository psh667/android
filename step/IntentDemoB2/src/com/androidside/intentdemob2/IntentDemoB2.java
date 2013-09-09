package com.androidside.intentdemob2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IntentDemoB2 extends Activity implements View.OnClickListener {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.Send);
        button.setOnClickListener(this);            
    }

    public void onClick(View v) {
        EditText nameEdit = (EditText) findViewById(R.id.Name);
        EditText ageEdit = (EditText) findViewById(R.id.Age);
        
        PersonInfo info = new PersonInfo();
        info.name = nameEdit.getText().toString();
        info.age = ageEdit.getText().toString();
        
        Intent intent = new Intent(this, IntentCallee.class);        
        intent.putExtra("personinfo", info);
        
        startActivity(intent);
    }

}
