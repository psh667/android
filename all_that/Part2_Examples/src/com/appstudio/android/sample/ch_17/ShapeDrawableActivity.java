package com.appstudio.android.sample.ch_17;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;

public class ShapeDrawableActivity extends Activity {
    CustomDrawableView cdv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        cdv = new CustomDrawableView(this);
        
        setContentView(cdv);       
    }
}


class CustomDrawableView extends View {
	private ShapeDrawable[] mDrawables;
	
	public CustomDrawableView(Context context) {
		super(context);
		
		Path path = new Path();
        path.moveTo(50, 0);
        path.lineTo(0, 50);
        path.lineTo(50, 100);
        path.lineTo(100, 50);
        path.close();
        
		mDrawables = new ShapeDrawable[3];
		mDrawables[0] = new ShapeDrawable(new OvalShape());
		mDrawables[0].getPaint().setColor(Color.BLUE);
		
		mDrawables[1] = new ShapeDrawable(new RectShape());
		mDrawables[1].getPaint().setColor(Color.GREEN);
		
		mDrawables[2] = new ShapeDrawable(new PathShape(path, 100, 100));
		mDrawables[2].getPaint().setColor(Color.RED);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		 
		 int x = 10;
         int y = 10;
         int width = 400;
         int height = 100;

         for (Drawable dr : mDrawables) {
             dr.setBounds(x, y, x + width, y + height);
             dr.draw(canvas);
             y += height + 5;
         }
	}
   
	
}