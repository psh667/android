package pro.android;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class OpenGLTestHarnessActivity extends Activity {
	private GLSurfaceView mTestHarness;
	
	@Override
	protected void  onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		mTestHarness  = new GLSurfaceView(this);
		mTestHarness.setEGLConfigChooser(false);
		//mTestHarness.setRenderer(new SimpleTriangleRenderer(this));
		mTestHarness.setRenderer(new SimpleTriangleRenderer2(this));
		mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		//mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		setContentView(mTestHarness);
	}
	
	@Override
	protected void  onResume() 	{
	super.onResume();
	mTestHarness.onResume();
	}
	
	@Override
	protected void  onPause()  {
		super.onPause();
		mTestHarness.onPause();
	}
}
