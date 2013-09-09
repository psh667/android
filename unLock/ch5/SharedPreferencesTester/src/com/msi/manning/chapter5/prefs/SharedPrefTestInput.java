package com.msi.manning.chapter5.prefs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPrefTestInput extends Activity {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    public static final String PREFS_WORLD_READ = "PREFS_WORLD_READABLE";
    public static final String PREFS_WORLD_WRITE = "PREFS_WORLD_WRITABLE";
    public static final String PREFS_WORLD_READ_WRITE = "PREFS_WORLD_READABLE_WRITABLE";

    public static final String KEY_PRIVATE = "KEY_PRIVATE";
    public static final String KEY_WORLD_READ = "KEY_WORLD_READ";
    public static final String KEY_WORLD_WRITE = "KEY_WORLD_WRITE";
    public static final String KEY_WORLD_READ_WRITE = "KEY_WORLD_READ_WRITE";

    private EditText inputPrivate;
    private EditText inputWorldRead;
    private EditText inputWorldWrite;
    private EditText inputWorldReadWrite;
    private Button button;

    private SharedPreferences prefsPrivate;
    private SharedPreferences prefsWorldRead;
    private SharedPreferences prefsWorldWrite;
    private SharedPreferences prefsWorldReadWrite;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.shared_preftest_input);

        this.inputPrivate = (EditText) findViewById(R.id.input_private);
        this.inputWorldRead = (EditText) findViewById(R.id.input_worldread);
        this.inputWorldWrite = (EditText) findViewById(R.id.input_worldwrite);
        this.inputWorldReadWrite = (EditText) findViewById(R.id.input_worldreadwrite);
        this.button = (Button) findViewById(R.id.prefs_test_button);

        this.button.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                boolean valid = validate();
                if (valid) {
                    prefsPrivate = getSharedPreferences(SharedPrefTestInput.PREFS_PRIVATE, Context.MODE_PRIVATE);
                    prefsWorldRead = getSharedPreferences(SharedPrefTestInput.PREFS_WORLD_READ,
                        Context.MODE_WORLD_READABLE);
                    prefsWorldWrite = getSharedPreferences(SharedPrefTestInput.PREFS_WORLD_WRITE,
                        Context.MODE_WORLD_WRITEABLE);
                    prefsWorldReadWrite = getSharedPreferences(SharedPrefTestInput.PREFS_WORLD_READ_WRITE,
                        Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);

                    Editor prefsPrivateEditor = prefsPrivate.edit();
                    Editor prefsWorldReadEditor = prefsWorldRead.edit();
                    Editor prefsWorldWriteEditor = prefsWorldWrite.edit();
                    Editor prefsWorldReadWriteEditor = prefsWorldReadWrite.edit();

                    prefsPrivateEditor.putString(SharedPrefTestInput.KEY_PRIVATE, inputPrivate.getText().toString());
                    prefsWorldReadEditor.putString(SharedPrefTestInput.KEY_WORLD_READ, inputWorldRead.getText()
                        .toString());
                    prefsWorldWriteEditor.putString(SharedPrefTestInput.KEY_WORLD_WRITE, inputWorldWrite.getText()
                        .toString());
                    prefsWorldReadWriteEditor.putString(SharedPrefTestInput.KEY_WORLD_READ_WRITE, inputWorldReadWrite
                        .getText().toString());

                    prefsPrivateEditor.commit();
                    prefsWorldReadEditor.commit();
                    prefsWorldWriteEditor.commit();
                    prefsWorldReadWriteEditor.commit();

                    Intent intent = new Intent(SharedPrefTestInput.this, SharedPrefTestOutput.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        StringBuffer sb = new StringBuffer();
        sb.append("Validation failed: \n");

        if (isEmpty(this.inputPrivate)) {
            sb.append("First input, private pref, must be present.\n");
            valid = false;
        }
        if (isEmpty(this.inputWorldRead)) {
            sb.append("Second input, world read pref, must be present.\n");
            valid = false;
        }
        if (isEmpty(this.inputWorldWrite)) {
            sb.append("Third input, world write pref, must be present.\n");
            valid = false;
        }
        if (isEmpty(this.inputWorldReadWrite)) {
            sb.append("Fourth input, world read write pref, must be present.\n");
            valid = false;
        }

        if (!valid) {
            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private boolean isEmpty(EditText et) {
        return ((et == null) || (et.getText().toString() == null) || et.getText().toString().equals(""));
    }
}
