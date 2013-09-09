package org.androidtown.tutorial.graphic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 서피스뷰를 이용하는 페인트보드를 보여주기 위한 액티비티
 * 
 * @author Mike
 *
 */
public class PaintBoardSurfaceActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        PaintBoardSurface board = new PaintBoardSurface(this);
        setContentView(board);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
