package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class AnimAttr extends Activity {
	ImageView mAnimTarget;
	CheckBox mChkBefore;
	CheckBox mChkAfter;
	CheckBox mChkRepeat;
	CheckBox mChkReverse;
	RadioGroup mRadInter;
	Spinner mSpinInter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animattr);

		mAnimTarget = (ImageView)findViewById(R.id.animtarget);
		mChkBefore = (CheckBox)findViewById(R.id.btnfillbefore);
		mChkAfter = (CheckBox)findViewById(R.id.btnfillafter);
		mChkRepeat = (CheckBox)findViewById(R.id.btnrepeat);
		mChkReverse = (CheckBox)findViewById(R.id.btnreverse);

		mSpinInter = (Spinner)findViewById(R.id.spininter);
		mSpinInter.setPrompt("Select Interpolator");

		ArrayAdapter<CharSequence> adspin = ArrayAdapter.createFromResource(
				this, R.array.interpolator,	android.R.layout.simple_spinner_item);
		adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinInter.setAdapter(adspin);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnstart:
			TranslateAnimation trans = new TranslateAnimation(0, 250, 0, 0);
			trans.setDuration(2000);
			trans.setFillBefore(mChkBefore.isChecked());
			trans.setFillAfter(mChkAfter.isChecked());
			if (mChkRepeat.isChecked()) {
				trans.setRepeatCount(1);
				if (mChkReverse.isChecked()) {
					trans.setRepeatMode(Animation.REVERSE);
				}
			}
			switch (mSpinInter.getSelectedItemPosition()) {
			case 0:
				trans.setInterpolator(new LinearInterpolator());
				break;
			case 1:
				trans.setInterpolator(new AccelerateInterpolator());
				break;
			case 2:
				trans.setInterpolator(new DecelerateInterpolator());
				break;
			case 3:
				trans.setInterpolator(new AccelerateDecelerateInterpolator());
				break;
			case 4:
				trans.setInterpolator(new AnticipateInterpolator());
				break;
			case 5:
				trans.setInterpolator(new BounceInterpolator());
				break;
			case 6:
				trans.setInterpolator(new CycleInterpolator(0.5f));
				break;
			case 7:
				trans.setInterpolator(new OvershootInterpolator());
				break;
			case 8:
				trans.setInterpolator(new AnticipateOvershootInterpolator());
				break;
			}
			mAnimTarget.startAnimation(trans);
			break;
		}
	}
}
