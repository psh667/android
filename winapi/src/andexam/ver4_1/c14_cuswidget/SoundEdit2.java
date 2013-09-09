package andexam.ver4_1.c14_cuswidget;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.media.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public class SoundEdit2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soundedit2);
	}
}

class SoundEditWidget2 extends EditText {
	SoundPool mPool = null;
	int mClick1, mClick2;
	int mSound;
	float mVolume = 1.0f;
	float mSpeed = 1.0f;
	
	public SoundEditWidget2(Context context) {
		super(context);
		init(context, null);
	}

	public SoundEditWidget2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public SoundEditWidget2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	
	void init(Context context, AttributeSet attrs) {
		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mClick1 = mPool.load(context, R.raw.click, 1);
		mClick2 = mPool.load(context, R.raw.click2, 1);
		mSound = mClick1;
		
		if (attrs != null) {
			TypedArray ar = context.obtainStyledAttributes(attrs, 
					R.styleable.SoundEditWidget2);
			mVolume = ar.getFloat(R.styleable.SoundEditWidget2_volume, 1.0f);
			mSpeed = ar.getFloat(R.styleable.SoundEditWidget2_speed, 1.0f);
			mSound = ar.getInt(R.styleable.SoundEditWidget2_sound, mClick1);
			ar.recycle();
		}
	}

	protected void onTextChanged(CharSequence text, int start, int before, int after) {
		if (mPool != null) {
			mPool.play(mSound, mVolume, mVolume, 0, 0, mSpeed);
		}
	}
}
