package kr.co.infinity.PlayerTest;

import java.io.IOException;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

public class PlayerTest extends Activity {
	MediaPlayer mp = null;
	EditText edit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		edit = (EditText) findViewById(R.id.path);
	}

	public void startResAudio(View v) {
		mp = MediaPlayer.create(this, R.raw.old_pop);
		mp.start();
	}

	public void stopResAudio(View v) {
		if (mp != null) {
			mp.stop();
			mp.release();
		}
		mp = null;
	}

	public void startFileAudio(View v) {
		String file;
		file = edit.getText().toString();
		mp = new MediaPlayer();
		try {
			mp.setDataSource(file);
			mp.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mp.start();
	}

	public void stopFileAudio(View v) {
		if (mp != null) {
			mp.stop();
			mp.release();
		}
		mp = null;
	}
}