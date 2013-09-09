package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class FrameAni extends Activity {
	AnimationDrawable mAni;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frameani);

		ImageView img = (ImageView)findViewById(R.id.babydrum);
		mAni = (AnimationDrawable)img.getBackground();

		img.post(new Runnable() {
			public void run() {
				mAni.start();
			}
		});
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			mAni.start();
			break;
		case R.id.stop:
			mAni.stop();
			break;
		}
	}
}
