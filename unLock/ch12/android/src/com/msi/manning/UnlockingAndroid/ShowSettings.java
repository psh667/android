/*
 * showsettings.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */

package com.msi.manning.UnlockingAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShowSettings extends Activity {

    Prefs myprefs = null;

    final String tag = "CH12:ShowSettings";

    AlertDialog.Builder adb;// = new AlertDialog.Builder(this);

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.showsettings);

        this.myprefs = new Prefs(getApplicationContext());

        // load screen
        PopulateScreen();

        this.adb = new AlertDialog.Builder(this);

        final Button savebutton = (Button) findViewById(R.id.settingssave);

        // create anonymous click listener to handle the "save"
        savebutton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                try {

                    // get the string and do something with it.

                    final EditText email = (EditText) findViewById(R.id.emailaddress);
                    if (email.getText().length() == 0) {

                        AlertDialog ad = ShowSettings.this.adb.create();
                        ad.setMessage("Please Enter Your Email Address");
                        ad.show();
                        return;
                    }

                    final EditText serverurl = (EditText) findViewById(R.id.serverurl);
                    if (serverurl.getText().length() == 0) {
                        AlertDialog ad = ShowSettings.this.adb.create();
                        ad.setMessage("Please Enter The Server URL");
                        ad.show();
                        return;
                    }

                    // save off values
                    ShowSettings.this.myprefs.setEmail(email.getText().toString());
                    ShowSettings.this.myprefs.setServer(serverurl.getText().toString());
                    ShowSettings.this.myprefs.save();

                    // we're done!
                    finish();
                } catch (Exception e) {
                    Log.i(ShowSettings.this.tag, "Failed to Save Settings [" + e.getMessage() + "]");
                }
            }
        });
    }

    private void PopulateScreen() {
        try {
            final EditText emailfield = (EditText) findViewById(R.id.emailaddress);
            final EditText serverurlfield = (EditText) findViewById(R.id.serverurl);

            emailfield.setText(this.myprefs.getEmail());
            serverurlfield.setText(this.myprefs.getServer());
        } catch (Exception e) {

        }
    }
}
