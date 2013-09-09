package kr.co.infinity.AudioRecorderTest;

import java.io.IOException;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AudioRecorderTest extends Activity {
	private static final String LOG_TAG = "AudioRecorderTest";
	private static String filename = null;

	Button play, record;
	private MediaRecorder recorder = null;
	private MediaPlayer player = null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		filename = Environment.getExternalStorageDirectory().getAbsolutePath();
		filename += "/test.3gp";
		play = (Button) findViewById(R.id.play);
		record = (Button) findViewById(R.id.record);

		play.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (player == null) {
					player = new MediaPlayer();
					try {
						player.setDataSource(filename);
						player.prepare();
						player.start();
					} catch (IOException e) {
						Log.e(LOG_TAG, "prepare() failed");
					}
					play.setText("재생 중지");
				} else {
					player.release();
					player = null;
					play.setText("재생 시작");
				}
			}
		});

		record.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (recorder == null) {
					recorder = new MediaRecorder();
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					recorder.setOutputFile(filename);
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

					try {
						recorder.prepare();
					} catch (IOException e) {
						Log.e(LOG_TAG, "prepare() failed");
					}

					recorder.start();
					record.setText("녹음 중지");
				} else {
					recorder.stop();
					recorder.release();
					recorder = null;
					record.setText("녹음 시작");
				}
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if (recorder != null) {
			recorder.release();
			recorder = null;
		}

		if (player != null) {
			player.release();
			player = null;
		}
	}
}
