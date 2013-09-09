package com.androidside.intentdemoa2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IntentDemoA2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        final EditText text = (EditText) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        
        button.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View arg0) {                
                Uri uri = Uri.fromParts("package", text.getText().toString(), null);
                Intent intent = new Intent(Intent.ACTION_DELETE, uri);
                startActivity(intent);
            }
        });
    }
}
