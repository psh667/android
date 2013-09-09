package andexam.ver4_1.c32_map;

import andexam.ver4_1.*;
import java.util.*;

import android.graphics.*;
import android.os.*;
import android.widget.*;

import com.google.android.maps.*;

public class OverlayView extends MapActivity {
	MapView mMap;

	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewtest);

		mMap = (MapView)findViewById(R.id.mapview);
		MapController mapControl = mMap.getController();
		mapControl.setZoom(13);
		mMap.setBuiltInZoomControls(true);

		GeoPoint pt = new GeoPoint(37927800, 127754100);
		mapControl.setCenter(pt);

		Tourmap tour = new Tourmap();
		List<Overlay> overlays = mMap.getOverlays();
		overlays.add(tour);
	}

	class Tourmap extends Overlay {
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);

			Paint pnt = new Paint();
			Bitmap bit;
			Point pt;

			pnt.setAntiAlias(true);
			pnt.setTextSize(30);
			canvas.drawText("춘천 관광지 안내", 10, 40, pnt);

			Projection projection;
			projection = mapView.getProjection();

			// 춘천 중도
			bit = BitmapFactory.decodeResource(getResources(), R.drawable.jungdo);
			pt = projection.toPixels(new GeoPoint(37888800, 127703500), null);
			canvas.drawBitmap(bit, pt.x, pt.y, pnt);

			// 인형 극장
			bit = BitmapFactory.decodeResource(getResources(), R.drawable.inhyung);
			pt = projection.toPixels(new GeoPoint(37920200, 127722800), null);
			canvas.drawBitmap(bit, pt.x, pt.y, pnt);

			// 소양댐
			bit = BitmapFactory.decodeResource(getResources(), R.drawable.soyangdam);
			pt = projection.toPixels(new GeoPoint(37945600, 127780800), null);
			canvas.drawBitmap(bit, pt.x, pt.y, pnt);
		}

		public boolean onTap(GeoPoint p, MapView mapView) {
			String msg;
			msg = "x = " + p.getLatitudeE6() + ", y = " + p.getLongitudeE6();
			Toast.makeText(OverlayView.this, msg, Toast.LENGTH_SHORT).show();
			return true;
		}
	}
}

