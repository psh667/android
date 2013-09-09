package com.appstudio.android.sample.ch_17;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class TransitionDrawableActivity extends Activity {
    
    TransitionDrawable transition ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_layout);
        
        Resources res = this.getResources();
        transition = (TransitionDrawable)res.getDrawable(R.drawable.transition_img);
        transition.startTransition(3000);
        
        ImageView iv = (ImageView)findViewById(R.id.imageView1);
        iv.setImageDrawable(transition);
        
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				transition.startTransition(3000);
			}
		});
        
        
       
    }
}