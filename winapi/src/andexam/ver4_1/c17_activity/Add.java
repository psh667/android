package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;

public class Add extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		Intent intent = getIntent();
		int left = intent.getIntExtra("left", 1);
		int right = intent.getIntExtra("right", 2);
		TextView result = (TextView)findViewById(R.id.result);
		result.setText(String.format("%d + %d = %d", left, right, left + right));
	}
}
