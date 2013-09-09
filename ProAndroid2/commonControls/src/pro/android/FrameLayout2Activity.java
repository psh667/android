package pro.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FrameLayout2Activity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.layout_frame2);
	
		ImageView one  = (ImageView)this.findViewById(R.id.oneImgView);
		ImageView two  = (ImageView)this.findViewById(R.id.twoImgView);
	
		one.setOnClickListener(new OnClickListener(){

		@Override
		public void  onClick(View view)  {
			ImageView two = (ImageView)FrameLayout2Activity.this.findViewById(R.id.twoImgView);
			two.setVisibility(View.VISIBLE);
			view.setVisibility(View.GONE);
		}});
	
		two.setOnClickListener(new OnClickListener(){
	
		@Override
		public void  onClick(View view)  {
			ImageView one = (ImageView)FrameLayout2Activity.this.findViewById(R.id.oneImgView);
			one.setVisibility(View.VISIBLE);
			view.setVisibility(View.GONE);
		}});
	}
}
