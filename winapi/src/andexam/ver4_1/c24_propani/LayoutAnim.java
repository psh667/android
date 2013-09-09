package andexam.ver4_1.c24_propani;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class LayoutAnim extends Activity {
	LinearLayout mLinear;
	int mCount = 1;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layoutanim);
		
		mLinear =(LinearLayout)findViewById(R.id.root); 
	}
	
	public void mOnClick(View v) {
		Button btn = new Button(this);
		btn.setText("B" + mCount);
		mCount++;
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mLinear.removeView(v);
			}
		});
		mLinear.addView(btn);
	}	
}
