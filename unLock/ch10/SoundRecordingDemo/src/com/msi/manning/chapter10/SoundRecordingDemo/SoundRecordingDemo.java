package com.msi.manning.chapter10.SoundRecordingDemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class SoundRecordingDemo extends Activity  {
  MediaRecorder mRecorder;
  File mSampleFile = null;
  static final String SAMPLE_PREFIX = "recording";
  static final String SAMPLE_EXTENSION = ".mp3"; 
  private static final String TAG="SoundRecordingDemo";

  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mRecorder = new MediaRecorder();
     
        Button startRecording = (Button)findViewById(R.id.startrecording);
        Button stopRecording = (Button)findViewById(R.id.stoprecording);
       
        startRecording.setOnClickListener(new View.OnClickListener(){
          public void onClick(View v) {
            startRecording();   
          }
        });
        
        stopRecording.setOnClickListener(new View.OnClickListener(){
          public void onClick(View v) {
            stopRecording();
            addToDB();
          }
          
        });
    }

    protected void addToDB() {
      ContentValues values = new ContentValues(3);
      long current = System.currentTimeMillis();
          
      values.put(MediaStore.Audio.Media.TITLE, "test_audio");
      values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
      values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");
      values.put(MediaStore.Audio.Media.DATA, mSampleFile.getAbsolutePath());
      ContentResolver contentResolver = getContentResolver();
      
      Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
      Uri newUri = contentResolver.insert(base, values);

      sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }

    protected void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mSampleFile.getAbsolutePath());
        mRecorder.prepare();
        mRecorder.start();

        if (mSampleFile == null) {
	          File sampleDir = Environment.getExternalStorageDirectory();
	        
	          try { mSampleFile = File.createTempFile(SAMPLE_PREFIX, SAMPLE_EXTENSION, sampleDir);
	          } catch (IOException e) {
	              Log.e(TAG,"sdcard access error");
	              return;
	          }
        }
    }

    protected void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
      }
}