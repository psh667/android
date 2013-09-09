package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class AnimSet extends Activity {
	ImageView mAnimTarget;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animset);

		mAnimTarget = (ImageView)findViewById(R.id.animtarget);
	}

	public void mOnClick(View v) {
		AnimationSet ani = null;
		switch (v.getId()) {
		case R.id.btnstart:
			ani = new AnimationSet(true);
			ani.setInterpolator(new LinearInterpolator());
			
			TranslateAnimation trans = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 1, 
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
			trans.setDuration(3000);
			ani.addAnimation(trans);
			
			AlphaAnimation alpha = new AlphaAnimation(1, 0);
			alpha.setDuration(300);
			alpha.setStartOffset(500);
			alpha.setRepeatCount(4);
			alpha.setRepeatMode(Animation.REVERSE);
			ani.addAnimation(alpha);
			break;
		}
		mAnimTarget.startAnimation(ani);
	}
}
