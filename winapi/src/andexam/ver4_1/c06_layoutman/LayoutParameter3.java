package andexam.ver4_1.c06_layoutman;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class LayoutParameter3 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout linear = new LinearLayout(this);
		linear.setOrientation(LinearLayout.VERTICAL);
		linear.setGravity(Gravity.CENTER);
		linear.setBackgroundColor(Color.LTGRAY);

		TextView text = new TextView(this);
		text.setText("TextView");
		text.setTextColor(Color.RED);
		text.setTextSize(20);
		text.setBackgroundColor(Color.GREEN);

		LinearLayout.LayoutParams paramtext = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		linear.addView(text, paramtext);

		setContentView(linear);
	}
}