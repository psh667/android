package com.paad.whereami;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Location;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyPositionOverlay extends Overlay {

	private final int mRadius = 5;
	Location location;

	public Location getLocation() {
	    return location;
	}

	public void setLocation(Location location) {
	    this.location = location;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	    Projection projection = mapView.getProjection();

	    if (shadow == false && location != null) {
	        // 현재 위치를 얻어온다.
	        Double latitude = location.getLatitude()*1E6;
	        Double longitude = location.getLongitude()*1E6;
	        GeoPoint geoPoint;
	        geoPoint = new GeoPoint(latitude.intValue(),longitude.intValue()); 
	        // 현재 위치를 화면 픽셀로 변환한다.
	        Point point = new Point();
	        projection.toPixels(geoPoint, point);

	        RectF oval = new RectF(point.x - mRadius, point.y - mRadius,
	                               point.x + mRadius, point.y + mRadius);

	        // 페인트를 설정한다.
	        Paint paint = new Paint();
	        paint.setARGB(250, 255, 255, 255);
	        paint.setAntiAlias(true);
	        paint.setFakeBoldText(true);

	        Paint backPaint = new Paint();
	        backPaint.setARGB(175, 50, 50, 50);
	        backPaint.setAntiAlias(true);

	        RectF backRect = new RectF(point.x + 2 + mRadius,
	                                   point.y - 3*mRadius,
	                                   point.x + 65, point.y + mRadius);

	        // 마커를 그린다.
	        canvas.drawOval(oval, paint);
	        canvas.drawRoundRect(backRect, 5, 5, backPaint);
	        canvas.drawText("현재 위치",
                            point.x + 2*mRadius, point.y,
                            paint);
	    }
	    super.draw(canvas, mapView, shadow);
	}

    @Override
    public boolean onTap(GeoPoint point, MapView mapView) {
        return false;
    }
}
