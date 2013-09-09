package kr.co.infinit.AudioPlayerTest1;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioPlayerTest1 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	Button playButton = (Button) findViewById(R.id.play);
		playButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent =new Intent(Intent.ACTION_VIEW);
				Uri uri = Uri.parse("file:///sdcard/old_pop.mp3");
				intent.setDataAndType(uri, "audio/mp3");
				startActivity(intent);
			}
		});
    }
}