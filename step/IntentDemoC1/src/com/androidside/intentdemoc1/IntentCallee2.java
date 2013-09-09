package com.androidside.intentdemoc1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IntentCallee2 extends Activity implements View.OnClickListener {                                           
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intentcallee2);
        
        Button button = (Button) findViewById(R.id.OK);
        button.setOnClickListener(this);
    }    

    public void onClick(View v) {
        EditText nameEdit = (EditText) findViewById(R.id.Name);
        EditText ageEdit = (EditText) findViewById(R.id.Age);
        
        Intent intent = new Intent(this, IntentCaller2.class);        
        intent.putExtra("name", nameEdit.getText());
        intent.putExtra("age", ageEdit.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
