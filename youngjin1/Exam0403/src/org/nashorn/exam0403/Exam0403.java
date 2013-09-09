package org.nashorn.exam0403;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Exam0403 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button callButton = (Button)findViewById(R.id.call);
		callButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				String toDial = "tel:0123456789";
				startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(toDial)));
				finish();
			}
        });
    }
}