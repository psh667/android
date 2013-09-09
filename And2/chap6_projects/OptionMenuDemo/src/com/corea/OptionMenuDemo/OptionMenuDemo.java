package com.corea.OptionMenuDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class OptionMenuDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.setQwertyMode(true);
        MenuItem mnu1 = menu.add(0, 0, 0, "항목 0");
        {
            mnu1.setAlphabeticShortcut('a');          
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "항목 1");
        {
            mnu2.setAlphabeticShortcut('b');
            //mnu2.setNumericShortcut('1'); //??
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "항목 2");
        {
            mnu3.setAlphabeticShortcut('c');
            mnu3.setIcon(R.drawable.icon);
        }
        MenuItem mnu4 = menu.add(0, 3, 3, "항목 3");
        {
            mnu4.setAlphabeticShortcut('d');                    
        }
        menu.add(0, 4, 4, "항목 4");
        menu.add(0, 5, 5, "항목 5");
        menu.add(0, 6, 6, "항목 6");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	if (item.getItemId() >= 0 && item.getItemId() <= 6) {
    		Toast.makeText(this, "항목 " + item.getItemId() + " 누름", 
                    Toast.LENGTH_LONG).show();
    		return true;
    	}
    	else
    		return false;
    }
}
