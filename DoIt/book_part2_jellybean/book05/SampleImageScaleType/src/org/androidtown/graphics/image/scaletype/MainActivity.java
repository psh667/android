package org.androidtown.graphics.image.scaletype;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 이미지를 보여줄 때 scaleType 을 지정하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        ImageView image01 = (ImageView) findViewById(R.id.image01);
        ImageView image02 = (ImageView) findViewById(R.id.image02);
        ImageView image03 = (ImageView) findViewById(R.id.image03);
        ImageView image04 = (ImageView) findViewById(R.id.image04);
        ImageView image05 = (ImageView) findViewById(R.id.image05);
        ImageView image06 = (ImageView) findViewById(R.id.image06);
        ImageView image07 = (ImageView) findViewById(R.id.image07);
        ImageView image08 = (ImageView) findViewById(R.id.image08);

        image01.setScaleType(ScaleType.CENTER);
        image02.setScaleType(ScaleType.CENTER_CROP);
        image03.setScaleType(ScaleType.CENTER_INSIDE);
        image04.setScaleType(ScaleType.FIT_CENTER);
        image05.setScaleType(ScaleType.FIT_END);
        image06.setScaleType(ScaleType.FIT_START);
        image07.setScaleType(ScaleType.FIT_XY);
        image08.setScaleType(ScaleType.MATRIX);


        Matrix matrix = new Matrix();
        matrix.postRotate(45.0F);

        image08.setImageMatrix(matrix);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
