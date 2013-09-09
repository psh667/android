package com.androidside.intentdemob2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntentCallee extends Activity implements View.OnClickListener {                                           
    /** Called when the activity is first created. */
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
        
        PersonInfo info = (PersonInfo) intent.getExtras().getParcelable("personinfo");
        
        TextView nameText = (TextView) findViewById(R.id.Name);        
        nameText.setText(info.name);
        
        TextView ageText = (TextView) findViewById(R.id.Age);
        ageText.setText(info.age);        
    }

    public void onClick(View v) {
        finish();
    }
}
