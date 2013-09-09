package com.androidside.canvasdemoa1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class CanvasDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
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

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            
            //사각형 채우기
            paint.setStyle(Paint.Style.FILL);            
            canvas.drawRect(10, 10, 10+100, 10+100, paint);
            
            //사각형 테두리 그리기
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(120, 10, 120+100, 10+100, paint);
            
            //라운드 사각형 채우기
            paint.setStyle(Paint.Style.FILL);
            RectF rectf1 = new RectF(10, 120, 10+100, 120+100);
            canvas.drawRoundRect(rectf1, 10, 10, paint);
            
            //라운드 사각형 테두리 그리기
            paint.setStyle(Paint.Style.STROKE);
            RectF rectf2 = new RectF(120, 120, 120+100, 120+100);
            canvas.drawRoundRect(rectf2, 10, 10, paint);
            
            //원 채우기
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(60, 280, 50, paint);
            
            //원 테두리 그리기
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(170, 280, 50, paint);
        }
    }
}
