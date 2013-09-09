package org.androidtown.animation.thread;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 스레드를 이용한 애니메이션을 보여주는 서피스뷰 클래스 정의
 * 
 * @author Mike
 *
 */
public class ThreadAnimationView extends SurfaceView implements Callback {

	private ImageThread thread;

	public ThreadAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
	}

	private void init(Context context) {
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		thread = new ImageThread(context, holder);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

	public void surfaceDestroyed(SurfaceHolder holder) {
		try {
			thread.join();
		} catch (InterruptedException ex) { }
	}

	class ImageThread extends Thread {
		SurfaceHolder mHolder;
		private int mImageWidth;
		private int mImageHeight;
		private int mCount = 0;
		private Drawable mFrontImage[] = new Drawable[4];
		private Bitmap mBackImage[] = new Bitmap[4];

		public ImageThread(Context context, SurfaceHolder surfaceHolder) {
			mHolder = surfaceHolder;

			Resources res = context.getResources();

			mFrontImage[0] = res.getDrawable(R.drawable.emo_im_laughing);
			mFrontImage[1] = res.getDrawable(R.drawable.emo_im_happy);
			mFrontImage[2] = res.getDrawable(R.drawable.emo_im_sad);
			mFrontImage[3] = res.getDrawable(R.drawable.emo_im_crying);

			mBackImage[0] = BitmapFactory.decodeResource(res,
					R.drawable.gallery_selected_default);
			mBackImage[1] = BitmapFactory.decodeResource(res,
					R.drawable.gallery_selected_focused);
			mBackImage[2] = BitmapFactory.decodeResource(res,
					R.drawable.gallery_selected_pressed);
			mBackImage[3] = BitmapFactory.decodeResource(res,
					R.drawable.gallery_unselected_default);

			mImageWidth = mFrontImage[1].getIntrinsicWidth();
			mImageHeight = mFrontImage[1].getIntrinsicHeight();
		}

		public void run() {
			while (true) {
				Canvas c = null;
				try {
					c = mHolder.lockCanvas(null);
					synchronized (mHolder) {
						doDraw(c);
						mCount++;
						sleep(100);
					}
				} catch (InterruptedException ex) {
					Log.e("ThreadAnimationView", "Exception in thread.", ex);
				} finally {
					if (c != null) {
						mHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}

		private void doDraw(Canvas canvas) {
			if (canvas != null) {
				canvas.drawBitmap(mBackImage[mCount % 4], 20, 270, null);
	
				mFrontImage[mCount % 4].setBounds(70, 320,
						70 + mImageWidth, 320 + mImageHeight);
				mFrontImage[mCount % 4].draw(canvas);
			}
		}

	}

}
