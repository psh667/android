package andexam.ver4_1.c34_sensor;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.hardware.*;
import android.os.*;
import android.view.*;

public class Accelerator extends Activity {
	SensorManager mSm;
	AcceleratorView mView;
	boolean mArrange = true;
	float pitch;
	float roll;
	final static float G = SensorManager.STANDARD_GRAVITY;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = new AcceleratorView(this);
		setContentView(mView);
		
		mSm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	}
	
	protected void onResume() {
		super.onResume();
		RegisterListener(SensorManager.SENSOR_DELAY_GAME);
	}
	
	protected void onPause() {
		super.onPause();
		mSm.unregisterListener(mView);
	}
	
	void reRegisterListener(int delay) {
		mSm.unregisterListener(mView);
		RegisterListener(delay);
	}
	
	@SuppressWarnings("deprecation")
	void RegisterListener(int delay) {
		mSm.registerListener(mView, mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				delay);
		mSm.registerListener(mView, mSm.getDefaultSensor(Sensor.TYPE_ORIENTATION), 
				delay);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0,1,0,"X");
		menu.add(0,2,0,"Y");
		menu.add(0,3,0,"Z");
		menu.add(0,4,0,"중력 배제");
		menu.add(0,5,0,"주기");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			mView.viewLog(1);
			return true;
		case 2:
			mView.viewLog(2);
			return true;
		case 3:
			mView.viewLog(3);
			return true;
		case 4:
			mArrange = !mArrange;
			return true;
		case 5:
			new AlertDialog.Builder(Accelerator.this)
			.setTitle("센서 주기를 선택하세요.")
			.setItems(new String[] {"느리게", "보통", "빠르게", "최대속도" }, 
				new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						reRegisterListener(SensorManager.SENSOR_DELAY_UI);
						break;
					case 1:
						reRegisterListener(SensorManager.SENSOR_DELAY_NORMAL);
						break;
					case 2:
						reRegisterListener(SensorManager.SENSOR_DELAY_GAME);
						break;
					case 3:
						reRegisterListener(SensorManager.SENSOR_DELAY_FASTEST);
						break;
					}
				}
			})
			.setNegativeButton("취소", null)
			.show();
			return true;
		}
		return false;
	}
	
	class AccelValue {
		float x;
		float y;
		float z;
		float pitch;
		float roll;
		long time;
	}

	class AcceleratorView extends View implements SensorEventListener {
		int width;
		int height;
		int w10;
		ArrayList<AccelValue> arValue = new ArrayList<AccelValue>();
		final static int MAGX = 2;
		final static int MAGY = 2;
		boolean mStop = false;
		
		public AcceleratorView(Context context) {
			super(context);
		}

		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			width = w;
			height = h;
			w10 = width/10;
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@SuppressWarnings("deprecation")
		public void onSensorChanged(SensorEvent event) {
			if (mStop) {
				return;
			}
			float[] v = event.values;
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				if (arValue.size() * MAGY > height - 50) {
					arValue.remove(0);
				}
				AccelValue av = new AccelValue();
				av.x = v[0];
				av.y = v[1];
				av.z = v[2];
				av.pitch = pitch;
				av.roll = roll;
				av.time = event.timestamp;
				arValue.add(av);
				invalidate();
				break;
			case Sensor.TYPE_ORIENTATION:
				pitch = v[1];
				roll = v[2];
				break;
			}
		}

		public void onDraw(Canvas canvas) {
			Paint Pnt = new Paint();
			Pnt.setColor(Color.WHITE);
			Pnt.setTextSize(20);
			Paint linePaint = new Paint();
			linePaint.setColor(0xff404040);
			
			int i;
			AccelValue v;
			float value;
			int x, y;
			int oldx, oldy;
			int basex;

			// X 가속 그림
			basex = oldx = w10 * 2;
			oldy = 0;
			canvas.drawText("X", basex - 25, 25, Pnt);
			canvas.drawLine(basex, 0, basex, height, linePaint);
			for (i = 0;i < arValue.size();i++) {
				v = arValue.get(i);
				value = v.x;
				if (mArrange) {
					value -= (float)Math.sin(Deg2Rad(v.roll)) * G;
				}
				x = (int)(basex + value * MAGX);
				y = i * MAGY;
				canvas.drawLine(oldx, oldy, x, y, Pnt);
				oldx = x;
				oldy = y;
			}

			// Y 가속 그림
			basex = oldx = w10 * 5;
			oldy = 0;
			canvas.drawText("Y", basex - 25, 25, Pnt);
			canvas.drawLine(basex, 0, basex, height, linePaint);
			for (i = 0;i < arValue.size();i++) {
				v = arValue.get(i);
				value = v.y;
				if (mArrange) {
					value += (float)Math.sin(Deg2Rad(v.pitch)) * G;
				}
				x = (int)(basex + value * MAGX);
				y = i * MAGY;
				canvas.drawLine(oldx, oldy, x, y, Pnt);
				oldx = x;
				oldy = y;
			}

			// Z 가속 그림
			basex = oldx = w10 * 8;
			oldy = 0;
			canvas.drawText("Z", basex - 25, 25, Pnt);
			canvas.drawLine(basex, 0, basex, height, linePaint);
			for (i = 0;i < arValue.size();i++) {
				v = arValue.get(i);
				value = v.z;
				if (mArrange) {
					// 중력을 제외한 순수한 Z 축 움직임을 구하는 방법을 아직 찾지 못했음
					// pitch, roll을 알고 있지만 방정식을 도출할 수 없음
				}
				x = (int)(basex + value * MAGX);
				y = i * MAGY;
				canvas.drawLine(oldx, oldy, x, y, Pnt);
				oldx = x;
				oldy = y;
			}
		}
		
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				mStop = !mStop;
				return true;
			}
			return false;
		}
		
		public void viewLog(int what) {
			mStop = true;
			StringBuilder result = new StringBuilder();
			float min = 100f, max = -100f;
			// 아래쪽 1/4 정도만 덤프한다. -> 전부 덤프
			for (int i = 0;i < arValue.size();i++) {
				float value = 0;
				switch (what) {
				case 1:
					value = arValue.get(i).x;
					break;
				case 2:
					value = arValue.get(i).y;
					break;
				case 3:
					value = arValue.get(i).z;
					break;
				}
				// 시간은 10억분의 1초 단위이므로 정수 2자리, 소수 2자리만 출력
				result.append(String.format("%d(%.2f) => %.4f\n", i,
						(float)(arValue.get(i).time / 10000000L % 10000L)/100, value));
				min = Math.min(value, min);
				max = Math.max(value, max);
			}
			
			result.append("min = " + min + "\nmax = " + max);

			new AlertDialog.Builder(Accelerator.this)
			.setMessage(result.toString())
			.setTitle("Dump")
			.show();			
		}
		
		float Deg2Rad(float deg) {
			return (deg * (float)Math.PI) / 180;
		}
	}
}
