package andexam.ver4_1.c30_service;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.os.*;
import android.service.wallpaper.*;
import android.util.*;
import android.view.*;

public class ChildrenWall extends WallpaperService {
	Handler mHandler = new Handler();

	public Engine onCreateEngine() {
		return new ChildrenEngine();
	}
	
	// 아기들 위치 정보
	class ChildPos {
		float x, y;
		float dx, dy;
		int count;
		Bitmap bitmap;
	};
	
	// 과일 위치 정보
	class FruitPos {
		int x, y;
		Rect frt;
		int type;
	};

	class ChildrenEngine extends Engine {
		final int CHILDRAD = 40;
		final int FRUITNUM = 7;
		final int FRUITRAD = 30;
		boolean mVisible = false;
		int mWidth, mHeight;
		Paint mBackPaint;
		Paint mBitmapPaint;
		ChildPos[] mChild;
		ArrayList<FruitPos> arFruit = new ArrayList<FruitPos>();
		Bitmap[] mFruit;
		Random Rnd = new Random();
		boolean mGiveFruit;
		int mBackColor;
		int mScale = 0;
		int mScaleDelta = 1;
		float mRotate = -7;
		float mRotateDelta = 0.5f;

		ChildrenEngine() {
			mChild = new ChildPos[2];
			mChild[0] = new ChildPos();
			mChild[1] = new ChildPos();
			mChild[0].bitmap = BitmapFactory.decodeResource(getResources(), 
					R.drawable.hanseul);
			mChild[1].bitmap = BitmapFactory.decodeResource(getResources(), 
					R.drawable.hangyul);
			mFruit = new Bitmap[FRUITNUM];
			mFruit[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fruit1);
			mFruit[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fruit2);
			mFruit[2] = BitmapFactory.decodeResource(getResources(), R.drawable.fruit3);
			mFruit[3] = BitmapFactory.decodeResource(getResources(), R.drawable.fruit4);
			mFruit[4] = BitmapFactory.decodeResource(getResources(), R.drawable.fruit5);
			mFruit[5] = BitmapFactory.decodeResource(getResources(), R.drawable.fruit6);
			mFruit[6] = BitmapFactory.decodeResource(getResources(), R.drawable.fruit7);

			// 설정 변경 핸들러 등록
			SharedPreferences pref = getSharedPreferences("ChildrenWall", 0);
			pref.registerOnSharedPreferenceChangeListener(mPrefChange);
			mPrefChange.onSharedPreferenceChanged(pref, null);

			setTouchEventsEnabled(true);
		}

		// 설정값 재조사
		SharedPreferences.OnSharedPreferenceChangeListener mPrefChange =
				new SharedPreferences.OnSharedPreferenceChangeListener() {
					public void onSharedPreferenceChanged(
							SharedPreferences sharedPreferences, String key) {
						mGiveFruit = sharedPreferences.getBoolean("givefruit", true);
						mBackColor = sharedPreferences.getInt("backcolor", 0);
						setBackShader();
					}
		};
		
		// 배경 무늬 만듬
		void setBackShader() {
			if (mBackPaint != null) {
				int color1=0, color2=0;
				switch (mBackColor) {
				case 0:
					color1 = 0xff006000;
					color2 = 0xff002000;
					break;
				case 1:
					color1 = 0xff000060;
					color2 = 0xff000020;
					break;
				case 2:
					color1 = 0xff606000;
					color2 = 0xff202000;
					break;
				case 3:
					color1 = 0xff606060;
					color2 = 0xff202020;
					break;
				}
				mBackPaint.setShader(new LinearGradient(0,0,0,mHeight,
						color1, color2, TileMode.CLAMP));
			}
		}

