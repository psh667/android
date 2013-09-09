package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class AnimCustom extends Activity {
	ImageView mAnimTarget;
	Button mBtnAnim;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animcustom);

		mAnimTarget = (ImageView)findViewById(R.id.animtarget);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnskew:
			mAnimTarget.startAnimation(new SkewAnim());
			break;
		case R.id.btncamera:
			mAnimTarget.startAnimation(new CameraAnim());
			break;
		}
	}

	class SkewAnim extends Animation {
		public void initialize(int width, int height, int parentWidth, int parentHeight) {
			super.initialize(width, height, parentWidth, parentHeight);
			setDuration(1000);
			setInterpolator(new LinearInterpolator());
		}
	
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			Matrix matrix = t.getMatrix();
			matrix.setSkew(0.5f * interpolatedTime, 0);
		}
	}

	class CameraAnim extends Animation {
		float cx, cy;
		public void initialize(int width, int height, int parentWidth, int parentHeight) {
			super.initialize(width, height, parentWidth, parentHeight);
			cx = width / 2;
			cy = height / 2;
			setDuration(1000);
			setInterpolator(new LinearInterpolator());
		}

		protected void applyTransformation(float interpolatedTime, Transformation t) {
			Camera cam = new Camera();
			cam.rotateY(360 * interpolatedTime);
			Matrix matrix = t.getMatrix();
			cam.getMatrix(matrix);
			matrix.preTranslate(-cx, -cy);
			matrix.postTranslate(cx, cy);
		}
	}
}
