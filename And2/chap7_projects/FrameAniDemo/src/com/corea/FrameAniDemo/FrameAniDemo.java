package com.corea.FrameAniDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;

public class FrameAniDemo extends Activity {
    AnimationDrawable frameAni = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        
        // Handle 시작 버튼
        final Button onButton = (Button) findViewById(R.id.ButtonStart);
        onButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            startAnimation();
	        }
        });
        // Handle 중지 버튼
        final Button offButton = (Button) findViewById(R.id.ButtonStop);
        offButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            stopAnimation();
        }
        });        
    }
    private void startAnimation()
    {
         ImageView imageView = (ImageView)findViewById(R.id.ImageView01);
         
        BitmapDrawable frame[] = { (BitmapDrawable)getResources().getDrawable(R.drawable.moon01),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon02),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon03),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon04),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon05),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon06),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon07),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon08),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon09),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon10),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon11),
        		 					(BitmapDrawable)getResources().getDrawable(R.drawable.moon12)};

         int Duration = 400;
         int i;
         frameAni = new AnimationDrawable();

         // loop continuously
         frameAni.setOneShot(false);
         for(i=0; i<12; i++) {
        	 frameAni.addFrame(frame[i], Duration);
         }
         
         // Get the background
         imageView.setBackgroundDrawable(frameAni);         
         frameAni.setVisible(true,true);
         frameAni.start();
    }
    private void stopAnimation()
    {
         frameAni.stop();
         frameAni.setVisible(false,false);
    }
}
    
