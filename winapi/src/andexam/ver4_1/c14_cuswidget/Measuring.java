package andexam.ver4_1.c14_cuswidget;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class Measuring extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.measuring);

		final MeasView meas = (MeasView)findViewById(R.id.meas);
		final TextView text = (TextView)findViewById(R.id.text);
		text.postDelayed(new Runnable() {
			public void run() {
				text.setText(meas.mResult);
			}
		}, 100);
	}
}

class MeasView extends View {
	String mResult = ""; 

	public MeasView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MeasView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MeasView(Context context) {
		super(context);
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.RED);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int wMode, hMode;
		int wSpec, hSpec;
		int Width, Height;

		Width = 150;
		Height = 80;
		
		wMode = MeasureSpec.getMode(widthMeasureSpec);
		wSpec = MeasureSpec.getSize(widthMeasureSpec);
		hMode = MeasureSpec.getMode(heightMeasureSpec);
		hSpec = MeasureSpec.getSize(heightMeasureSpec);

		switch (wMode) {
		case MeasureSpec.AT_MOST:
			Width = Math.min(wSpec, Width);
			break;
		case MeasureSpec.EXACTLY:
			Width = wSpec;
			break;
		case MeasureSpec.UNSPECIFIED:
			break;
		}

		switch (hMode) {
		case MeasureSpec.AT_MOST:
			Height = Math.min(hSpec, Height);
			break;
		case MeasureSpec.EXACTLY:
			Height = hSpec;
			break;
		case MeasureSpec.UNSPECIFIED:
			break;
		}

		setMeasuredDimension(Width, Height);

		mResult += (Spec2String(widthMeasureSpec) + ", " 
				+ Spec2String(heightMeasureSpec) + 
				" -> (" + Width + "," + Height + ")\n");
	}
	
	String Spec2String(int Spec) {
		String str = "";
		
		switch (MeasureSpec.getMode(Spec)) {
		case MeasureSpec.AT_MOST:
			str = "AT_MOST";
			break;
		case MeasureSpec.EXACTLY:
			str = "EXACTLY";
			break;
		default:
			str = "UNSPECIFIED";
			break;
		}
		
		str += " " + MeasureSpec.getSize(Spec);
		return str;
	}
}


