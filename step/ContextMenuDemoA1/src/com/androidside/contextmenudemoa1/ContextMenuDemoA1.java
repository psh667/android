package com.androidside.contextmenudemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ContextMenuDemoA1 extends Activity {
    public static final int MENU_ONE = Menu.FIRST + 1;
    public static final int MENU_TWO = Menu.FIRST + 2;
    public static final int MENU_THREE = Menu.FIRST + 3;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.button);
        registerForContextMenu(button);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, MENU_ONE, Menu.NONE, "RED");
        menu.add(Menu.NONE, MENU_TWO, Menu.NONE, "GREEN");
        menu.add(Menu.NONE, MENU_THREE, Menu.NONE, "BLUE");
    }

    public boolean onContextItemSelected(MenuItem item) {
        Log.d("tag", "onContextItemSelected "+item.getItemId());
        String selectedMenu = null;

        switch (item.getItemId()) {
            case MENU_ONE:      selectedMenu = "menu_one";
            case MENU_TWO:      selectedMenu = "menu_two";
            case MENU_THREE:    selectedMenu = "menu_three";
        }
        
        Toast.makeText(this, selectedMenu, Toast.LENGTH_SHORT).show();

        return false;
    }

}
