package com.coreservlets.drawing;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class DrawingExampleLauncher extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    /** Switches to the DrawShapes1 activity when the associated button is clicked. 
     *  You must also register the new Activity in AndroidManifest.xml. 
     */
    public void launchDrawShapes1(View clickedButton) {
        Intent activityIntent = 
                new Intent(this, DrawShapes1.class);
        startActivity(activityIntent);
    }
    
    /** Switches to the RotateActivity when the associated button is clicked. */
    public void launchRotate(View clickedButton) {
        Intent activityIntent = 
                new Intent(this, RotateActivity.class);
        startActivity(activityIntent);
    }
    
    /** Switches to the SkewActivity when the associated button is clicked. */
    
    public void launchSkew(View clickedButton) {
        Intent activityIntent = 
                new Intent(this, SkewActivity.class);
        startActivity(activityIntent);
    }
    
    /** Switches to the DrawShapes2 activity when the associated button is clicked. */
    
    public void launchDrawShapes2(View clickedButton) {
        Intent activityIntent = 
                new Intent(this, DrawShapes2.class);
        startActivity(activityIntent);
    }
}
