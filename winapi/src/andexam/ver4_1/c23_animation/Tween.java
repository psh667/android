package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class Tween extends Activity {
	LinearLayout mLinear;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tween);

		mLinear = (LinearLayout)findViewById(R.id.linear);
	}
	
	public void mOnClick(View v) {
		Animation ani = null;
		switch (v.getId()) {
		case R.id.translate:
			ani = AnimationUtils.loadAnimation(this, R.anim.translate);
			break;
		case R.id.rotate:
			ani = AnimationUtils.loadAnimation(this, R.anim.rotate);
			break;
		case R.id.scale:
			ani = AnimationUtils.loadAnimation(this, R.anim.scale);
			break;
		case R.id.alpha:
			ani = AnimationUtils.loadAnimation(this, R.anim.alpha);
			break;
		case R.id.set:
			ani = AnimationUtils.loadAnimation(this, R.anim.set);
			break;
		}
		mLinear.startAnimation(ani);
	}
}
