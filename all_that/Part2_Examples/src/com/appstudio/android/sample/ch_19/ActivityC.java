package com.appstudio.android.sample.ch_19;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ActivityC extends Activity {

    private String mActivityName;
    private TextView mStatusView;
    private Tracker mTracker = Tracker.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        mActivityName = getString(R.string.activity_c_label);
        mStatusView = (TextView)findViewById(R.id.status_view_c);
        
        mTracker.setStatus(mActivityName, getString(R.string.on_create));
        Utils.printStatus(mStatusView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.setStatus(mActivityName, getString(R.string.on_start));
        Utils.printStatus(mStatusView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mTracker.setStatus(mActivityName, getString(R.string.on_restart));
        Utils.printStatus(mStatusView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setStatus(mActivityName, getString(R.string.on_resume));
        Utils.printStatus(mStatusView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTracker.setStatus(mActivityName, getString(R.string.on_pause));
        Utils.printStatus(mStatusView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTracker.setStatus(mActivityName, getString(R.string.on_stop));
        Utils.printStatus(mStatusView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTracker.setStatus(mActivityName, getString(R.string.on_destroy));
        Utils.printStatus(mStatusView);
    }

    public void startActivityA(View v) {
        Intent intent = new Intent(ActivityC.this, ActivityA.class);
        startActivity(intent);
    }

    public void startActivityB(View v) {
        Intent intent = new Intent(ActivityC.this, ActivityB.class);
        startActivity(intent);
    }

    public void finishActivityC(View v) {
        ActivityC.this.finish();
    }
}
