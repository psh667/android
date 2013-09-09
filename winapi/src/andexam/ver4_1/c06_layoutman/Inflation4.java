package andexam.ver4_1.c06_layoutman;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Inflation4 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LinearLayout linear = new LinearLayout(this);
		linear.setOrientation(LinearLayout.VERTICAL);
		linear.setBackgroundColor(Color.LTGRAY);

		TextView text = (TextView)View.inflate(this, R.layout.mytext, null);

		linear.addView(text);
		setContentView(linear);
	}
}