package andexam.ver4_1.c14_cuswidget;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public class SoundEdit extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soundedit);
	}
}

class SoundEditWidget extends EditText {
	SoundPool mPool = null;
	int mClick;
	
	public SoundEditWidget(Context context) {
		super(context);
		init(context);
	}
	
	public SoundEditWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public SoundEditWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	void init(Context context) {
		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mClick = mPool.load(context, R.raw.click, 1);
	}

	protected void onTextChanged(CharSequence text, int start, int before, int after) {
		if (mPool != null) {
			mPool.play(mClick, 1, 1, 0, 0, 1);
		}
	}
}
