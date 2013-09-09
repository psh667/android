package andexam.ver4_1.c24_propani;

import android.animation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class ObjectAnimatorTest2 extends Activity {
	Button mBtn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.valueanimatortest);
		
		mBtn=(Button)findViewById(R.id.btn);
	}
	
	public void mOnClick(View v) {
		ObjectAnimator anim = ObjectAnimator.ofInt(mBtn, "width", 400);
		anim.setInterpolator(null);
		anim.setRepeatCount(1);
		anim.setRepeatMode(ValueAnimator.REVERSE);
		anim.setDuration(2000);
		anim.start();
	}	
}