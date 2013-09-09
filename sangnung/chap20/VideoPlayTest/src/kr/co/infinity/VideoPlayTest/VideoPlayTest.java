package kr.co.infinity.VideoPlayTest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        VideoView videoview = (VideoView)this.findViewById(R.id.videoview);
        MediaController mc = new MediaController(this);
        videoview.setMediaController(mc);
		String folder = Environment.getExternalStorageDirectory().getAbsolutePath();

		//videoview.setVideoURI(Uri.parse(....));
		videoview.setVideoPath(folder + "/dog.mp4");
        videoview.requestFocus();
        videoview.start();
    }
}
 