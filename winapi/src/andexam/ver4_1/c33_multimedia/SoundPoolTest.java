package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class SoundPoolTest extends Activity {
	SoundPool pool;
	int ddok;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soundpooltest);
		
		pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		ddok = pool.load(this, R.raw.ddok, 1);
	}

	public void mOnClick(View v) {
		MediaPlayer player;
		switch (v.getId()) {
		case R.id.play1:
			pool.play(ddok, 1, 1, 0, 0, 1);
			break;
		case R.id.play2:
			pool.play(ddok, 0.5f, 0.5f, 0, 0, 1);
			break;
		case R.id.play3:
			pool.play(ddok, 1, 1, 0, 2, 1);
			break;
		case R.id.play4:
			pool.play(ddok, 1, 1, 0, 0, 0.5f);
			break;
		}
	}
}