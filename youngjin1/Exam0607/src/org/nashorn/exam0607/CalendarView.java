package org.nashorn.exam0607;

import android.view.*;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.*;

public class CalendarView extends View
{
	private Exam0607 parent;

	private Paint blackPaint;
	private Paint textPaint;
	private Paint redTextPaint;
	private Paint blackTextPaint;
	private Paint cyanTextPaint;
	
	private Drawable bgImage;
	
	private int[][]		gcal = new int[7][6];
	
	public CalendarView(Context context)
	{
		super(context);
		setFocusable(true);
		parent = (Exam0607)context;
		initSheetView();
	}
	
	protected void initSheetView()
	{
		setFocusable(true);
		
		Resources r = this.getResources();
		
		blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		blackPaint.setColor(r.getColor(R.color.black_color));
		blackPaint.setStrokeWidth(1);
		blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		
		textPaint	= new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(20);
		textPaint.setColor(r.getColor(R.color.text_color));
		
		redTextPaint	= new Paint(Paint.ANTI_ALIAS_FLAG);
		redTextPaint.setTextSize(20);
		redTextPaint.setColor(r.getColor(R.color.red_color));
		
		blackTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		blackTextPaint.setTextSize(20);
		blackTextPaint.setColor(r.getColor(R.color.black_color));
		
		cyanTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		cyanTextPaint.setTextSize(20);
		cyanTextPaint.setColor(r.getColor(R.color.cyan_color));
		
		bgImage = r.getDrawable(R.drawable.bg);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		
		bgImage.setBounds(0, 0, width, height);
		bgImage.draw(canvas);
		
		for (int i = 0; i < 8; i++)
		{
			canvas.drawLine(width/7*i, 0, width/7*i, height, blackPaint);
		}
		for (int i = 0; i < 7; i++)
		{
			canvas.drawLine(0, height/6*i, width, height/6*i, blackPaint);
		}

		//draw Calandar
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 6; j++)
				gcal[i][j] = 0;
		
		int  first_day_of_week, x1, y1, x_sun;
		first_day_of_week = parent.calendar.zeller(
				parent.calendar.getCurrentYear(), parent.calendar.getCurrentMonth(), 1);
		for (int i = 0; i < parent.calendar.getLastDay(
				parent.calendar.getCurrentYear(), parent.calendar.getCurrentMonth()); i++)
	    {
			x1	= ((first_day_of_week + i) % 7) * (width / 7);
			y1	= ((first_day_of_week + i) / 7) * (height / 6);
	        x_sun = (first_day_of_week + i) % 7;
	        gcal[(first_day_of_week + i) % 7][(first_day_of_week + i) / 7] = i + 1;

	        if ( parent.calendar.getCurrentDay() == i + 1)
	        {
	        	String dayText = String.valueOf(i+1);
				
	        	canvas.drawText(dayText, x1+1, y1+21, blackTextPaint);
				canvas.drawText(dayText, x1+2, y1+21, blackTextPaint);
				
				canvas.drawText(dayText,x1, y1+20, cyanTextPaint);
				canvas.drawText(dayText,x1+1, y1+20, cyanTextPaint);
	        }
			else if (x_sun == x1)
			{
				String dayText = String.valueOf(i+1);
				
				canvas.drawText(dayText, 1, y1+21, blackTextPaint);
				canvas.drawText(dayText, 2, y1+21, blackTextPaint);
				
				canvas.drawText(dayText, 0, y1+20, redTextPaint);
				canvas.drawText(dayText, 1, y1+20, redTextPaint);
				
			}
			else
			{
				String dayText = String.valueOf(i+1);
				
				canvas.drawText(dayText, x1+1, y1+21, blackTextPaint);
				canvas.drawText(dayText, x1+2, y1+21, blackTextPaint);
				
				canvas.drawText(dayText, x1, y1+20, textPaint);
				canvas.drawText(dayText, x1+1, y1+20, textPaint);
				
			}
	    }
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        
        int width = getMeasuredWidth();
		int height = getMeasuredHeight();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	{
            		for (int k = 0; k < 7; k++)
            			for (int l = 0; l < 6; l++)
            			{
            				if (x > ((width / 7) * k) && 
            					x < ((width / 7) * (k + 1)) &&
            					y > ((height / 6) * l) && 
            					y < ((height / 6) * (l + 1)))
            				{
            					if (gcal[k][l] != 0)
            					{
            						if (parent.calendar.getCurrentDay() != gcal[k][l])
            						{
            							parent.calendar.setCurrentDay(gcal[k][l]);

            							parent.displayCalendar();
            						}
            						break;
            					}
            				}
            			}
            	}
                break;
            case MotionEvent.ACTION_MOVE:
                //touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //touch_up();
                invalidate();
                break;
        }
        return true;
    }	
}