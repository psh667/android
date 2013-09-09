package com.appstudio.android.sample.ch_9;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;

public class ContextMenuActivity extends Activity {
	private TextView resultView;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contextmenumain);
		registerForContextMenu(this.findViewById(R.id.long_press));
		resultView = (TextView) findViewById(R.id.resultView);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  switch (item.getItemId()) {
	  case R.id.add:
		  	resultView.setText("추가 메뉴를  선택하였습니다.");
		    return true;
	  case R.id.call:
		    resultView.setText("전화 메뉴를 선택하였습니다.");
		    return true;
	  case R.id.camera:
		    resultView.setText("카메라 메뉴를 선택하였습니다.");
		    return true;
	  case R.id.edit:
		    resultView.setText("편집 메뉴를  선택하였습니다.");
		    return true;
	  case R.id.exit:
		  	resultView.setText("종료 메뉴를 선택하였습니다.");
		    return true;
	  case R.id.trash:
		  	resultView.setText("휴지통 메뉴를 선택하였습니다");
		    return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
}
