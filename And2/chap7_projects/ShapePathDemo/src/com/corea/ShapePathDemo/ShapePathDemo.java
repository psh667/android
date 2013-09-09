package com.corea.ShapePathDemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Bundle;
import android.widget.ImageView;

public class ShapePathDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Path path = new Path();
    	
    	path.moveTo(50, 0);    // 1
       	path.lineTo(0, 37);	// 2
    	path.lineTo(18, 96);	// 3
    	path.lineTo(84, 96);	// 4
    	path.lineTo(100, 37);	// 5
    	path.lineTo(50, 0);	// 6
    	    	
    	ShapeDrawable penta =new ShapeDrawable(new PathShape(path, 100, 100));
    	
    	penta.setIntrinsicWidth(80);
    	penta.setIntrinsicHeight(80);
    	penta.getPaint().setColor(Color.MAGENTA);
	
    	ImageView imageView=(ImageView)findViewById(R.id.ImageView01);
        imageView.setImageDrawable(penta);    
    }
}