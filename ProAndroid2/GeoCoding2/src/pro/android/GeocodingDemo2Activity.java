package pro.android;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.maps.GeoPoint; import com.google.android.maps.MapActivity; import  com.google.android.maps.MapView;

public class GeocodingDemo2Activity extends MapActivity
{
	Geocoder  geocoder = null;
	MapView  mapView  = null;
	ProgressDialog progDialog=null;
	List<Address> addressList=null;
	
	@Override
	protected  boolean isLocationDisplayed()  {
		return false;
	}
	
	@Override
	protected  boolean isRouteDisplayed()  {
		return false;
	}
	
	@Override
	protected void  onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.geocode);
		mapView  = (MapView)findViewById(R.id.geoMap);
		mapView.setBuiltInZoomControls(true);
		
		// Jacksonville, FL의 위도/경도
		int lat = (int)(30.334954*1000000);
		int lng  = (int)(-81.5625*1000000);
		GeoPoint  pt = new GeoPoint(lat,lng);
		mapView.getController().setZoom(10);
		mapView.getController().setCenter(pt);
		
		Button   geoBtn  =(Button)findViewById(R.id.geocodeBtn);
		geocoder = new Geocoder(this);
		geoBtn.setOnClickListener(new OnClickListener(){
		@Override
		public void  onClick(View view)  {
			EditText loc   = (EditText)findViewById(R.id.location);
			String  locationName = loc.getText().toString();
			
			progDialog = ProgressDialog.show(GeocodingDemo2Activity.this, "처리 중...", "위치 검색...", true, false);
			
			findLocation(locationName);
			}});
		}
		
		private void  findLocation(final  String  locationName)
		{
			Thread  thrd = new Thread()
			{
				public void  run()
			{
				try  {
				// 백그라운드 작업 수행
				addressList = geocoder.getFromLocationName(locationName, 5);
				// 결과를 처리하도록 핸들러에게 메시지 전송
				uiCallback.sendEmptyMessage(0);
				
				}  catch (IOException e)  {
					e.printStackTrace();
				}
			}
		};
		thrd.start();
	}
	// UI 스레스 콜백 핸들러
	private Handler   uiCallback = new Handler()
	{
		@Override
		public void  handleMessage(Message msg)
		{
			progDialog.dismiss();
			
			if(addressList!=null && addressList.size()>0)
			{
				int lat = (int)(addressList.get(0).getLatitude()*1000000);
				int lng = (int)(addressList.get(0).getLongitude()*1000000);
				GeoPoint pt = new GeoPoint(lat,lng);
				mapView.getController().setZoom(15);
				mapView.getController().setCenter(pt);
			}
			else
			{
				Dialog foundNothingDlg = new
				AlertDialog.Builder(GeocodingDemo2Activity.this)
				.setIcon(0)
				.setTitle("위치 검색 실패")
				.setPositiveButton("확인", null)
				.setMessage("위치가 없습니다...")
				.create();
				foundNothingDlg.show();
			}
		}
	};
}
