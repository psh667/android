package org.androidtown.lbs.geocoding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 주소로 위치를 찾거나 위치 좌표를 이용해 주소를 찾는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {
	private static String TAG = "MainActivity";
	
	TextView contentsText;
	Geocoder gc;
	
	EditText edit01;
	EditText edit02;
	EditText edit03;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		edit01 = (EditText) findViewById(R.id.edit01);
		edit02 = (EditText) findViewById(R.id.edit02);
		edit03 = (EditText) findViewById(R.id.edit03);
		contentsText = (TextView) findViewById(R.id.contentsText);

		// 버튼 이벤트 처리
		Button show_btn = (Button) findViewById(R.id.show_btn);
		show_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 사용자가 입력한 주소 정보 확인
				String searchStr = edit01.getText().toString();
				
				// 주소 정보를 이용해 위치 좌표 찾기 메소드 호출
				searchLocation(searchStr);
			}
		});

		// 버튼 이벤트 처리
		Button show_btn2 = (Button) findViewById(R.id.show_btn2);
		show_btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 사용자가 입력한 위치 좌표 확인
				String LatStr = edit02.getText().toString();
				String LonStr = edit03.getText().toString();
				double latitude = 0.0D;
				double longitude = 0.0D;

				try {
					latitude = Double.parseDouble(LatStr);
					longitude = Double.parseDouble(LonStr);
				} catch(NumberFormatException ex) {
					Log.d(TAG, "예외 : " + ex.toString());
				}

				// 위치 좌표를 이용해 주소를 검색하는 메소드 호출
				searchLocation(latitude, longitude);
			}
		});

		// 지오코더 객체 생성
		gc = new Geocoder(this, Locale.KOREAN);

    }

    /**
     * 주소를 이용해 위치 좌표를 찾는 메소드 정의
     */
    private void searchLocation(String searchStr) {
    	// 결과값이 들어갈 리스트 선언
    	List<Address> addressList = null;

    	try {
    		addressList = gc.getFromLocationName(searchStr, 3);

    		if (addressList != null) {
    			contentsText.append("\nCount of Addresses for [" + searchStr + "] : " + addressList.size());
    			for (int i = 0; i < addressList.size(); i++) {
    				Address outAddr = addressList.get(i);
    				int addrCount = outAddr.getMaxAddressLineIndex() + 1;
    				StringBuffer outAddrStr = new StringBuffer();
    				for (int k = 0; k < addrCount; k++) {
    					outAddrStr.append(outAddr.getAddressLine(k));
    				}
    				outAddrStr.append("\n\tLatitude : " + outAddr.getLatitude());
    				outAddrStr.append("\n\tLongitude : " + outAddr.getLongitude());

    				contentsText.append("\n\tAddress #" + i + " : " + outAddrStr.toString());
    			}
    		}

    	} catch(IOException ex) {
    		Log.d(TAG, "예외 : " + ex.toString());
    	}

    }

    /**
     * 위치 좌표를 이용해 주소를 검색하는 메소드 정의
     */
    private void searchLocation(double latitude, double longitude) {
    	List<Address> addressList = null;

    	try {
    		addressList = gc.getFromLocation(latitude, longitude, 3);

    		if (addressList != null) {
    			contentsText.append("\nCount of Addresses for [" + latitude + ", " + longitude + "] : " + addressList.size());
    			for (int i = 0; i < addressList.size(); i++) {
    				Address outAddr = addressList.get(i);
    				int addrCount = outAddr.getMaxAddressLineIndex() + 1;
    				StringBuffer outAddrStr = new StringBuffer();
    				for (int k = 0; k < addrCount; k++) {
    					outAddrStr.append(outAddr.getAddressLine(k));
    				}
    				outAddrStr.append("\n\tLatitude : " + outAddr.getLatitude());
    				outAddrStr.append("\n\tLongitude : " + outAddr.getLongitude());

    				contentsText.append("\n\tAddress #" + i + " : " + outAddrStr.toString());
    			}
    		}

    	} catch(IOException ex) {
    		Log.d(TAG, "예외 : " + ex.toString());
    	}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
