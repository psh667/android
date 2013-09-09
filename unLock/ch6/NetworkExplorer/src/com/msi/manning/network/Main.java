package com.msi.manning.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity {

    private Button socketButton;
    private Button getButton;
    private Button apacheButton;
    private Button apacheViaHelperButton;
    private Button helperFormButton;
    private Button deliciousButton;
    private Button gClientLoginButton;

    private TextView status;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.main);

        this.socketButton = (Button) findViewById(R.id.main_socket_button);
        this.socketButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                startActivity(new Intent(Main.this, SimpleSocket.class));
            }
        });

        this.getButton = (Button) findViewById(R.id.main_get_button);
        this.getButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                startActivity(new Intent(Main.this, SimpleGet.class));
            }
        });

        this.apacheButton = (Button) findViewById(R.id.main_apache_button);
        this.apacheButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                startActivity(new Intent(Main.this, ApacheHTTPSimple.class));
            }
        });

        this.apacheViaHelperButton = (Button) findViewById(R.id.main_apacheviahelper_button);
        this.apacheViaHelperButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                startActivity(new Intent(Main.this, ApacheHTTPViaHelper.class));
            }
        });

        this.helperFormButton = (Button) findViewById(R.id.main_helper_button);
        this.helperFormButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                startActivity(new Intent(Main.this, HTTPHelperForm.class));
            }
        });

        this.deliciousButton = (Button) findViewById(R.id.main_delicious_button);
        this.deliciousButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                startActivity(new Intent(Main.this, DeliciousRecentPosts.class));
            }
        });

        this.gClientLoginButton = (Button) findViewById(R.id.main_gclientlogin_button);
        this.gClientLoginButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                startActivity(new Intent(Main.this, GoogleClientLogin.class));
            }
        });

        this.status = (TextView) findViewById(R.id.main_status);
    }

    @Override
    public void onStart() {
        super.onStart();

        ConnectivityManager cMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
        this.status.setText(netInfo.toString());
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
