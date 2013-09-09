package andexam.ver4_1.c24_propani;

import android.animation.*;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class AnimatorSetTest extends Activity {
	AnimView mView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animatorsettest);

		LinearLayout root = (LinearLayout)findViewById(R.id.root);
		mView = new AnimView(this);
		root.addView(mView);
	}
	
	public void mOnClick(View v) {
		mView.startAnim(v.getId());
	}
	
	public class AnimView extends View {
		Ball mBall;
		ObjectAnimator mToRight;
		ObjectAnimator mToDown;
		ObjectAnimator mEnLarge;
		public AnimView(Context context) {
			super(context);
			mBall = new Ball();
			mBall.setX(100);
			mBall.setY(50);
			mBall.setRad(20);

			mToRight = ObjectAnimator.ofInt(mBall, "x", 100, 400);
			mToRight.setDuration(2000);
			mToRight.addUpdateListener(mInvalator);

			mToDown = ObjectAnimator.ofInt(mBall, "y", 50, 300);
			mToDown.setDuration(2000);
			mToDown.addUpdateListener(mInvalator);
			
			mEnLarge = ObjectAnimator.ofInt(mBall, "rad", 20, 40);
			mEnLarge.setDuration(2000);
			mEnLarge.addUpdateListener(mInvalator);
		}
		
		public void startAnim(int id) {
			mBall.setX(100);
			mBall.setY(50);
			mBall.setRad(20);

			AnimatorSet set = new AnimatorSet();
			switch (id) {
			case R.id.btn1:
				set.playSequentially(mToRight, mToDown, mEnLarge);

				//set.play(mToDown).after(mToRight).before(mEnLarge);
				
				//set.play(mToRight).before(mToDown);
				//set.play(mToDown).before(mEnLarge);
				break;
			case R.id.btn2:
				set.playSequentially(mToDown, mToRight, mEnLarge);

				//set.play(mToRight).after(mToDown).before(mEnLarge);
				break;
			case R.id.btn3:
				set.playTogether(mToRight, mToDown, mEnLarge);
				//set.play(mToRight).with(mEnLarge).with(mToDown);
				break;
			case R.id.btn4:
				set.play(mToRight).with(mEnLarge).before(mToDown);
				break;
			}
			set.start();
		}
		
		AnimatorUpdateListener mInvalator = new AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animation) {
				invalidate();
			}
		};
	
		protected void onDraw(Canvas canvas) {
			Paint pnt = new Paint();
			pnt.setColor(mBall.getColor());
			pnt.setAntiAlias(true);
			canvas.drawCircle(mBall.getX(), mBall.getY(), mBall.getRad(), pnt);
		}
	}
}
