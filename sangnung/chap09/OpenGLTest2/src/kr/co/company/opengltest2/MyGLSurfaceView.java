package kr.co.company.opengltest2;

import android.content.Context;
import android.opengl.GLSurfaceView;

class MyGLSurfaceView extends GLSurfaceView {
	public MyGLSurfaceView(Context context) {
		super(context);
		setRenderer(new MyRenderer());
	}
}
