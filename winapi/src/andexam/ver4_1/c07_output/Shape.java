package andexam.ver4_1.c07_output;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.*;
import android.os.*;
import android.widget.*;

public class Shape extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shape);

		Button btn;

		// 그래디언트 드로블
		btn = (Button)findViewById(R.id.btn21);
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, 
				new int[] {Color.BLUE, Color.GREEN});
		btn.setBackground(g);

		// 단색 드로블
		btn = (Button)findViewById(R.id.btn22);
		btn.setBackground(new ColorDrawable(Color.GREEN));

		// 특정 색으로 채우는 드로블. 모서리는 X, Y 각각에 대해 값을 지정할 수도 있다. 
		btn = (Button)findViewById(R.id.btn23);
		PaintDrawable pd = new PaintDrawable(Color.YELLOW);
		pd.setCornerRadius(10.0f);
		btn.setBackground(pd);

		// 원모양 드로블. 
		btn = (Button)findViewById(R.id.btn31);
		ShapeDrawable sd = new ShapeDrawable(new OvalShape());
		sd.getPaint().setShader(new RadialGradient(60, 30, 50,
				Color.WHITE, Color.BLACK, TileMode.CLAMP));
		btn.setBackground(sd);

		// 둥근 사각형 드로블
		btn = (Button)findViewById(R.id.btn32);
		float[] outR = new float[] {5, 5, 30, 40, 5, 5, 5, 5 };
		RectF inRect = new RectF(30, 30, 30, 30);
		float[] inR = new float[] {0, 0, 20, 30, 0, 0, 0, 0 };
		ShapeDrawable sd2 = new ShapeDrawable(new RoundRectShape(outR, inRect, inR));
		sd2.getPaint().setColor(Color.MAGENTA);
		btn.setBackground(sd2);

		// 패스 드로블
		btn = (Button)findViewById(R.id.btn33);
		Path path = new Path();
		path.moveTo(5, 0);
		path.lineTo(0, 7);
		path.lineTo(3, 7);
		path.lineTo(3, 10);
		path.lineTo(7, 10);
		path.lineTo(7, 7);
		path.lineTo(10, 7);
		ShapeDrawable sd3 = new ShapeDrawable(new PathShape(path,10,10));
		sd3.getPaint().setShader(new LinearGradient(0,0,0,10,
				0xff00ff00, 0xffff0000, TileMode.CLAMP));
		btn.setBackground(sd3);
	}
}