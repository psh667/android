package andexam.ver4_1.c07_output;

import andexam.ver4_1.*;
import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Beep extends Activity {
	SoundPool mPool;
	int mDdok;
	AudioManager mAm;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beep);
		
		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mDdok = mPool.load(this, R.raw.ddok, 1);
		mAm = (AudioManager)getSystemService(AUDIO_SERVICE);

		findViewById(R.id.play1).setOnClickListener(mClickListener);
		findViewById(R.id.play2).setOnClickListener(mClickListener);
	}

	Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			MediaPlayer player;
			switch (v.getId()) {
			case R.id.play1:
				mPool.play(mDdok, 1, 1, 0, 0, 1);
				break;
			case R.id.play2:
				mAm.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
				break;
			}
		}
	};
}
	