		Runnable mRunDraw = new Runnable() {
			public void run() {
				drawFrame();
			}
		};

		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
		}

		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mRunDraw);
		}

		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;

			if (mVisible) {
				drawFrame();
			} else {
				mHandler.removeCallbacks(mRunDraw);
			}
		}

		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mRunDraw);
		}

		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			mWidth = width;
			mHeight = height;
			mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			setBackShader();
			mChild[0].x = CHILDRAD;
			mChild[1].x = mWidth - CHILDRAD; 
			mChild[0].y = mChild[1].y = mHeight / 2;
			mChild[0].count = mChild[1].count = 0;
		}

		void drawFrame() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = holder.lockCanvas(); 
			if (canvas != null) {
				// 배경 그림
				canvas.drawRect(0,0,mWidth,mHeight,mBackPaint);

				// 과일 그림
				for(FruitPos fp:arFruit) {
					RectF dest = new RectF(fp.x, fp.y, fp.x, fp.y);
					int scale = FRUITRAD - mScale;
					dest.inset(-scale, -scale);
					canvas.drawBitmap(mFruit[fp.type], null, dest, mBitmapPaint);
				}
				mScale += mScaleDelta;
				if (mScale == 0 || mScale == 5) mScaleDelta *= -1;

				// 미리보기에 대한 안내를 출력한다.
				if (isPreview()) {
					Paint textPaint = new Paint();
					textPaint.setTextSize(40);
					textPaint.setColor(Color.WHITE);
					canvas.drawText("미리보기", mWidth/2-80, 100, textPaint);
				}

				for (int i = 0; i < 2; i++) {
					// 이동 정보가 있으면 계속 이동
					if (mChild[i].count != 0) {
						mChild[i].count--;
						mChild[i].x += mChild[i].dx;
						if (mChild[i].x < CHILDRAD) mChild[i].x = CHILDRAD;
						if (mChild[i].x > mWidth - CHILDRAD) mChild[i].x = 
								mWidth - CHILDRAD;
						mChild[i].y += mChild[i].dy;
						if (mChild[i].y < CHILDRAD) mChild[i].y = CHILDRAD;
						if (mChild[i].y > mHeight - CHILDRAD) mChild[i].y = 
								mHeight - CHILDRAD;
					// 새로운 이동 방향 계산
					} else {
						// 과일이 없으면 -3~3 범위내에서 무작위로 움직인다.
						if (arFruit.size() == 0) {
							mChild[i].dx = Rnd.nextFloat() * 6 - 3;
							mChild[i].dy = Rnd.nextFloat() * 6 - 3;
							mChild[i].count = Rnd.nextInt(11) + 5;
						// 과일이 있으면 과일쪽으로 이동한다.
						} else {
							// 가장 가까이 있는 과일을 찾는다.
							int fruit = findNearestFruit(mChild[i].x, mChild[i].y);
							// 수평 속도는 4~12사이
							mChild[i].dx = Rnd.nextFloat() * 8 + 4;
							// 과일보다 오른쪽에 있으면 왼쪽으로 이동
							if (mChild[i].x > arFruit.get(fruit).x) mChild[i].dx *= -1;
							// 수직 속도는 수평 속도에 기울기를 곱해 구하되 약간의 오차를 
							// 발생시켜 움직임을 자연스럽게 했다.
							float degree = 1;
							if (arFruit.get(fruit).x - mChild[i].x != 0) {
								degree = (arFruit.get(fruit).y - mChild[i].y) / 
										(arFruit.get(fruit).x - mChild[i].x);
								degree *= (Rnd.nextFloat() / 2.5f + 0.8f);
							}
							mChild[i].dy = mChild[i].dx * degree;
							// 너무 커지지 않도록 적당히 한계를 둔다.
							if (mChild[i].dy > 20) mChild[i].dy = 20;
							if (mChild[i].dy < -20) mChild[i].dy = -20;
							mChild[i].count = Rnd.nextInt(11) + 5;
						}
					}
					
					// 충돌 체크
					Rect crt = new Rect((int)mChild[i].x,(int)mChild[i].y,
							(int)mChild[i].x,(int)mChild[i].y);
					crt.inset(-CHILDRAD, -CHILDRAD);
					for(int f = 0;f < arFruit.size(); f++) {
						if (crt.intersect(arFruit.get(f).frt)) {
							arFruit.remove(f);
							f--;
						}
					}
					
					// 아기 그림. 과일이 없으면 고개를 까닥거린다.
					if (arFruit.size() == 0) {
						canvas.save();
						canvas.rotate(i==0 ? mRotate:-mRotate, mChild[i].x, mChild[i].y);
					}
					canvas.drawBitmap(mChild[i].bitmap, mChild[i].x - CHILDRAD, 
							mChild[i].y - CHILDRAD, mBitmapPaint);
					if (arFruit.size() == 0) {
						canvas.restore();
					}
					
					mRotate += mRotateDelta;
					if (mRotate >= 7 || mRotate <= -7) mRotateDelta *= -1;
				}
				
				holder.unlockCanvasAndPost(canvas); 
			} 

			mHandler.removeCallbacks(mRunDraw);
			if (mVisible) {
				mHandler.postDelayed(mRunDraw, 100);
			}
		}
		
		// 가장 가까운 위치의 과일을 찾는다.
		int findNearestFruit(float x, float y) {
			int fruit = 0;
			float mindist = 10000;
			for(int f = 0;f < arFruit.size(); f++) {
				float dist = (float) Math.hypot(x-arFruit.get(f).x, y-arFruit.get(f).y);
				if (dist < mindist) {
					fruit = f;
					mindist = dist;
				}
			}
			
			return fruit;
		}

		// 터치한 곳에 과일을 배치한다.
		public void onTouchEvent(MotionEvent event) {
			super.onTouchEvent(event);
			
			if (mGiveFruit == false) return;
			
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				FruitPos fp = new FruitPos();
				fp.x = (int)event.getX();
				fp.y = (int)event.getY();
				fp.type = Rnd.nextInt(FRUITNUM);
				fp.frt = new Rect((int)fp.x, (int)fp.y,(int)fp.x,(int)fp.y);
				fp.frt.inset(-FRUITRAD, -FRUITRAD);
				arFruit.add(fp);
			}
		}
	}
}

