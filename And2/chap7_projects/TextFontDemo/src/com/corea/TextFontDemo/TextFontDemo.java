package com.corea.TextFontDemo;

import android.app.Activity;
import android.os.Bundle;
	import android.content.*;
	import android.view.*;
	import android.graphics.Canvas;
	import android.graphics.Color;
	import android.graphics.Paint;
	import android.graphics.Typeface;

	public class TextFontDemo extends Activity {
	    /** Called when the activity is first created. */

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(new TxtFont(this));
	    }
	}

	class TxtFont extends View {
	    /** Called when the activity is first created. */
		public TxtFont(Context context) {
		   super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
	       canvas.drawColor(Color.WHITE);
	       Paint tPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		   Typeface tType1 = Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC);
		   tPaint.setTextSize(15);
		   tPaint.setTypeface(tType1);
		   canvas.drawText("Serif Bold_Italic Typeface", 20, 20, tPaint);

		   Typeface tType2 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
		   tPaint.setTextSize(20);
		   tPaint.setTypeface(tType2);
		   canvas.drawText("Sans_Serif Normal Typeface", 30, 50, tPaint);
		}
	}