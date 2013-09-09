package com.appstudio.android.sample.ch_28_2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.appstudio.android.sample.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MyMapActivity extends MapActivity 
                                  implements LocationListener {
	
  private static final int TWO_MINUTES = 1000 * 60 * 2;
  private static final String TAG = "appstudio";
  private MapView mMapView;
  private LocationManager mLocationManager;
  private MapController mMapController;
  private Location mLocation;
  private MyItemizedOverlay mItemizedoverlay;
  private CustomLocationOverlay mLocationOverlay;
	
  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);
    setContentView(R.layout.my_map_activity);
    mMapView = (MapView) findViewById(R.id.myGMap);
    mLocationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
	
    mMapController = mMapView.getController();
    mMapView.setBuiltInZoomControls(true);
    List<Overlay> mapOverlays = mMapView.getOverlays();
    Drawable drawable = this.getResources().
                                getDrawable(R.drawable.office);
    
    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                                drawable.getIntrinsicHeight());
    
    mItemizedoverlay = new MyItemizedOverlay(drawable, this);
    OverlayItem item1 = new OverlayItem(
                             new GeoPoint(37498006, 127026447),
                             "씨너스 강남", 
                             "가본적 없는 영화관이다.");

    OverlayItem item2 = new OverlayItem(
                            new GeoPoint(37654827, 127061198 ),
                            "롯데백화점 노원", 
                            "자주가는 영화관이다.");
	
    mItemizedoverlay.addOverlayItem(item1);
    mItemizedoverlay.addOverlayItem(item2);
    mItemizedoverlay.pupulate();
    mLocationOverlay = new CustomLocationOverlay();
    mapOverlays.add(mItemizedoverlay);
    mapOverlays.add(mLocationOverlay);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mLocationManager.removeUpdates(this);		
  }

  @Override
  protected void onResume() {
    super.onResume();
    Criteria criteria = new Criteria();
    String strProvider = 
              mLocationManager.getBestProvider(criteria, true);

    mLocation = 
            mLocationManager.getLastKnownLocation(strProvider);

    mLocationManager.requestLocationUpdates(strProvider, 
                                                   0, 0, this);
  }


  @Override
  protected boolean isRouteDisplayed() {
    return false;
  }

  @Override
  public void onLocationChanged(Location location) {
    if (location != null) {
      Log.d(TAG, "location changed");
      if(isBetterLocation(location, mLocation))  {
        mLocation = location;
        GeoPoint geoPoint = getGeoPoint(location);
        mMapController.animateTo(geoPoint);
      }
    }
  }

  @Override
  public void onProviderDisabled(String provider) {}

  @Override
  public void onProviderEnabled(String provider) {}

  @Override
  public void onStatusChanged(String provider, int status, 
                                              Bundle extras) {}
	
  private boolean isBetterLocation(Location location,
                                Location currentBestLocation) {
    
    if (currentBestLocation == null) {
      // 최선의 위치 정보가 없으면 새로운 위치 정보는 
      // 무조건 최선의 위치 정보가 된다.
      return true;
    }

    // 새로운 위치 정보가 너무 옛날 정보인지, 
    // 아주 최신 정보인지, 비교적 최신 정보인지를 구분
    long timeDelta = location.getTime() - 
                                 currentBestLocation.getTime();
    
    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
    boolean isSignificantlyOlder = timeDelta <-TWO_MINUTES;
    boolean isNewer = timeDelta > 0;

    // 새로운 위치가 유지하고 있던 최선의 위치보다 
    // 2분 이상 최신이면 새로운 위치 정보를 사용
    if (isSignificantlyNewer) {
      return true;
      // 새로운 위치가 유지하던 최선의 위치보다 2분 이상 오래되었으면 
      // 유지하던 최선의 위치 사용
    } else if (isSignificantlyOlder) {
      return false;
    }

    // 어느 위치의 정확도가 더 높은지, 
    // 새로운 위치의 정확도가 매우 떨어지는지를 식별
    int accuracyDelta = (int) (location.getAccuracy() - 
                            currentBestLocation.getAccuracy());

    boolean isLessAccurate = accuracyDelta > 0;
    boolean isMoreAccurate = accuracyDelta < 0;
    
    boolean isSignificantlyLessAccurate = accuracyDelta > 200;
  
    // 위치 제공자가 같은지를 식별
    boolean isFromSameProvider = 
            isSameProvider(location.getProvider(),
                            currentBestLocation.getProvider());

    // 시간과 정확도를 기반으로 새로운 위치를 사용할지를 결정
    if (isMoreAccurate) {
      return true;
    } else if (isNewer && !isLessAccurate) {
      return true;
    } else if (isNewer && !isSignificantlyLessAccurate 
                                       && isFromSameProvider) {
      
      return true;
    }
    return false;
  }


  private boolean isSameProvider(String provider1, 
                                            String provider2) {
    
    if (provider1 == null) {
      return provider2 == null;
    }
    return provider1.equals(provider2);
  }	

  private GeoPoint getGeoPoint(Location location) {
    if (location == null) {
      return null;
    }
    Double lat = location.getLatitude() * 1E6;
    Double lng = location.getLongitude() * 1E6;
    return new GeoPoint(lat.intValue(), lng.intValue());
  }
  
  class MyItemizedOverlay extends 
                                 ItemizedOverlay<OverlayItem> {
    
    private ArrayList<OverlayItem> mOverlays = 
                                  new ArrayList<OverlayItem>();
    
    private Context mContext;
    public MyItemizedOverlay(Drawable arg0, 
                                             Context context) {
      
      super(boundCenterBottom(arg0));
      mContext = context;
    }

    @Override
    protected OverlayItem createItem(int arg0) {
      Log.d(TAG, "create "+arg0);
      return mOverlays.get(arg0);
    }

    @Override
    public int size() {
      return mOverlays.size();
    }
    
    @Override
    public boolean onTap(int i) {
      Toast.makeText(mContext, mOverlays.get(i).getSnippet(),
                                     Toast.LENGTH_LONG).show();
      
      return true;
    }

    @Override
    public boolean onTap(GeoPoint arg0, MapView arg1) {
      return super.onTap(arg0, arg1);
    }

    public void addOverlayItem(OverlayItem overlay) {
      mOverlays.add(overlay);
    }	
		
    public void pupulate()  {
      populate();
    }
}
	
  private class CustomLocationOverlay extends Overlay {
    
    @Override
    public boolean draw(Canvas canvas, MapView mapView, 
                                   boolean shadow, long when) {
      
      super.draw(canvas, mapView, shadow);
      Paint paint = new Paint();
	
      Point myScreenCoords = new Point();

      mapView.getProjection().toPixels(getGeoPoint(mLocation), 
                                               myScreenCoords);
      
      paint.setStrokeWidth(1);
      paint.setColor(Color.BLUE);
      paint.setStyle(Paint.Style.STROKE);
      
      Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                                            R.drawable.marker);
      
      canvas.drawBitmap(bmp, myScreenCoords.x, 
                                      myScreenCoords.y, paint);
      
      canvas.drawText("나의 현재 위치", myScreenCoords.x, 
                                      myScreenCoords.y, paint);
      
      return true;
    }
  }

}
