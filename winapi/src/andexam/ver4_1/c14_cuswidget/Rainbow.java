package andexam.ver4_1.c14_cuswidget;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Shader.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class Rainbow extends Activity {
	RainbowProgress mProgress;
	Handler mHandler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rainbow);

		mProgress = (RainbowProgress)findViewById(R.id.progress);

		Button btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mProgress.getPos() == 0) {
					mProgress.setPos(0);
					mHandler.sendEmptyMessage(0);
				}
			}
		});

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				int Pos;
				Pos = mProgress.getPos();
				if (Pos < mProgress.getMax()) {
					mProgress.setPos(Pos+1);
					mHandler.sendEmptyMessageDelayed(0,100);
				} else {
					Toast.makeText(Rainbow.this, "Completed", 0).show();
					mProgress.setPos(0);
				}
			}
		};
	}
}

class RainbowProgress extends View {
	int mMax;
	int mPos;
	int mProgHeight;
	LinearGradient mShader;

	public RainbowProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RainbowProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RainbowProgress(Context context) {
		super(context);
		init();
	}

	void init() {
		mMax = 100;
		mPos = 0;
	}

	void setMax(int aMax) { 
		if (aMax > 0) {
			mMax = aMax;
			invalidate();
		}
	}

	int getMax() { return mMax; }

	void setPos(int aPos) {
		if (aPos < 0 || aPos > mMax) {
			return;
		}
		mPos = aPos; 
		invalidate();
	}

	int getPos() { return mPos; }

	protected void onDraw(Canvas canvas) {
		if (mShader == null) {
			mProgHeight = getHeight() - getPaddingTop() - getPaddingBottom();
			int[] colors = { Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE };
			mShader = new LinearGradient(0,0,0,mProgHeight,
					colors, null, TileMode.CLAMP);
		}
		
		RectF rt = new RectF();
		rt.left = getPaddingLeft();
		rt.right = getWidth() - getPaddingRight();
		rt.bottom = getPaddingTop() + mProgHeight;
		rt.top = rt.bottom - mProgHeight * mPos / mMax;

		Paint fillpnt = new Paint();
		fillpnt.setShader(mShader);
		canvas.drawRect(rt, fillpnt);

		rt.top = getPaddingTop();
		Paint outpnt = new Paint();
		outpnt.setColor(Color.WHITE);
		outpnt.setStyle(Paint.Style.STROKE);
		canvas.drawRect(rt, outpnt);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int Width = 48, Height = 200;

		switch (MeasureSpec.getMode(widthMeasureSpec)) {
		case MeasureSpec.AT_MOST:
			Width = Math.min(MeasureSpec.getSize(widthMeasureSpec), Width);
			break;
		case MeasureSpec.EXACTLY:
			Width = MeasureSpec.getSize(widthMeasureSpec);
			break;
		}

		switch (MeasureSpec.getMode(heightMeasureSpec)) {
		case MeasureSpec.AT_MOST:
			Height = Math.min(MeasureSpec.getSize(heightMeasureSpec), Height);
			break;
		case MeasureSpec.EXACTLY:
			Height = MeasureSpec.getSize(heightMeasureSpec);
			break;
		}

		setMeasuredDimension(Width, Height);
	}
}
