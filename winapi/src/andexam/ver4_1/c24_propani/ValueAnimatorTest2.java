package andexam.ver4_1.c24_propani;

import android.animation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class ValueAnimatorTest2 extends Activity {
	Button mBtn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.valueanimatortest);
		
		mBtn=(Button)findViewById(R.id.btn);
	}
	
	public void mOnClick(View v) {
		ValueAnimator anim = ValueAnimator.ofInt(200, 400);
		anim.setDuration(2000);
		anim.setInterpolator(null);
		anim.setRepeatCount(1);
		anim.setRepeatMode(ValueAnimator.REVERSE);
		//* 간략한 방법
		anim.addListener(new AnimatorListenerAdapter() {
			public void onAnimationStart(Animator animation) {
				Toast.makeText(ValueAnimatorTest2.this, "start", 0).show();
			}
			public void onAnimationEnd(Animator animation) {
				Toast.makeText(ValueAnimatorTest2.this, "end", 0).show();
			}
		});
		//*/
		/* 인터페이스를 구현하는 원래 방법
		anim.addListener(new Animator.AnimatorListener() {
			public void onAnimationStart(Animator animation) {
				Toast.makeText(ValueAnimatorTest2.this, "start", 0).show();
			}
			public void onAnimationEnd(Animator animation) {
				Toast.makeText(ValueAnimatorTest2.this, "end", 0).show();
			}
			public void onAnimationCancel(Animator animation) {
			}
			public void onAnimationRepeat(Animator animation) {
			}
		});
		//*/
		
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animation) {
				mBtn.setWidth((Integer)animation.getAnimatedValue());
			}
		});
		anim.start();
	}	
}