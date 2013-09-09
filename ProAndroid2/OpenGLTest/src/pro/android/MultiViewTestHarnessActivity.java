package pro.android;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MenuItem;

public  class  MultiViewTestHarnessActivity extends Activity  {
	private GLSurfaceView mTestHarness;
	@Override
	protected void  onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
	
		mTestHarness  = new GLSurfaceView(this);
		mTestHarness.setEGLConfigChooser(false);
	
		Intent intent = getIntent();
		int mid = intent.getIntExtra("com.ai.menuid",  R.id.MenuId_OpenGL15_Current);
		if (mid  == R.id.MenuId_OpenGL15_Current)
		{
			mTestHarness.setRenderer(new TexturedPolygonRenderer(this));
			mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
			setContentView(mTestHarness);
			return;
		}
	
		if (mid  == R.id.mid_OpenGL15_SimpleTriangle)
		{
			mTestHarness.setRenderer(new SimpleTriangleRenderer(this));
			mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			setContentView(mTestHarness);
			return;
		}
		if (mid  == R.id.mid_OpenGL15_AnimatedTriangle15)
		{
			mTestHarness.setRenderer(new AnimatedSimpleTriangleRenderer(this));
			setContentView(mTestHarness);
			return;
		}
		if (mid  == R.id.mid_rectangle)
		{
			mTestHarness.setRenderer(new SimpleRectangleRenderer(this));
			mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			setContentView(mTestHarness);
			return;
		}
		if (mid  == R.id.mid_square_polygon)
		{
			mTestHarness.setRenderer(new SquareRenderer(this));
			mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			setContentView(mTestHarness);
			return;
		}
		if (mid  == R.id.mid_polygon)
		{
			mTestHarness.setRenderer(new PolygonRenderer(this));
			setContentView(mTestHarness);
			return;
		}
		if (mid  == R.id.mid_textured_square)
		{
			mTestHarness.setRenderer(new TexturedSquareRenderer(this));
			mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			setContentView(mTestHarness);
			return;
		}
		// 위의 모든 if에 해당되지 않을 땐 다음을 수행
		mTestHarness.setRenderer(new TexturedPolygonRenderer(this));
		mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		setContentView(mTestHarness);
		return;
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
	
	
	@Override
	public  boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.mid_OpenGL15_SimpleTriangle)
		{
			// 이 메뉴 항목을 로컬에서 다른 용도로 사용할 수도 있는
			// 메인 액티비티를 가리키게 함
			return true;
		}
		// 이 메뉴 항목들은 다중 뷰를 가리키게 함
		this.invokeMultiView(item.getItemId());
		return true;
	}

	// 메뉴 ID를 지닌 로딩 인텐트를 통해 다중 뷰를 호출
	// mid: 메뉴 ID
	private void  invokeMultiView(int  mid)
	{
		Intent intent = new Intent(this,MultiViewTestHarnessActivity.class);
		intent.putExtra("com.ai.menuid",  mid);
		startActivity(intent);
	}

}
