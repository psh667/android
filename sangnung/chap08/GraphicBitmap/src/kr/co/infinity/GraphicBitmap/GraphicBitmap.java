package kr.co.infinity.GraphicBitmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
class MyView extends View {
    public MyView(Context context) {
        super(context);
        //setBackgroundColor(Color.YELLOW);
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();       
        Matrix m= new Matrix();
        m.preScale(1, -1);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.house);
        Bitmap mb=Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, false);
        Bitmap sb = Bitmap.createScaledBitmap(b, 200, 200, false);
        canvas.drawBitmap(mb, 0, 0, null);
        canvas.drawBitmap(sb, 100, 100, null);
    }
}

public class GraphicBitmap extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView w = new MyView(this);
        setContentView(w);
    }
}
