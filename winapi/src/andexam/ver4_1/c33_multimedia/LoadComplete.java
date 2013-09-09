package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class LoadComplete extends Activity {
	SoundPool pool;
	int stream;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadcomplete);

		pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	}
	
	SoundPool.OnLoadCompleteListener mListener = 
		new SoundPool.OnLoadCompleteListener() {
		public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
			if (status == 0) {
				stream = soundPool.play(sampleId, 1, 1, 0, 0, 1);
			}
		}
	};

	public void mOnClick(View v) {
		MediaPlayer player;
		switch (v.getId()) {
		case R.id.load1:
			int song = pool.load(this, R.raw.goodtime, 1);
			pool.play(song, 1, 1, 0, 0, 1);
			break;
		case R.id.load2:
			pool.setOnLoadCompleteListener(mListener);
			pool.load(this, R.raw.goodtime, 1);
			break;
		case R.id.pause:
			//pool.pause(stream);
			pool.autoPause();
			break;
		case R.id.resume:
			//pool.resume(stream);
			pool.autoResume();
			break;
		}
	}
}
