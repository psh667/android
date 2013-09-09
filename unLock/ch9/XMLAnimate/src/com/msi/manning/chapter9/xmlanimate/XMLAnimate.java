package com.msi.manning.chapter9.xmlanimate;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;

public class XMLAnimate extends Activity 
{
  
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        ImageView img = (ImageView)findViewById(R.id.simple_anim);
        img.setBackgroundResource(R.anim.simple_animation);

        
        MyAnimationRoutine mar = new MyAnimationRoutine();
        MyAnimationRoutine2 mar2 = new MyAnimationRoutine2();
        
        Timer t = new Timer(false);
        t.schedule(mar, 100);
        Timer t2 = new Timer(false);
        t2.schedule(mar2, 5000);
        
    }
    class MyAnimationRoutine extends TimerTask
    {
    	
    	public void run()
    	{
        	ImageView img = (ImageView)findViewById(R.id.simple_anim);
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
            frameAnimation.start();
    	}
    }


    class MyAnimationRoutine2 extends TimerTask
    {
    	   	
    	
    	public void run()
    	{
        	ImageView img = (ImageView)findViewById(R.id.simple_anim);
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
            frameAnimation.stop();
    	}
    }





}