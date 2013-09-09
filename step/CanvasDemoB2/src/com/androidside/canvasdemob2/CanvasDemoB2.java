package com.androidside.canvasdemob2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

public class CanvasDemoB2 extends Activity {
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
            
            String filePath = Environment.getExternalStorageDirectory()+"/pic.jpg"; 
            
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            canvas.drawBitmap(bitmap, 0, 0, null);
            bitmap.recycle();
        }
    }
}
