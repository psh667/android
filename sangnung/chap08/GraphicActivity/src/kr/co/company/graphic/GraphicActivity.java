package kr.co.company.graphic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
    public MyView(Context context) {
        super(context);
        setBackgroundColor(Color.YELLOW);
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();    
        paint.setColor(Color.RED);
		canvas.drawLine(20, 50, 100, 50, paint);
		canvas.drawRect(10, 110, 150, 150, paint);
		canvas.drawCircle(50, 230, 40, paint);
		canvas.drawText("텍스트도 그릴 수 있습니다", 10, 300, paint);
    }
}

public class GraphicActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView w = new MyView(this);
        setContentView(w);
    }
}