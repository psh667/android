package andexam.ver4_1.c24_propani;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class BallAnim extends Activity {
	AnimView mView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ballanim);
		
		LinearLayout root = (LinearLayout)findViewById(R.id.root);
		mView = new AnimView(this);
		root.addView(mView);
	}
	
	public void mOnClick(View v) {
		mView.startAnim();
	}
	
	public class AnimView extends View {
		Ball mBall;
		public AnimView(Context context) {
			super(context);
			mBall = new Ball();
			mBall.setX(100);
			mBall.setY(50);
			mBall.setRad(20);
		}
		
		public void startAnim() {
			ObjectAnimator anim = ObjectAnimator.ofInt(mBall, "x", 100, 400);
			anim.setDuration(2000);
			anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				public void onAnimationUpdate(ValueAnimator animation) {
					invalidate();
				}
			});
			anim.start();
		}

		protected void onDraw(Canvas canvas) {
			Paint pnt = new Paint();
			pnt.setColor(mBall.getColor());
			pnt.setAntiAlias(true);
			canvas.drawCircle(mBall.getX(), mBall.getY(), mBall.getRad(), pnt);
		}
	}
}

class Ball {
	int x = 0, y = 0;
	int rad = 20;
	int color = Color.YELLOW;
	
	void setX(int ax) { x = ax; }
	int getX() { return x; }
	void setY(int ay) { y = ay; }
	int getY() { return y; }
	void setRad(int arad) { rad = arad; }
	int getRad() { return rad; }
	void setColor(int acolor) { color = acolor; }
	int getColor() { return color; }
}

