package com.corea.LinearGradientDemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

public class LinearGradientDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LinearGrad(this));
    }
}

class LinearGrad extends View {
    /** Called when the activity is first created. */
	public LinearGrad(Context context) {
		super(context);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		Paint rectPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		LinearGradient linGrad = new LinearGradient
        	(0, 0, 25, 25, Color.MAGENTA, Color.CYAN, Shader.TileMode.REPEAT);
		rectPaint.setShader(linGrad);
		canvas.drawRect(canvas.getWidth()/4, canvas.getHeight()/6, 
			canvas.getWidth()/4*3, canvas.getHeight()/3*2, rectPaint);
	}
}