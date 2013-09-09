package org.nashorn.exam0601;

import android.app.Activity;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;

public class Exam0601 extends Activity {
	MultitouchView	mMultitouchView;
	private final static int NORMAL_MODE = 1;
	private final static int ZOOM_MODE = 2;
	private float oldDistance;
	private float newDistance;
	private int mode = NORMAL_MODE;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mMultitouchView = new MultitouchView(this);
        
        setContentView(mMultitouchView);
    }
    
    public boolean onTouchEvent(MotionEvent event)
    {
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_POINTER_2_DOWN :
	    	{
	    		float x = event.getX(0) - event.getX(1);
			   	float y = event.getY(0) - event.getY(1);
			   	oldDistance = FloatMath.sqrt(x * x + y * y);
			   	newDistance = oldDistance;
			   	if (oldDistance > 10f)
			   	{
			   		mode = ZOOM_MODE;
			   	}
			   	else
			   	{
			   		mode = NORMAL_MODE;
			   		oldDistance = 0.0f;
			   		newDistance = 0.0f;
			   	}
	    	}
			break;
    	case MotionEvent.ACTION_MOVE:
	    	{
	    		if (mode == ZOOM_MODE)
	    		{
	    			float x = event.getX(0) - event.getX(1);
	    		   	float y = event.getY(0) - event.getY(1);
	    		   	newDistance = FloatMath.sqrt(x * x + y * y);
	    		   	if (newDistance > 10f)
				   	{
	    		   		float scale = newDistance / oldDistance;
	    		   		mMultitouchView.setScale(scale);
	    		   		mMultitouchView.invalidate();
				   	}
				   	else
				   	{
				   		mode = NORMAL_MODE;
				   		oldDistance = 0.0f;
				   		newDistance = 0.0f;
				   	}
	    		}
	    	}
	    	break;
    	}
    	
   		return super.onTouchEvent(event);
    }
}