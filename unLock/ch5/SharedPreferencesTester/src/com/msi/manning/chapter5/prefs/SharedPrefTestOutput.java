package com.msi.manning.chapter5.prefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class SharedPrefTestOutput extends Activity {

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
        super.onStart();
        this.prefsPrivate = getSharedPreferences(SharedPrefTestInput.PREFS_PRIVATE, Context.MODE_PRIVATE);
        this.prefsWorldRead = getSharedPreferences(SharedPrefTestInput.PREFS_WORLD_READ, Context.MODE_WORLD_READABLE);
        this.prefsWorldWrite = getSharedPreferences(SharedPrefTestInput.PREFS_WORLD_WRITE, Context.MODE_WORLD_WRITEABLE);
        this.prefsWorldReadWrite = getSharedPreferences(SharedPrefTestInput.PREFS_WORLD_READ_WRITE,
            Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);

        this.outputPrivate.setText(this.prefsPrivate.getString(SharedPrefTestInput.KEY_PRIVATE, "NA"));
        this.outputWorldRead.setText(this.prefsWorldRead.getString(SharedPrefTestInput.KEY_WORLD_READ, "NA"));
        this.outputWorldWrite.setText(this.prefsWorldWrite.getString(SharedPrefTestInput.KEY_WORLD_WRITE, "NA"));
        this.outputWorldReadWrite.setText(this.prefsWorldReadWrite.getString(SharedPrefTestInput.KEY_WORLD_READ_WRITE,
            "NA"));
    }
}
