package com.corea.ShapeDemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.widget.ImageView;

public class ShapeDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ShapeDrawable rect=new ShapeDrawable(new OvalShape());

    	rect.getPaint().setColor(Color.YELLOW);
    	rect.setIntrinsicHeight(150);
    	rect.setIntrinsicWidth(200);
    		
    	ImageView iView=(ImageView)findViewById(R.id.ImageView1);
        iView.setImageDrawable(rect);
    }
}