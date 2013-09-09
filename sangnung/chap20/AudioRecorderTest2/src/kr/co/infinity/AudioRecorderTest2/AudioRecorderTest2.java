package kr.co.infinity.AudioRecorderTest2;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioRecorderTest2 extends Activity {
	Uri recordedAudio;
	String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button recordButton = (Button) findViewById(R.id.record);
		Button playButton = (Button) findViewById(R.id.play);
		recordButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(
						"android.provider.MediaStore.RECORD_SOUND");
				startActivityForResult(i, 0);
			}
		});
		playButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(path)),
						"audio/3gpp");
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {
				Uri recordedAudio = data.getData();
				Cursor c = getContentResolver().query(recordedAudio, null,
						null, null, null);
				c.moveToNext();
				path = c.getString(c
						.getColumnIndex(MediaStore.MediaColumns.DATA));
			}
		}
	}
}