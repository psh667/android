package com.StarWars;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class About extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.about);

        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(OnButtonClick);
    }

    //-----------------------------------
    // Button Click
    //-----------------------------------
    Button.OnClickListener OnButtonClick = new Button.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnBack :
				finish();
			} // switch
		}
    };
}

