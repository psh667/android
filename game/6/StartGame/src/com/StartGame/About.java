
//-------------------------------------
// About - StartGame에서 호출
//-------------------------------------
package com.StartGame;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class About extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        findViewById(R.id.ImageView01).setOnClickListener(ButtonClick);
    }

    //------------------------------------
    // Button Click
    //------------------------------------
    OnClickListener ButtonClick = new Button.OnClickListener() {
		public void onClick(View view) {
			finish();
		}
	};
}