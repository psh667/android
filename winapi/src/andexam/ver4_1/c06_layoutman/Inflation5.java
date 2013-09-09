package andexam.ver4_1.c06_layoutman;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Inflation5 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inflation5);
		
		Button btn = (Button)findViewById(R.id.btnnewmessage); 
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RelativeLayout rel = (RelativeLayout)View.inflate(
						Inflation5.this, R.layout.newmessage, null);

				LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
				if (linear.getChildCount() % 2 == 0) {
					rel.setBackgroundColor(Color.GRAY);
				} else {
					rel.setBackgroundColor(Color.RED);
				}
				linear.addView(rel);
			}
		});
	}
}