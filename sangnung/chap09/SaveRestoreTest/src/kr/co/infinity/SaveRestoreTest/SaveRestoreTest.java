package kr.co.infinity.SaveRestoreTest;

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
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);

		canvas.translate(150, 100);

		for(int i=0; i < 5; i++){
			canvas.save();
			canvas.translate(0, 50);
			paint.setColor(0x88ff0000 + i*40);
			canvas.drawCircle(0, 0, 20*i, paint);
			canvas.restore();
		}
	}
}
public class SaveRestoreTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
    }
}

