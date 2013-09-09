package com.androidside.mapdemoa2;

import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;

public class MapDemoA2 extends MapActivity {
    private MapView mapView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 레이아웃에 추가되어 있는 MapView를 얻는다.
        mapView = (MapView) findViewById(R.id.map);

        // 신림역의 위도와 경도를 지오포인트(GeoPoint)에 설정한다.
        GeoPoint gp = new GeoPoint((int) (37.484203 * 1000000.0),
                (int) (126.929699 * 1000000.0));

        //지정된 gp를 화면 중앙에 오게 위치시킨다.
        mapView.getController().setCenter(gp);
        
        //줌 레벨을 16으로 설정한다.
        mapView.getController().setZoom(16);
                
        //줌 콘트롤을 추가한다.
        mapView.setBuiltInZoomControls(true);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 일반지도와 위성지도를 번갈아 보여준다.
        if (keyCode == KeyEvent.KEYCODE_M) {
            mapView.setSatellite(!mapView.isSatellite());

        // 서울대학교역으로 이동한다.
        } else if (keyCode == KeyEvent.KEYCODE_A) {
            mapView.getController().animateTo(
                    new GeoPoint((int) (37.481102 * 1E6),
                            (int) (126.952803 * 1E6)));

        // 맵에 아이콘을 표시한다.
        } else if (keyCode == KeyEvent.KEYCODE_I) {
            setIcon();

        // 맵에 표시된 모든 아이콘을 제거한다.
        } else if (keyCode == KeyEvent.KEYCODE_C) {
            mapView.removeAllViews();

        // 맵을 이미지로 저장한다.
        } else if (keyCode == KeyEvent.KEYCODE_S) {
            saveBitmap();
        }
        
        return (super.onKeyDown(keyCode, event));
    }

    // 맵에 아이콘을 표시한다.
    public void setIcon() {
        final GeoPoint gp = new GeoPoint(
                mapView.getMapCenter().getLatitudeE6(), mapView.getMapCenter()
                        .getLongitudeE6());

        MapView.LayoutParams mapParams = new MapView.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gp,
                MapView.LayoutParams.CENTER);

        ImageView iv = new ImageView(getApplicationContext());
        iv.setImageResource(R.drawable.pin);
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(
                        v.getContext(),
                        "LatitudeE6: " + gp.getLatitudeE6() + "\nLongitudeE6: "
                                + gp.getLongitudeE6(), Toast.LENGTH_LONG)
                        .show();

            }
        });
        mapView.addView(iv, mapParams);
    }

    // 맵을 이미지로 저장한다.
    private void saveBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(400, 600, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        mapView.draw(canvas);

        try {
            FileOutputStream fos = this.openFileOutput("map.png",
                    Context.MODE_WORLD_WRITEABLE);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}