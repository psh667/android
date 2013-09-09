package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class SpeedTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speedtest);
	}

	public void mOnClick(View v) {
		int i;
		int a, b=123, c=456;
		long start, end;
		
		start = System.currentTimeMillis();
		for (i=0;i<500000000;i++) {
			a=b+c;
		}
		end = System.currentTimeMillis();
		
		TextView result = (TextView)findViewById(R.id.result);
		String sres = "덧셈 5억번에 총 " + (end-start)/1000.0 + " 초가 걸렸습니다.";
		result.setText(sres);
		
	}
}
