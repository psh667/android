package kr.co.company.opengltest1;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class MyRenderer implements Renderer {
	
	public MyRenderer() {
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {		
		   gl.glClearColor(0.5f, 0.0f, 0.0f, 1.0f);	
	}

	public void onDrawFrame(GL10 gl) { 
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height); 	
	}
}
