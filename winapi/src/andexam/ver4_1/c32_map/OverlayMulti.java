package andexam.ver4_1.c32_map;

import andexam.ver4_1.*;
import java.util.*;

import android.graphics.drawable.*;
import android.os.*;
import android.widget.*;

import com.google.android.maps.*;

public class OverlayMulti extends MapActivity {
	MapView mMap;
	Drawable mBlue, mRed;

	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewtest);
		
		mMap = (MapView)findViewById(R.id.mapview);
		MapController mapControl = mMap.getController();
		mapControl.setZoom(16);
		mMap.setBuiltInZoomControls(true);
		
		GeoPoint pt = new GeoPoint(37497900, 127027700);
		mapControl.setCenter(pt);

		mBlue = getResources().getDrawable(R.drawable.bluemarker);
		mBlue.setBounds(0, 0, mBlue.getIntrinsicWidth(),mBlue.getIntrinsicHeight());
		mRed = getResources().getDrawable(R.drawable.redmarker);
		mRed.setBounds(0, 0, mRed.getIntrinsicWidth(), mRed.getIntrinsicHeight());

		Restaurant rest = new Restaurant(mBlue);
		List<Overlay> overlays = mMap.getOverlays();
		overlays.add(rest);
	}
	
	class Restaurant extends ItemizedOverlay<OverlayItem> {
		public Restaurant(Drawable defaultMarker) {
			super(defaultMarker);
			boundCenterBottom(defaultMarker);
			boundCenter(mRed);
			populate();
		}

		/* 이 메서드를 재정의하면 그림자 출력을 제어할 수 있다.
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, false);
		}
		//*/

		public int size() {
			return 5;
		}

		protected OverlayItem createItem(int i) {
			OverlayItem item = null;
			
			switch (i) {
			case 0:
				item = new OverlayItem(new GeoPoint(37497000, 127031100), 
						"할매 칼국수", "바지락 전문");
				break;
			case 1:
				item = new OverlayItem(new GeoPoint(37503000, 127029200), 
						"강남 떡볶이", "물은 셀프");
				break;
			case 2:
				item = new OverlayItem(new GeoPoint(37500300, 127024200), 
						"서초 라면", "김치 무제한");
				item.setMarker(mRed);
				break;
			case 3:
				item = new OverlayItem(new GeoPoint(37494500, 127023700), 
						"한빛 만두", "저렴한 가격");
				break;
			case 4:
				item = new OverlayItem(new GeoPoint(37502800, 127032700), 
						"미영 분식", "친절 봉사");
				item.setMarker(mRed);
				break;
			}
			return  item;
		}
		
		public boolean onTap(int index) {
			String msg;
			OverlayItem item = getItem(index);
			msg = "상호 = " + item.getTitle() + ",설명 = " + item.getSnippet();
			Toast.makeText(OverlayMulti.this, msg, Toast.LENGTH_LONG).show();
			return true;
		}
	}
}

