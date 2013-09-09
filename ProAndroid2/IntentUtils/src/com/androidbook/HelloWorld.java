package com.androidbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HelloWorld extends Activity 
{
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 

    TextView tv = new TextView(this); 
    tv.setText("안녕, 안드로이드. 안녕 해 봐"); 
    setContentView(tv); 
    registerMenu(this.getTextView());
  }

  private void registerMenu(Object textView) {
	// TODO Auto-generated method stub
	
}

private Object getTextView() {
	// TODO Auto-generated method stub
	return null;
}

@Override 
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu); 
    int base=Menu.FIRST;  // 값은 1이다 
    //MenuItem item1 = 
    menu.add(base,base,base,"테스트"); 
    return true; 
  }
  @Override 
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == 1) {
      IntentsUtils.tryOneOfThese(this); 
    }
    else {
      return super.onOptionsItemSelected(item);
    }
    return true; 
  }
}

