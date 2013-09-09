package com.androidside.intentdemoa1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IntentDemoA1 extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final EditText numberEdit = (EditText) findViewById(R.id.number);
        Button callButton = (Button) findViewById(R.id.call);
        
        callButton.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View arg0) {
                String number = numberEdit.getText().toString();
                
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });
    }
}
