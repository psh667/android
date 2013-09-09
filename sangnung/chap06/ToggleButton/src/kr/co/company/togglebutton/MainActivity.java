package kr.co.company.togglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ToggleButton togglebutton = (ToggleButton) 
				findViewById(R.id.togglebutton);
			togglebutton.setOnClickListener(new OnClickListener(){ 
				public void onClick(View v){ // 클릭되면 실행된다.     
					if(togglebutton.isChecked()){ 
						Toast.makeText(getApplicationContext(),"Checked",
							Toast.LENGTH_SHORT).show();
					}
					else{ 
						Toast.makeText(getApplicationContext(),"Not checked",
							Toast.LENGTH_SHORT).show();   
					}  
			}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
