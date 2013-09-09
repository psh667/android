package kr.co.company.providerlisttest;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class ProviderListTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LocationManager manager = (LocationManager)
			getSystemService(Context.LOCATION_SERVICE);

		TextView output =(TextView)findViewById(R.id.output);

		// 제공자 목록 구해서 출력
		List<String> providers = manager.getAllProviders();
		String s="";
		for (int i = 0; i < providers.size(); i++) {
			s += "위치 제공자  " + providers.get(i) + "의 상태: " + manager.isProviderEnabled(providers.get(i)) + "\n";
		}
		output.setText(s);        
    }
}