package kr.co.company.bouncingballs;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

class MyView extends View {

	float mX = 30;

	public MyView(Context context) {
		super(context);
		setBackgroundColor(Color.YELLOW);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return false;
		ValueAnimator moveAnim = ValueAnimator.ofFloat(30, 300);
		moveAnim.setDuration(2000);
		moveAnim.setInterpolator(new AccelerateInterpolator());
		moveAnim.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animation) {
				mX = (Float) animation.getAnimatedValue();
				invalidate();
			}
		});
		moveAnim.start();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		canvas.drawCircle(mX, 200, 30, paint);
	}
}

public class BouncingBallsActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView w = new MyView(this);
		setContentView(w);
	}

}