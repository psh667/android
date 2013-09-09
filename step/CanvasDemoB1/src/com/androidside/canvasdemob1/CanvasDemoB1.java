package com.androidside.canvasdemob1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

public class CanvasDemoB1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    private static class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            canvas.drawBitmap(bitmap, 0, 0, null);
            
            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, 80, 20, false);
            for (int i = 0; i < 250; i+=25) {
                canvas.drawBitmap(bitmap2, i, 100+i, null);
            }
            
            bitmap.recycle();
            bitmap2.recycle();
        }
    }
}
