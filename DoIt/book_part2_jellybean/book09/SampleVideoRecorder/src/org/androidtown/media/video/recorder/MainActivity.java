package org.androidtown.media.video.recorder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * 동영상을 녹화하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {
	public static final String TAG = "SampleVideoRecorderActivity";

	private static String EXTERNAL_STORAGE_PATH = "";
    private static String RECORDED_FILE = "video_recorded";
    private static int fileIndex = 0;
    private static String filename = "";

    MediaPlayer player;
    MediaRecorder recorder;

    private Camera camera = null;
    SurfaceHolder holder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check external storage
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
        	Log.d(TAG, "External Storage Media is not mounted.");
        } else {
        	EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        // create a SurfaceView instance and add it to the layout
        SurfaceView surface = new SurfaceView(this);
        holder = surface.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        FrameLayout frame = (FrameLayout) findViewById(R.id.videoLayout);
        frame.addView(surface);


        Button recordBtn = (Button) findViewById(R.id.recordBtn);
        Button recordStopBtn = (Button) findViewById(R.id.recordStopBtn);
        Button playBtn = (Button) findViewById(R.id.playBtn);
        Button playStopBtn = (Button) findViewById(R.id.playStopBtn);

        recordBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                	if (recorder == null) {
                		recorder = new MediaRecorder();
                    }

                	recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                	recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                	recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                	recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                	recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

                	filename = createFilename();
                	Log.d(TAG, "current filename : " + filename);
                	recorder.setOutputFile(filename);

                	recorder.setPreviewDisplay(holder.getSurface());
                	recorder.prepare();
                	recorder.start();

                } catch (Exception ex) {
                    Log.e(TAG, "Exception : ", ex);

                    recorder.release();
                    recorder = null;
                }
            }
        });

        recordStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recorder == null)
                    return;

                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder = null;

                ContentValues values = new ContentValues(10);

                values.put(MediaStore.MediaColumns.TITLE, "RecordedVideo");
                values.put(MediaStore.Audio.Media.ALBUM, "Video Album");
                values.put(MediaStore.Audio.Media.ARTIST, "Mike");
                values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Video");
                values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
                values.put(MediaStore.Audio.Media.DATA, filename);

                Uri videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
                if (videoUri == null) {
                    Log.d("SampleVideoRecorder", "Video insert failed.");
                    return;
                }

                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));

            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player == null) {
                    player = new MediaPlayer();
                }

                try {
                    player.setDataSource(filename);
                    player.setDisplay(holder);

                    player.prepare();
                    player.start();
                } catch (Exception e) {
                    Log.e(TAG, "Video play failed.", e);
                }
            }
        });


        playStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player == null)
                	return;

                player.stop();
                player.release();
                player = null;
            }
        });
    }


    private String createFilename() {
    	fileIndex++;

    	String newFilename = "";
    	if (EXTERNAL_STORAGE_PATH == null || EXTERNAL_STORAGE_PATH.equals("")) {
    		// use internal memory
    		newFilename = RECORDED_FILE + fileIndex + ".mp4";
    	} else {
    		newFilename = EXTERNAL_STORAGE_PATH + "/" + RECORDED_FILE + fileIndex + ".mp4";
    	}

    	return newFilename;
    }



    protected void onPause() {
    	if (camera != null) {
    		camera.release();
    		camera = null;
        }

        if (recorder != null) {
        	recorder.release();
        	recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }

        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
