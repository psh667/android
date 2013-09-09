package com.coreservlets.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SkewShapeView extends View {
    private Paint mBlackPaint = makePaint(Color.BLUE);
    
    // If made programmatically and added with setContentView
    
    public SkewShapeView(Context context) {
        super(context);
    }
    
    // If made from XML file
    public SkewShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    // Avoid allocating objects in onDraw, esp bitmaps.
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int deltaW = viewWidth/36;
        int rectWidth = 4*deltaW;
        int deltaH = 5;
        int rectHeight = viewHeight-2*deltaH;
        int left = deltaW;
        int top = deltaH;
        for(int i=0; i<5; i++) {
            canvas.drawRect(left, top, left+rectWidth, top+rectHeight, mBlackPaint);
            left = left+6*deltaW;
            canvas.skew(0.1f, 0);
        }
    }
    
    private Paint makePaint(int color) {
        // Angled lines look much better with antialiasing
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Paint p = new Paint();
        p.setColor(color);
        return(p);
    }
}