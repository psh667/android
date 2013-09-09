package kr.co.infinity.ImplicitIntent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImplicitIntent extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	    Button b = (Button)findViewById(R.id.Button01);
	    b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(Intent.ACTION_CALL);
				in.setData(Uri.parse("tel:01012341234"));
				startActivity(in);
			}
		}); 

    }
}