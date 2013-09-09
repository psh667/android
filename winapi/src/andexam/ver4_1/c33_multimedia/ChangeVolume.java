package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ChangeVolume extends Activity {
	MediaPlayer mPlayer;
	AudioManager mAm;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changevolume);

		mPlayer = new MediaPlayer();
		try {
			String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
			mPlayer.setDataSource(sd + "/eagle5.mp3");
			mPlayer.prepare();
		} catch (Exception e) {;}

		mAm = (AudioManager)getSystemService(AUDIO_SERVICE);
		SeekBar seek = (SeekBar)findViewById(R.id.seekvolume);
		seek.setMax(mAm.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		seek.setProgress(mAm.getStreamVolume(AudioManager.STREAM_MUSIC));
		seek.setOnSeekBarChangeListener(mOnSeek);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnplay:
			if (mPlayer.isPlaying()) {
				mPlayer.pause();
			} else {
				mPlayer.start();
			}
			break;
		case R.id.btnvoldown:
			mAm.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI | 
					AudioManager.FLAG_ALLOW_RINGER_MODES | 
					AudioManager.FLAG_PLAY_SOUND |
					AudioManager.FLAG_VIBRATE );
			break;
		case R.id.btnvolup:
			mAm.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI | 
					AudioManager.FLAG_ALLOW_RINGER_MODES | 
					AudioManager.FLAG_PLAY_SOUND |
					AudioManager.FLAG_VIBRATE );
			break;
		case R.id.btnring:
			switch (mAm.getRingerMode()) {
			case AudioManager.RINGER_MODE_NORMAL:
				mAm.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				break;
			case AudioManager.RINGER_MODE_SILENT:
				mAm.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				break;
			case AudioManager.RINGER_MODE_VIBRATE:
				mAm.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				break;
			}
			break;
		case R.id.btneffect1:
			mAm.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
			break;
		case R.id.btneffect2:
			mAm.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
			break;
		case R.id.btneffect3:
			mAm.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
			break;
		}
	};

	SeekBar.OnSeekBarChangeListener mOnSeek = 
		new SeekBar.OnSeekBarChangeListener() {
		public void onProgressChanged(SeekBar seekBar, 
				int progress, boolean fromUser) {
			mAm.setStreamVolume(AudioManager.STREAM_MUSIC,
					progress, 0);
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	};	

	public void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
}
