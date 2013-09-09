package com.msi.manning.chapter10.MediaPlayerExample;

import android.app.Activity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;
import android.widget.Button; 

public class MediaPlayerActvity extends Activity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        Button mybutton = (Button) findViewById(R.id.playsong);
        mybutton.setOnClickListener(new Button.OnClickListener() {
               public void onClick(View v) {
                   MediaPlayer mp = MediaPlayer.create(MediaPlayerActvity.this, R.raw.halotheme);
                  mp.start();
                    mp.setOnCompletionListener(new OnCompletionListener(){
                         public void onCompletion(MediaPlayer arg0) {
                   
                         }
                    }
                    );
               }
        }
        );
    }
}