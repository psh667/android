package andexam.ver4_1.c32_map;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.location.*;
import android.os.*;
import android.widget.*;

public class GetProvider extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getprovider);

		// 위치 관리자 구함
		LocationManager LocMan = (LocationManager)
			getSystemService(Context.LOCATION_SERVICE);

		// 제공자 목록 구해서 출력
		List<String> arProvider = LocMan.getProviders(false);
		String result = "";
		for (int i = 0; i < arProvider.size(); i++) {
			result += ("Provider " + i + " : " + arProvider.get(i) + "\n");
		}

		// 최적의 제공자 조사
		Criteria crit = new Criteria();
		crit.setAccuracy(Criteria.NO_REQUIREMENT);
		crit.setPowerRequirement(Criteria.NO_REQUIREMENT);
		crit.setAltitudeRequired(false);
		crit.setCostAllowed(false);
		String best = LocMan.getBestProvider(crit, true);
		result += ("\nbest provider : " + best + "\n\n");

		// GPS와 네트워크 제공자 사용 가능성 조사
		result += LocationManager.GPS_PROVIDER + " : " + 
			LocMan.isProviderEnabled(LocationManager.GPS_PROVIDER) + "\n";
		result += LocationManager.NETWORK_PROVIDER  + " : " + 
			LocMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER ) + "\n";

		// 결과 출력
		TextView EditResult =(TextView)findViewById(R.id.result);
		EditResult.setText(result);
	}
}