package kr.co.company.opengltest1;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class OpenGLTest1Activity extends Activity {
	private MyGLSurfaceView mGLView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGLView = new MyGLSurfaceView(this);
		setContentView(mGLView);
	}
	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}
}
