package com.androidside.titlebardemoa3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class TitleBarDemoA3 extends Activity {
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    requestWindowFeature(Window.FEATURE_RIGHT_ICON);
    setContentView(R.layout.main);
    
    setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON, R.drawable.icon);        
}
}