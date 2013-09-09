package kr.co.infinity.SubMenuTest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

public class SubMenuTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
			SubMenu sub = menu.addSubMenu("file");
			sub.add(0, 1, 0, "new");
			sub.add(0, 2, 0, "open");
		return true;
	}
   
}