package kr.co.company.opengltest2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class MyRenderer implements Renderer {
	private Triangle mTriangle;
	
	public MyRenderer() {
		mTriangle = new Triangle();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST); 
		gl.glShadeModel(GL10.GL_SMOOTH); 			
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	
		gl.glClearDepthf(1.0f); 					
		gl.glEnable(GL10.GL_DEPTH_TEST); 	
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
		gl.glLoadIdentity();					
		gl.glTranslatef(0.0f, 0.0f, -6.0f);	
		mTriangle.draw(gl);					
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height); 	
		gl.glMatrixMode(GL10.GL_PROJECTION); 	
		gl.glLoadIdentity(); 					
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW); 	
		gl.glLoadIdentity(); 				
	}
}
