package com.appstudio.android.sample.ch_17;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class GraphActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_view);
        
        DrawingView ov = new DrawingView(this);
        
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout1);
        ll.addView(ov);
        
    }
    
    public class DrawingView extends View{

		public DrawingView(Context context) {
			super(context);
		}

		public DrawingView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		public DrawingView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			paint.setTextSize(22);
			paint.setAntiAlias(true);
			
			//원
			canvas.drawCircle(240, 100, 70, paint);
			canvas.drawText("Circle", 200, 190, paint);
			
			//사각형
			canvas.drawRect(190,200,290,300, paint);
			canvas.drawText("Rect", 200, 320, paint);
			
			//부채꼴
			RectF rf = new RectF(190, 350, 290, 450);
			canvas.drawArc(rf, 0, 100, true, paint);
			canvas.drawText("Arc", 200, 470, paint);
			
			//선만들기
			paint.setStrokeWidth(10);
			canvas.drawLine(200, 500, 300, 500, paint);
			canvas.drawText("Line", 200, 530, paint);
	
		}
		
		
		
		
		
    	
    }
}