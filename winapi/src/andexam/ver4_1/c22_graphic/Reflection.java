package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

// 볼 하나에 대한 정보
class Ball {
	int x, y;
	int rad;
	int dx, dy;
	int color;
	int count;

	// 새로운 볼 생성
	static Ball Create(int x, int y, int Rad) {
		Random Rnd = new Random();
		Ball NewBall = new Ball();

		NewBall.x = x;
		NewBall.y = y;
		NewBall.rad = Rad;
		do {
			NewBall.dx = Rnd.nextInt(11) - 5;
			NewBall.dy = Rnd.nextInt(11) - 5;
		} while (NewBall.dx == 0 || NewBall.dy == 0);

		NewBall.count = 0;
		NewBall.color = Color.rgb(Rnd.nextInt(256), Rnd.nextInt(256), Rnd.nextInt(256));

		return NewBall;
	}

	// 볼 이동
	void Move(int Width, int Height) {
		x += dx;
		y += dy;

		if (x < rad || x > Width - rad) {
			dx *= -1;
			count++;
		}
		if (y < rad || y > Height - rad) {
			dy *= -1;
			count++;
		}
	}

	// 그리기
	void Draw(Canvas canvas) {
		Paint pnt = new Paint();
		pnt.setAntiAlias(true);

		int r;
		int alpha;

		for (r = rad, alpha = 1; r > 4; r --, alpha += 5) {
			pnt.setColor(Color.argb(alpha, Color.red(color), 
					Color.green(color), Color.blue(color)));
			canvas.drawCircle(x, y, r, pnt);
		}
	}
}

public class Reflection extends Activity {
	MyView vw;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vw = new MyView(this);
		setContentView(vw);
	}
}

class MyView extends View {
	Bitmap mBack;
	ArrayList<Ball> arBall = new ArrayList<Ball>();
	final static int DELAY = 50;
	final static int RAD = 24;

	public MyView(Context context) {
		super(context);
		mBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.family);
		mHandler.sendEmptyMessageDelayed(0,DELAY);
	}	

	// 새로운 볼 생성
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Ball NewBall = Ball.Create((int)event.getX(), (int)event.getY(), RAD);
			arBall.add(NewBall);
			invalidate();
			return true;
		}
		return false;
	}

	// 화면 그리기
	public void onDraw(Canvas canvas) {
		Rect dst = new Rect(0,0,getWidth(),getHeight());
		canvas.drawBitmap(mBack, null, dst, null);
		for (int idx = 0;idx < arBall.size(); idx++) {
			arBall.get(idx).Draw(canvas);
		}
	}

	// 볼 이동 및 화면 무효화
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Ball B;
			for (int idx = 0;idx < arBall.size(); idx++) {
				B = arBall.get(idx);
				B.Move(getWidth(), getHeight());
				if (B.count > 4) {
					arBall.remove(idx);
					idx--;
				}
			}

			invalidate();
			mHandler.sendEmptyMessageDelayed(0,DELAY);
		}
	};
}
