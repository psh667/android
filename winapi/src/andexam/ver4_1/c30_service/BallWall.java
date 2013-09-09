package andexam.ver4_1.c30_service;

import andexam.ver4_1.*;
import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.os.*;
import android.service.wallpaper.*;
import android.util.*;
import android.view.*;

public class BallWall extends WallpaperService {
	Handler mHandler = new Handler();

	public Engine onCreateEngine() {
		return new BallEngine();
	}

	class BallEngine extends Engine {
		boolean mVisible = false;
		int mWidth, mHeight;
		Paint mBackPaint;
		Paint mBallPaint;
		int mBallX=50, mBallY=50;
		int mDx = 3, mDy = 4;
		final int RAD = 30;

		Runnable mRunDraw = new Runnable() {
			public void run() {
				drawFrame();
			}
		};

		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			mWidth = width;
			mHeight = height;
			mBallPaint = new Paint();
			mBallPaint.setAntiAlias(true);
			mBallPaint.setColor(Color.YELLOW);
			mBackPaint = new Paint();
			mBackPaint.setShader(new LinearGradient(0,0,0,mHeight,
					0xff606060, 0xff202020, TileMode.CLAMP));
		}

		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mRunDraw);
		}
		
		public void onDestroy() {
			super.onDestroy();
			mVisible = false;
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

		void drawFrame() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = holder.lockCanvas(); 
			if (canvas != null) {
				canvas.drawRect(0,0,mWidth,mHeight,mBackPaint);
				mBallX += mDx;
				if (mBallX + RAD > mWidth || mBallX - RAD < 0) mDx *= -1;
				mBallY += mDy;
				if (mBallY + RAD > mHeight || mBallY - RAD < 0) mDy *= -1;
				canvas.drawCircle(mBallX, mBallY, RAD, mBallPaint);
				holder.unlockCanvasAndPost(canvas); 
			} 

			mHandler.removeCallbacks(mRunDraw);
			if (mVisible) {
				mHandler.postDelayed(mRunDraw, 50);
			}
		}
	}
}

