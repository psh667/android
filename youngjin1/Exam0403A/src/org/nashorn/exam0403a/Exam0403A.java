package org.nashorn.exam0403a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Exam0403A extends Activity {
	private final static int SUB_ACTIVITY = 1;
	private EditText intentValueText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        intentValueText = (EditText)findViewById(R.id.intent_value);
        
        Button startButton = (Button)findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Exam0403A.this, SubActivity.class);
				i.putExtra("etTextValue", intentValueText.getText().toString());
				startActivityForResult(i, SUB_ACTIVITY);
			}
		});
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	switch(requestCode)
    	{
    	case SUB_ACTIVITY:
    		if (resultCode == Activity.RESULT_OK)
    		{
    			String returnString = data.getStringExtra("rtTextValue");
    			
    			intentValueText.setText(returnString);
    		}
    		break;
    	}
    }
}