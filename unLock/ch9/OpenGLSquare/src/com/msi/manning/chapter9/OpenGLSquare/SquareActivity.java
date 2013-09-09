package com.msi.manning.chapter9.OpenGLSquare;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.app.Activity;
import android.opengl.GLU;
import android.os.Bundle;

/* imports for creating a SurfaceView */
import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.*;


public class SquareActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(new DrawingSurfaceView(this));
	}

	class DrawingSurfaceView extends SurfaceView implements SurfaceHolder.Callback  {

		public SurfaceHolder mHolder;

		public DrawingThread mThread;

		public DrawingSurfaceView(Context c) {
			super(c);
			init();
		}
		public void init() {
	
			mHolder = getHolder();

			mHolder.addCallback(this);
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
		}

		public void surfaceCreated(SurfaceHolder holder) {
	
			mThread = new DrawingThread();
			mThread.start();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {

			mThread.waitForExit();
			mThread = null;
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

			mThread.onWindowResize(w, h);
		}

		class DrawingThread extends Thread {
			boolean stop;
			int w;
			int h;
			
			boolean changed = true; 

			DrawingThread() {
				super();
				stop = false;
				w = 0;
				h = 0;
			}

		
			public void run() {
			
				EGL10 egl = (EGL10)EGLContext.getEGL();

				EGLDisplay dpy = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

				int[] version = new int[2];
				egl.eglInitialize(dpy, version);

				int[] configSpec = {
						EGL10.EGL_RED_SIZE,      5,
						EGL10.EGL_GREEN_SIZE,    6,
						EGL10.EGL_BLUE_SIZE,     5,
						EGL10.EGL_DEPTH_SIZE,   16,
						EGL10.EGL_NONE
				};
				EGLConfig[] configs = new EGLConfig[1];
				int[] num_config = new int[1];
				egl.eglChooseConfig(dpy, configSpec, configs, 1, num_config);
				EGLConfig config = configs[0];


				EGLContext context = egl.eglCreateContext(dpy, config,
						EGL10.EGL_NO_CONTEXT, null);

				EGLSurface surface = null;
				GL10 gl = null;

				// now draw forever until asked to stop
				while( ! stop ) {
					int W, H; // copies of the current width and height
					boolean updated;
					synchronized(this) {
						updated = this.changed;
						W = this.w;
						H = this.h;
						this.changed = false;
					}
					if (updated) {
						/*
						 * The window size has changed, so we need to create a
						 * new surface.
						 */
						if (surface != null) {

							/*
							 * unbind and destroy the old EGL surface, if there
							 * is one.
							 */
							egl.eglMakeCurrent(dpy, EGL10.EGL_NO_SURFACE,
									EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
							egl.eglDestroySurface(dpy, surface);
						}

					
						surface = egl.eglCreateWindowSurface(dpy, config,
								mHolder, null);

						egl.eglMakeCurrent(dpy, surface, surface, context);

						gl = (GL10) context.getGL();

						gl.glDisable(GL10.GL_DITHER);

						gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
								GL10.GL_FASTEST);

						gl.glClearColor(1, 1, 1, 1);
						gl.glEnable(GL10.GL_CULL_FACE);
						gl.glShadeModel(GL10.GL_SMOOTH);
						gl.glEnable(GL10.GL_DEPTH_TEST);

						gl.glViewport(0, 0, W, H);


						float ratio = (float) W / H;
						gl.glMatrixMode(GL10.GL_PROJECTION);
						gl.glLoadIdentity();
						gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
					}
				
					drawFrame(gl);

					egl.eglSwapBuffers(dpy, surface);

					if (egl.eglGetError() == EGL11.EGL_CONTEXT_LOST) {
			
						Context c = getContext();
						if (c instanceof Activity) {
							((Activity)c).finish();
						}
					}
				}

				egl.eglMakeCurrent(dpy, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
						EGL10.EGL_NO_CONTEXT);
				egl.eglDestroySurface(dpy, surface);
				egl.eglDestroyContext(dpy, context);
				egl.eglTerminate(dpy);


			}

			public void onWindowResize(int w, int h) {
				synchronized(this) {
					this.w = w;
					this.h = h;
					this.changed = true;
				}
			}

			public void waitForExit() {
				stop = true;
				try {
					join();
				} catch (InterruptedException ex) {
				}
			}

			private void drawFrame(GL10 gl) {
			

				gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


				float[] square = new float[] { 	0.25f, 0.25f, 0.0f,
						0.75f, 0.25f, 0.0f,
						0.25f, 0.75f, 0.0f,
						0.75f, 0.75f, 0.0f };

				FloatBuffer squareBuff;

				ByteBuffer bb = ByteBuffer.allocateDirect(square.length*4);
				bb.order(ByteOrder.nativeOrder());
				squareBuff = bb.asFloatBuffer();
				squareBuff.put(square);
				squareBuff.position(0);

				gl.glMatrixMode(GL10.GL_PROJECTION);
				gl.glLoadIdentity();
				GLU.gluOrtho2D(gl, 0.0f,1.2f,0.0f,1.0f);

				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, squareBuff);
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

				gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				gl.glColor4f(0,1,1,1);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);



			}
		}
	}
}
