package kr.co.company.aa;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
    public MyView(Context context) {
        super(context);
        setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) 
    {
    	Paint paint = new Paint();
    	paint.setColor(Color.RED);

    	paint.setAntiAlias(true);

    	canvas.drawText("矩萍 俊老府绢教 ON", 10, 100, paint);
    	canvas.drawOval(new RectF(10,100,110,200), paint);

    	paint.setAntiAlias(false);
    	canvas.drawText("矩萍 俊老府绢教 OFF", 200, 100, paint);
    	canvas.drawOval(new RectF(200,100,300,200), paint);
    }
}

public class AAActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView w = new MyView(this);
        setContentView(w);
    }
} 