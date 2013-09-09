package kr.co.infinity.OverlayTest2;

import java.util.List;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;



public class OverlayTest2 extends MapActivity {
	MapView map;
	MapController controller=null;
	double lattitude=37.0;
	double longitude=127.0;
	int level=8;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		map = (MapView)findViewById(R.id.mapview);
		map.setBuiltInZoomControls(true);
		controller = map.getController();

		GeoPoint myLocation = new GeoPoint((int)lattitude*1000000, (int)longitude*1000000);
		controller.setCenter(myLocation);
		controller.setZoom(15);
		
		NaviOverlay navi = new NaviOverlay();
		List<Overlay> overlays = map.getOverlays();
		overlays.add(navi);
	}
	protected boolean isRouteDisplayed() {
		return false;
	}
	class NaviOverlay extends Overlay {
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);

			Paint paint = new Paint();

			canvas.drawText("경로 안내", 10, 10, paint);

			Projection projection;
			projection = mapView.getProjection();

			Point point1 = projection.toPixels(new GeoPoint((int)lattitude*1000000, (int)longitude*1000000), null);
			Point point2 = projection.toPixels(new GeoPoint((int)(lattitude)*1000000-10000, (int)(longitude)*1000000-10000), null);

			canvas.drawCircle(point1.x, point1.y, 5, paint);
			canvas.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
			canvas.drawCircle(point2.x, point2.y, 5, paint);
		}
	}
}
