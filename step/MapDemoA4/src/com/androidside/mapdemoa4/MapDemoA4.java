package com.androidside.mapdemoa4;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapDemoA4 extends MapActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MapView mapView = (MapView) findViewById(R.id.map);

        mapView.getController().setZoom(16);
        mapView.setBuiltInZoomControls(true);

        // 신림역 근처의 지오 포인트를 생성한다.
        GeoPoint geoPoint1 = new GeoPoint((int) (37.484203 * 1E6),
                (int) (126.929699 * 1E6));
        GeoPoint geoPoint2 = new GeoPoint((int) (37.485203 * 1E6),
                (int) (126.928699 * 1E6));
        GeoPoint geoPoint3 = new GeoPoint((int) (37.486203 * 1E6),
                (int) (126.927699 * 1E6));

        // 지오 포인트로 오버레이를 생성한다.
        MyOverlay myOverlay1 = new MyOverlay(geoPoint1);
        MyOverlay myOverlay2 = new MyOverlay(geoPoint2);
        MyOverlay myOverlay3 = new MyOverlay(geoPoint3);

        // 생성한 오버레이를 모두 추가한다.
        mapView.getOverlays().add(myOverlay1);
        mapView.getOverlays().add(myOverlay2);
        mapView.getOverlays().add(myOverlay3);

        // 추가된 오버레이를 화면에 표시한다.
        mapView.invalidate();

        // 신림역(geoPoint1)으로 구글 맵 화면을 이동한다.
        mapView.getController().animateTo(geoPoint1);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}

class MyOverlay extends Overlay {
    GeoPoint geoPoint;

    int radius = 10;

    MyOverlay(GeoPoint geoPoint) {
        super();

        this.geoPoint = geoPoint;
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        super.draw(canvas, mapView, shadow);

        if (shadow == false) {
            Projection projection = mapView.getProjection();

            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(4);

Point point = new Point();
projection.toPixels(geoPoint, point);

            // 반지름을 radius로 하여서 원을 그림
            canvas.drawCircle(point.x, point.y, radius, paint);
        }
    }

    // 사용자가 탭(클릭)하였을 때 호출되는 메소드
    @Override
    public boolean onTap(GeoPoint p, MapView mapView) {
        Projection projection = mapView.getProjection();

        // 붉은 점이 그려진 포인트 좌표 구하기
        Point prePoint = new Point();
        projection.toPixels(geoPoint, prePoint);

        // 사용자가 터치한 포인트 좌표 구하기
        Point newPoint = new Point();
        projection.toPixels(p, newPoint);

        // 사용자가 터치한 곳이 붉은 점이 그려진 곳인지 확인하여 토스트로 위도와 경도를 보여줌
        if (newPoint.x <= prePoint.x + radius
                && newPoint.x >= prePoint.x - radius
                && newPoint.y <= prePoint.y + radius
                && newPoint.y >= prePoint.y - radius) {
            String str = "LatitudeE6: " + p.getLatitudeE6() + "\n"
                    + "LongitudeE6: " + p.getLongitudeE6();

            Toast.makeText(mapView.getContext(), str, Toast.LENGTH_SHORT)
                    .show();
        }

        // false로 설정하여 다른 오버레이에서도 탭 이벤트를 처리할 수 있도록 함.
        // true로 설정할 경우 최상단의 오버레이에서만 탭 이벤트가 처리됨
        return false;
    }
}