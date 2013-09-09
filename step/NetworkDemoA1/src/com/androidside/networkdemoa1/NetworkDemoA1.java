package com.androidside.networkdemoa1;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class NetworkDemoA1 extends Activity {
    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        text = (TextView) findViewById(R.id.text);

        text.setText(getNetworkState2());
    }

    public String getNetworkState() {
        ConnectivityManager connMgr = 
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        String typeName = networkInfo.getTypeName();
        String state = "";

        if (typeName.equalsIgnoreCase("WIFI")) {
            state = networkInfo.getState().toString();
        } else if (typeName.equalsIgnoreCase("MOBILE")) {
            state = networkInfo.getDetailedState().toString();
        }

        return state;
    }

    public String getNetworkState2() {
        ConnectivityManager connMgr = 
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        String state = "";
/*Log.d("tag", "type : " + networkInfo.getType());
Log.d("tag", "subtype : " + networkInfo.getSubtype());
Log.d("tag", "subtypeName : " + networkInfo.getSubtypeName());*/
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            state = networkInfo.getState().toString();
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            state = networkInfo.getDetailedState().toString();
        }

        return state;
    }
}