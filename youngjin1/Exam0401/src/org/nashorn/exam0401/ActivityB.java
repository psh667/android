package org.nashorn.exam0401;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityB extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub2);
        
        Button exitButton = (Button)findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }
}