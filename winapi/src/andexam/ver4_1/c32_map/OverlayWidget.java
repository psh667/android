package andexam.ver4_1.c32_map;

import andexam.ver4_1.*;
import android.graphics.*;
import android.os.*;
import android.widget.*;

import com.google.android.maps.*;
import com.google.android.maps.MapView.*;

public class OverlayWidget extends MapActivity {
	MapView mMap;

	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewtest);

		mMap = (MapView)findViewById(R.id.mapview);
		MapController mapControl = mMap.getController();
		mapControl.setZoom(17);
		mMap.setSatellite(false);
		mMap.setBuiltInZoomControls(true);

		GeoPoint pt = new GeoPoint(37554644, 126970700);
		mapControl.setCenter(pt);

		// 서울역 좌표에 표식 배치
		ImageView dest = new ImageView(this);
		dest.setImageResource(R.drawable.marker);
		MapView.LayoutParams lp = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				pt, MapView.LayoutParams.CENTER);
		mMap.addView(dest, lp);

		// 상단에 설명 문자열 배치
		TextView title = new TextView(this);
		title.setText("서울역");
		title.setTextSize(40);
		title.setTextColor(Color.BLACK);
		MapView.LayoutParams lp2 = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				10,10, MapView.LayoutParams.LEFT | MapView.LayoutParams.TOP);
		mMap.addView(title, lp2);
	}
}

