package com.other.manning.chapter5.prefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SharedPrefTestOtherOutput extends Activity {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    public static final String PREFS_WORLD_READ = "PREFS_WORLD_READABLE";
    public static final String PREFS_WORLD_WRITE = "PREFS_WORLD_WRITABLE";
    public static final String PREFS_WORLD_READ_WRITE = "PREFS_WORLD_READABLE_WRITABLE";

    public static final String KEY_PRIVATE = "KEY_PRIVATE";
    public static final String KEY_WORLD_READ = "KEY_WORLD_READ";
    public static final String KEY_WORLD_WRITE = "KEY_WORLD_WRITE";
    public static final String KEY_WORLD_READ_WRITE = "KEY_WORLD_READ_WRITE";

    private static final String LOGTAG = "SharedPrefTestOutput";

    private TextView outputPrivate;
    private TextView outputWorldRead;
    private TextView outputWorldWrite;
    private TextView outputWorldReadWrite;

    private SharedPreferences prefsPrivate;
    private SharedPreferences prefsWorldRead;
    private SharedPreferences prefsWorldWrite;
    private SharedPreferences prefsWorldReadWrite;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.shared_preftest_output);

        this.outputPrivate = (TextView) findViewById(R.id.output_private);
        this.outputWorldRead = (TextView) findViewById(R.id.output_worldread);
        this.outputWorldWrite = (TextView) findViewById(R.id.output_worldwrite);
        this.outputWorldReadWrite = (TextView) findViewById(R.id.output_worldreadwrite);
    }

    @Override
    public void onStart() {
        Log.v(SharedPrefTestOtherOutput.LOGTAG, "onStart");
        super.onStart();
        Context otherAppsContext = null;
        try {
            otherAppsContext = createPackageContext("com.msi.manning.chapter5.prefs", Context.MODE_WORLD_WRITEABLE);
        } catch (NameNotFoundException e) {
            Log.e(SharedPrefTestOtherOutput.LOGTAG, e.getLocalizedMessage());
        }

        this.prefsPrivate = otherAppsContext.getSharedPreferences(SharedPrefTestOtherOutput.PREFS_PRIVATE, 0);
        this.prefsWorldRead = otherAppsContext.getSharedPreferences(SharedPrefTestOtherOutput.PREFS_WORLD_READ, 0);
        this.prefsWorldWrite = otherAppsContext.getSharedPreferences(SharedPrefTestOtherOutput.PREFS_WORLD_WRITE, 0);
        this.prefsWorldReadWrite = otherAppsContext
            .getSharedPreferences(SharedPrefTestOtherOutput.PREFS_WORLD_READ_WRITE, 0);

        this.outputPrivate.setText(this.prefsPrivate.getString(SharedPrefTestOtherOutput.KEY_PRIVATE, "NA"));
        this.outputWorldRead.setText(this.prefsWorldRead.getString(SharedPrefTestOtherOutput.KEY_WORLD_READ, "NA"));
        this.outputWorldWrite.setText(this.prefsWorldWrite.getString(SharedPrefTestOtherOutput.KEY_WORLD_WRITE, "NA"));
        this.outputWorldReadWrite.setText(this.prefsWorldReadWrite.getString(SharedPrefTestOtherOutput.KEY_WORLD_READ_WRITE,
            "NA"));
    }

}
