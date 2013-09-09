package andexam.ver4_1.c24_propani;

import java.util.*;

import android.animation.*;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class XmlAnim extends Activity {
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
			mBall.setX(100);
			mBall.setY(50);
			mBall.setRad(20);

			AnimatorSet set = (AnimatorSet)AnimatorInflater.loadAnimator(
					XmlAnim.this, R.animator.rightdownlarge);
			set.setTarget(mBall);
			ArrayList<Animator> childs = set.getChildAnimations();
			((ObjectAnimator)childs.get(0)).addUpdateListener(mInvalator);
			((ObjectAnimator)childs.get(1)).addUpdateListener(mInvalator);
			((ObjectAnimator)childs.get(2)).addUpdateListener(mInvalator);
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
