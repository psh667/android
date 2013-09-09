package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class AnimationTest extends Activity {
	ImageView mAnimTarget;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animationtest);

		mAnimTarget = (ImageView)findViewById(R.id.animtarget);
	}

	public void mOnClick(View v) {
		Animation anim = null;
		switch (v.getId()) {
		case R.id.btntrans1:
			anim = new TranslateAnimation(0, 200, 0, 0);
			break;
		case R.id.btntrans2:
			anim = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, 
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
			break;
		case R.id.btntrans3:
			anim = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 1, 
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
			break;
		case R.id.btnrotate1:
			anim = new RotateAnimation(0, -180);
			break;
		case R.id.btnrotate2:
			anim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
			break;
		case R.id.btnrotate3:
			anim = new RotateAnimation(0, 90, Animation.RELATIVE_TO_PARENT, 0.5f, 
				Animation.RELATIVE_TO_PARENT, 1.0f);
			break;
		case R.id.btnscale1:
			anim = new ScaleAnimation(0, 1, 0, 1);
			break;
		case R.id.btnscale2:
			anim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
			break;
		case R.id.btnscale3:
			anim = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
			break;
		case R.id.btnalpha1:
			anim = new AlphaAnimation(0, 1);
			break;
		case R.id.btnalpha2:
			anim = new AlphaAnimation(1, 0);
			break;
		}
		anim.setDuration(1000);
		mAnimTarget.startAnimation(anim);
	}
}
