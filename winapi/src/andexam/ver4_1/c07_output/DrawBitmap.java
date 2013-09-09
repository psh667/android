package andexam.ver4_1.c07_output;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;

public class DrawBitmap extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView vw = new MyView(this);
		setContentView(vw);
	}

	class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);
			Paint Pnt = new Paint();
			
			///* 원론적인 방법
			Resources res = getResources();
			BitmapDrawable bd = (BitmapDrawable)res.getDrawable(R.drawable.harubang);
			Bitmap bit = bd.getBitmap();
			//*/
			
			// 간단한 방법
			//Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.harubang);
			
			canvas.drawBitmap(bit, 10, 10, null);
			canvas.drawBitmap(bit, null, new Rect(120,10,170,85), null);
			canvas.drawBitmap(bit, new Rect(30,30,70,80), 
					new Rect(180,10,180+80,10+100), null);
		}
	}
}
