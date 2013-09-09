package com.androidside.tweeningdemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class TweeningDemoA1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ImageView imageView = (ImageView) findViewById(R.id.tweening_ani_imageview);        
        final Animation an = AnimationUtils.loadAnimation(this, R.anim.spin);
        
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageView.startAnimation(an);
            }
        });
    }
}
