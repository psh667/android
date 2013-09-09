package com.corea.CanvasDemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class CanvasDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Custom1 cv = new Custom1(this); 
        setContentView(cv);
    }	

    private class Custom1 extends View {
        public Custom1(Context context) {
             super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            Paint circlePaint = new Paint();
            circlePaint.setColor(Color.YELLOW);
            canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/4, 
            				  canvas.getWidth()/4, circlePaint);
        }
    }
}
