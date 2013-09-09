package com.corea.BitmapDemo;

import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.view.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Bitmap1(this));
    }
}

class Bitmap1 extends View {
    /** Called when the activity is first created. */
	public Bitmap1(Context context) {
	   super(context);
	}
	@Override
	protected void onDraw(Canvas canvas) {
	   canvas.drawColor(Color.WHITE);
		
	   Bitmap pic1=BitmapFactory.decodeResource(getResources(), R.drawable.flowerdew);
	   canvas.drawBitmap(pic1, 10, 20, null);
  		
	   Bitmap pic2 = Bitmap.createScaledBitmap(pic1, 150, 100, false);
	   canvas.drawBitmap(pic2, 100, 170, null);
	   
	   Bitmap pic3 = Bitmap.createScaledBitmap(pic1, 100, 75, false);
	   canvas.drawBitmap(pic3, 200, 285, null);
    }
}
