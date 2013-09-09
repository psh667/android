package org.nashorn.exam0403a;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends Activity {
	private EditText intentValueText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);
        
        Intent intent = getIntent();
        
        String textString = intent.getStringExtra("etTextValue");
        
        intentValueText = (EditText)findViewById(R.id.intent_value);
        intentValueText.setText(textString);
        
        Button startButton = (Button)findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri data = Uri.parse("content://exam0403a/sub_activity");
				Intent result = new Intent(null, data);
				result.putExtra("rtTextValue", intentValueText.getText().toString());
				setResult(RESULT_OK, result);
				
				finish();
			}
		});
    }
}