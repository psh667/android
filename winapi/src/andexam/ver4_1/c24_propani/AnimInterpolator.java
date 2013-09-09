package andexam.ver4_1.c24_propani;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import andexam.ver4_1.*;

public class AnimInterpolator extends Activity {
	AnimView mView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animinterpolator);

		LinearLayout root = (LinearLayout)findViewById(R.id.root);
		mView = new AnimView(this);
		root.addView(mView);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			mView.startAnim(new LinearInterpolator());
			break;
		case R.id.btn2:
			mView.startAnim(new AccelerateInterpolator());
			break;
		case R.id.btn3:
			mView.startAnim(new DecelerateInterpolator());
			break;
		case R.id.btn4:
			mView.startAnim(new BounceInterpolator());
			break;
		case R.id.btn5:
			mView.startAnim(new OvershootInterpolator());
			break;
		case R.id.btn6:
			mView.startAnim(new CycleInterpolator(3f));
			break;
		case R.id.btn7:
			mView.startAnim(new AnticipateInterpolator());
			break;
		case R.id.btn8:
			mView.startAnim(new MyInterpolator());
			break;
		}
	}
	
	class MyInterpolator implements TimeInterpolator {
		public float getInterpolation(float input) {
			return 1-input;
		}
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
		
		public void startAnim(TimeInterpolator inter) {
			ObjectAnimator anim = ObjectAnimator.ofInt(mBall, "x", 100, 400);
			anim.setDuration(4000);
			anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				public void onAnimationUpdate(ValueAnimator animation) {
					invalidate();
				}
			});
			anim.setInterpolator(inter);
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