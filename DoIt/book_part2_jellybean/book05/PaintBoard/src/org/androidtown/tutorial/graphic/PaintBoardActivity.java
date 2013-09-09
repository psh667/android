package org.androidtown.tutorial.graphic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * PaintBoard를 보여주기 위한 액티비티
 * 
 * @author Mike
 *
 */
public class PaintBoardActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        PaintBoard board = new PaintBoard(this);
        setContentView(board);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
