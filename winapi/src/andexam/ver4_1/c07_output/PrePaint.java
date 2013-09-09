package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class PrePaint extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView vw = new MyView(this);
		setContentView(vw);
	}

	class MyView extends View {
		Paint mPnt;
		public MyView(Context context) {
			super(context);

			mPnt = new Paint();
			mPnt.setAntiAlias(true);
			mPnt.setColor(Color.BLUE);
			mPnt.setStrokeWidth(10);
			mPnt.setStyle(Paint.Style.STROKE);
			mPnt.setStrokeJoin(Paint.Join.BEVEL);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);
			canvas.drawRect(10,10,300,100,mPnt);
		}
	}
}