package com.appstudio.android.sample.ch_9;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class SubMenuActivity extends Activity {
	private TextView resultView;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submenumain);
		resultView = (TextView) findViewById(R.id.resultView);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.sub_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.add:
	        resultView.setText("select add MenuItem");
	        return true;
	    case R.id.call:
	        resultView.setText("select call MenuItem");
	        return true;
	    case R.id.camera:
	        resultView.setText("select camera MenuItem");
	        return true;
	    case R.id.edit:
	        resultView.setText("select edit MenuItem");
	        return true;
	    case R.id.exit:
	        resultView.setText("select exit MenuItem");
	        return true;
	    case R.id.trash:
	        resultView.setText("select trash MenuItem");
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
