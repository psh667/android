package org.androidtown.ui.wallpaper;

import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * 라이브 배경화면을 만드는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class SampleWallpaperService  extends WallpaperService {
	private Handler handler = null;

	public SampleWallpaperService() {
		handler = new Handler();
	}

	public Engine onCreateEngine() {
		return new SampleWallpaperEngine();
	}

	private class SampleWallpaperEngine extends Engine {
		public static final String TAG = "SampleWallpaperService";

		private boolean visible = false;

		private int[] colors = {0, 0, 0} ;

		private final Runnable runnable = new Runnable() {
			public void run() {
				drawBackground();
			}
		};

		private void drawBackground() {
			SurfaceHolder holder = getSurfaceHolder();

			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					canvas.drawARGB(200, colors[0], colors[1], colors[2]);
				}
				updateBackground(colors);
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}

			handler.removeCallbacks(runnable);

			if (visible) {
				handler.postDelayed(runnable, 25);
			}
		}

		private void updateBackground(int[] colors) {
			if (colors[2] < 128) {
				colors[2]++;
			} else if (colors[1] < 128) {
				colors[1]++;
			} else if (colors[0] < 128) {
				colors[0]++;
			} else {
				resetColors();
			}
		}

		private void resetColors() {
			colors[0] = 0;
			colors[1] = 0;
			colors[2] = 0;
		}

		public void onDestroy() {
			super.onDestroy();

			handler.removeCallbacks(runnable);
		}

		public void onVisibilityChanged(boolean visible) {
			super.onVisibilityChanged(visible);

			Log.d(TAG, "onVisibilityChanged() called : " + visible);

			this.visible = visible;

			if (visible) {
				drawBackground();
			} else {
				handler.removeCallbacks(runnable);
			}
		}

		public void onTouchEvent(MotionEvent event) {
			Log.d(TAG, "onTouchEvent() called : " + event.getX() + ", " + event.getY());
			resetColors();

			super.onTouchEvent(event);
		}



	}

}