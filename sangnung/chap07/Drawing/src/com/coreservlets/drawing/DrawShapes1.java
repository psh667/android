package com.coreservlets.drawing;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;

public class DrawShapes1 extends Activity {
    private RandomShapeView mDrawingArea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_shapes1);
        mDrawingArea  = (RandomShapeView)findViewById(R.id.drawing_area);
    }
    
    /** Handles events for the button. Redraws the ShapeView. */
    
    public void redraw(View clickedButton) {
        mDrawingArea.invalidate();
    }
}
