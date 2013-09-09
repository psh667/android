package com.androidside.imageswitcherdemoa1;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class ImageSwitcherDemoA1 extends Activity implements View.OnClickListener, ViewSwitcher.ViewFactory {
    int[] images = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4};
    int imagesCnt = 0;
    
    Button button;
    ImageSwitcher switcher;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        switcher = (ImageSwitcher) findViewById(R.id.imageswitcher);
        switcher.setFactory(this);
        switcher.setInAnimation(AnimationUtils.loadAnimation(this,
                    android.R.anim.fade_in));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                    android.R.anim.fade_out));
        
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        switcher.setImageResource(images[imagesCnt++]);
        
        if (imagesCnt > images.length-1) {
            imagesCnt = 0;
        }
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        imageView.setBackgroundColor(0xFF000000);
        return imageView;
    }
    
    
}