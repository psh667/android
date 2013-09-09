package andexam.ver4_1.c31_gesture;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class TouchDump extends Activity {
	ArrayList<String> arTouch = new ArrayList<String>(); 
	TextView mResult;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touchdump);

		mResult = (TextView)findViewById(R.id.result);
	}

	final static String[] arAction = {
		"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
		"PDOWN", "PUP"
	};

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int pureaction = action & MotionEvent.ACTION_MASK;
		//int pid = (action & MotionEvent.ACTION_POINTER_ID_MASK) 
		//	>> MotionEvent.ACTION_POINTER_ID_SHIFT;
		// for 2.2 later
		int pid = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
			>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

		String info = "p" + pid + " " + arAction[pureaction];
		for (int i=0; i < event.getPointerCount();i++) {
			info += String.format(" p%d(%d,%d)", event.getPointerId(i),
					(int)event.getX(i), (int)event.getY(i));
		}
		
		if (event.getPointerCount() > 1) {
			int dx = (int)(event.getX(0) - event.getX(1));
			int dy = (int)(event.getY(0) - event.getY(1));
			int distance = (int)(Math.sqrt(dx * dx + dy * dy));
			info += " dis=" + distance;
		}
		
		AppendText(info);
		return true; 
	}

	void AppendText(String text) {
		if (arTouch.size() > 15) {
			arTouch.remove(0);
		}
		arTouch.add(text);
		StringBuilder result = new StringBuilder();
		for (String s : arTouch) {
			result.append(s);
			result.append("\n");
		}
		mResult.setText(result.toString());
	}
}