package com.appstudio.android.sample.ch_21;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Mp3Activity extends Activity {

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.atv_mp3);
	  }
	  
	  public void btn_onclick(View v) {
		  
	    if(v.getId() == R.id.btn_start){
	      startService(new Intent(Mp3Activity.this, Mp3Service.class));
	    }else if(v.getId() == R.id.btn_stop){
	      stopService(new Intent(Mp3Activity.this, Mp3Service.class));
	    }
	  }
}
