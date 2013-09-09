package com.corea.HelloImage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class HelloImage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.android);
        setContentView(image);
    }
}