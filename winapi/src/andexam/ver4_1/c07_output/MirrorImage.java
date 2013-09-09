package andexam.ver4_1.c07_output;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.os.*;
import android.view.*;

public class MirrorImage extends Activity {
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
			Paint Pnt = new Paint();
			canvas.drawColor(Color.BLACK);

			Bitmap car = BitmapFactory.decodeResource(getContext().getResources(), 
					R.drawable.korandoc);
			
			int width = car.getWidth();
			int height = car.getHeight();
			
			Matrix matrix = new Matrix();
			matrix.preScale(1, -1f);
			Bitmap mirror = Bitmap.createBitmap(car, 0, 0, width, height, matrix, false);

			canvas.drawBitmap(car, 0, 0, null);
			ComposeShader comp = new ComposeShader(
					new BitmapShader(mirror, TileMode.REPEAT, TileMode.REPEAT),
					new LinearGradient(0, height, 0, height + height, Color.TRANSPARENT, 
							Color.BLACK, TileMode.REPEAT), 
					new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
			Pnt.setShader(comp);
			canvas.drawRect(0, height, width, height + height, Pnt);
		}
	}
}
