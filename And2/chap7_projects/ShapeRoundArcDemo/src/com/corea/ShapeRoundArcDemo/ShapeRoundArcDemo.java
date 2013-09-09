package com.corea.ShapeRoundArcDemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.widget.ImageView;

public class ShapeRoundArcDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        float[] outerRadii = new float[] {6,6,6,6,6,6,6,6};
    	RectF insetRectangle = new RectF(2,2,2,2);
    	float[] innerRadii = new float[] {6,6,6,6,6,6,6,6};
    	
    	ShapeDrawable rndrect=new ShapeDrawable
    	       (new RoundRectShape( outerRadii, insetRectangle, innerRadii));
    	
    	rndrect.setIntrinsicHeight(100);
    	rndrect.setIntrinsicWidth(70);
    	rndrect.getPaint().setColor(Color.BLUE);
	
    	ImageView iView=(ImageView)findViewById(R.id.ImageView1);
        iView.setImageDrawable(rndrect);
    }
}