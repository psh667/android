package com.appstudio.android.sample.ch_25_2;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class PreferenceMenualEditingActivity extends Activity {
    private SharedPreferences mPref;
    private EditText mED;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.preference_manual_editing_activity);
        mED = (EditText)findViewById(R.id.editText1);
        mPref = PreferenceManager
                            .getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mED.setText(mPref.getString(
                "edittext_preference", "디폴트값"));
    }
    
    public void mOnSave(View view)  {
        Editor editor = mPref.edit();
        editor.putString("edittext_preference"
                         , mED.getText().toString());
        editor.commit();
        finish();
    }
}
