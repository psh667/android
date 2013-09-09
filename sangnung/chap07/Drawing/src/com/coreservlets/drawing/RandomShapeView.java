package com.coreservlets.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RandomShapeView extends View {
    private Integer[] mBackgrounds =
        { Color.CYAN, Color.GRAY, Color.LTGRAY,
          Color.MAGENTA, Color.YELLOW, Color.WHITE };
    private Paint[] mForegrounds =
        { makePaint(Color.BLACK), makePaint(Color.BLUE), 
          makePaint(Color.GREEN), makePaint(Color.RED) };
    private Bitmap[] mPics = 
        { makeBitmap(R.drawable.emo_im_angel), 
          makeBitmap(R.drawable.emo_im_cool),
          makeBitmap(R.drawable.emo_im_crying),
          makeBitmap(R.drawable.emo_im_happy),
          makeBitmap(R.drawable.emo_im_yelling) };
    private String mMessage = "Android";
    
    // This constructor called if View made programmatically and added with setContentView
    
    public RandomShapeView(Context context) {
        super(context);
    }
    
    // This constructor called if made from XML file
    
    public RandomShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    // Avoid allocating objects in onDraw, especially bitmaps.
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(RandomUtils.randomElement(mBackgrounds));
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int avgShapeWidth = viewWidth/5;
        for(int i=0; i<20; i++) {
            drawRandomCircle(canvas, viewWidth, viewHeight, avgShapeWidth);
            drawRandomRect(canvas, viewWidth, viewHeight, avgShapeWidth);
            drawRandomBitmap(canvas, viewWidth, viewHeight);
            drawRandomText(canvas, viewWidth, viewHeight, avgShapeWidth);
        }
    }
    
    private Paint makePaint(int color) {
        Paint p = new Paint();
        p.setColor(color);
        return(p);
    }
    
    private Bitmap makeBitmap(int bitmapId) {
        return(BitmapFactory.decodeResource(getResources(), bitmapId));
    }
    
    private void drawRandomCircle(Canvas canvas, int viewWidth, 
                                  int viewHeight, int avgShapeWidth) {
        float x = RandomUtils.randomFloat(viewWidth);
        float y = RandomUtils.randomFloat(viewHeight);
        float radius = RandomUtils.randomFloat(avgShapeWidth/2);
        Paint circleColor = RandomUtils.randomElement(mForegrounds);
        canvas.drawCircle(x, y, radius, circleColor);
    }
    
    private void drawRandomRect(Canvas canvas, int viewWidth, 
                                int viewHeight, int avgShapeWidth) {
        float left = RandomUtils.randomFloat(viewWidth);
        float top = RandomUtils.randomFloat(viewHeight);
        float width = RandomUtils.randomFloat(avgShapeWidth);
        float right = left + width;
        float bottom = top + width;
        Paint squareColor = RandomUtils.randomElement(mForegrounds);
        canvas.drawRect(left, top, right, bottom, squareColor);
    }
    
    private void drawRandomBitmap(Canvas canvas, int viewWidth, 
                                  int viewHeight) {
        float left = RandomUtils.randomFloat(viewWidth);
        float top = RandomUtils.randomFloat(viewHeight);
        Bitmap pic = RandomUtils.randomElement(mPics);
        // Last arg is the Paint: you can use null for opaque images
        canvas.drawBitmap(pic, left, top, null);
    }
    
    private void drawRandomText(Canvas canvas, int viewWidth, 
                                int viewHeight, int avgShapeWidth) {
        float x = RandomUtils.randomFloat(viewWidth);
        float y = RandomUtils.randomFloat(viewHeight);
        float textSize = RandomUtils.randomFloat(avgShapeWidth);
        Paint textPaint = RandomUtils.randomElement(mForegrounds);
        textPaint.setTextSize(textSize);
        canvas.drawText(mMessage, x, y, textPaint);
    }
}