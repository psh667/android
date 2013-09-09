package com.coreservlets.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RotateTextView extends View {
    private Paint mPaint = makePaint(Color.BLUE);
    private String mMessage = "Android";
    
    // If made programmatically and added with setContentView
    
    public RotateTextView(Context context) {
        super(context);
    }
    
    // If made from XML file
    
    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    // Avoid allocating objects in onDraw, esp bitmaps.
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        canvas.translate(viewWidth/2, viewHeight/2);
        for(int i=0; i<10; i++) {
            canvas.drawText(mMessage, 0, 0, mPaint);
            canvas.rotate(36);
        }
    }
    
    private Paint makePaint(int color) {
        // Angled lines look much better with antialiasing
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(color);
        p.setTextSize(50);
        return(p);
    }
}