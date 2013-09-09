package kr.co.infinity.IntentServiceTest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IntentServiceTest extends Activity {
	Intent i;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {                
            	i = new Intent(IntentServiceTest.this, MyIntentService.class);
            	startService(i);
            }
        });
        
        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopService(i);
            }
        });         
    }
}