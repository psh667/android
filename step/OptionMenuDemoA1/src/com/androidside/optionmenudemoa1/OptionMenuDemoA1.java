package com.androidside.optionmenudemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class OptionMenuDemoA1 extends Activity {
    public static final int MENU1 = Menu.FIRST + 1;
    public static final int MENU2 = Menu.FIRST + 2;
    public static final int MENU3 = Menu.FIRST + 3;
    public static final int MENU4 = Menu.FIRST + 4;
    public static final int MENU5 = Menu.FIRST + 5;
    public static final int MENU6 = Menu.FIRST + 6;
    public static final int MENU7 = Menu.FIRST + 7;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU1, Menu.NONE, "메뉴1").setIcon(R.drawable.icon);
        menu.add(Menu.NONE, MENU2, Menu.NONE, "메뉴2");  
        menu.add(Menu.NONE, MENU3, Menu.NONE, "메뉴3");
        menu.add(Menu.NONE, MENU4, Menu.NONE, "메뉴4");
        menu.add(Menu.NONE, MENU5, Menu.NONE, "메뉴5");
        menu.add(Menu.NONE, MENU6, Menu.NONE, "메뉴6");
        menu.add(Menu.NONE, MENU7, Menu.NONE, "메뉴7");
        
        return true;
    }    
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        
        return false;
    }
}