package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.os.*;
import android.view.*;

public class LinearGrad extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			Paint Pnt = new Paint();
			Pnt.setAntiAlias(true);

			int[] colors = { Color.RED, Color.GREEN, Color.BLUE, 
					Color.YELLOW, Color.WHITE };
			float[] pos = { 0.0f, 0.1f, 0.6f, 0.9f, 1.0f };

			// 수평
			Pnt.setShader(new LinearGradient(0,0,100,0,
					Color.BLUE, Color.WHITE, TileMode.CLAMP));
			canvas.drawRect(0,0,100,100,Pnt);

			// 우하향
			Pnt.setShader(new LinearGradient(110,0,210,100,
					Color.BLUE, Color.WHITE, TileMode.CLAMP));
			canvas.drawRect(110,0,210,100,Pnt);

			// 우상향
			Pnt.setShader(new LinearGradient(220,100,320,0,
					Color.BLUE, Color.WHITE, TileMode.CLAMP));
			canvas.drawRect(220,0,320,100,Pnt);

			// 가장자리 반복
			Pnt.setShader(new LinearGradient(0,0,100,0,
					Color.BLUE, Color.WHITE, TileMode.CLAMP));
			canvas.drawRect(0,110,320,150,Pnt);

			// 무늬 반복 
			Pnt.setShader(new LinearGradient(0,0,100,0,
					Color.BLUE, Color.WHITE, TileMode.REPEAT));
			canvas.drawRect(0,160,320,200,Pnt);

			// 무늬 반사 반복 
			Pnt.setShader(new LinearGradient(0,0,100,0,
					Color.BLUE, Color.WHITE, TileMode.MIRROR));
			canvas.drawRect(0,210,320,250,Pnt);

			// 여러 가지 색상 균등 배치
			Pnt.setShader(new LinearGradient(0,0,320,0,
					colors, null, TileMode.CLAMP));
			canvas.drawRect(0,260,320,300,Pnt);

			// 여러 가지 색상 임의 배치 
			Pnt.setShader(new LinearGradient(0,0,320,0,
					colors, pos, TileMode.CLAMP));
			canvas.drawRect(0,310,320,350,Pnt);
		}
	}
}
