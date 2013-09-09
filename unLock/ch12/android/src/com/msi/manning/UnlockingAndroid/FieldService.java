/*
 * fieldservice.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */
package com.msi.manning.UnlockingAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FieldService extends Activity {

    final int ACTIVITY_REFRESHJOBS = 1;
    final int ACTIVITY_LISTJOBS = 2;
    final int ACTIVITY_SETTINGS = 3;

    final String tag = "CH12:FieldService";

    Prefs myprefs = null;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.fieldservice);

        // get our application prefs handle
        this.myprefs = new Prefs(getApplicationContext());

        // see if we have an email address set yet....
        RefreshUserInfo();

        // refresh jobs
        final Button refreshjobsbutton = (Button) findViewById(R.id.getjobs);
        refreshjobsbutton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                try {
                    startActivityForResult(new Intent(v.getContext(), RefreshJobs.class),
                        FieldService.this.ACTIVITY_REFRESHJOBS);
                } catch (Exception e) {
                    Log.i(FieldService.this.tag, "Failed to refresh jobs [" + e.getMessage() + "]");
                }
            }
        });

        // show jobs
        final Button listjobsbutton = (Button) findViewById(R.id.showjobs);
        listjobsbutton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                try {
                    // Perform action on click
                    startActivityForResult(new Intent(v.getContext(), ManageJobs.class),
                        FieldService.this.ACTIVITY_LISTJOBS);
                } catch (Exception e) {
                    Log.i(FieldService.this.tag, "Failed to list jobs [" + e.getMessage() + "]");
                }
            }
        });

        // settings
        final Button settingsbutton = (Button) findViewById(R.id.settings);
        settingsbutton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                try {
                    // Perform action on click
                    startActivityForResult(new Intent(v.getContext(), ShowSettings.class),
                        FieldService.this.ACTIVITY_SETTINGS);
                } catch (Exception e) {
                    Log.i(FieldService.this.tag, "Failed to Launch Settings [" + e.getMessage() + "]");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_REFRESHJOBS:
                break;
            case ACTIVITY_LISTJOBS:
                break;
            case ACTIVITY_SETTINGS:
                RefreshUserInfo();
                break;
        }

    }

    private void RefreshUserInfo() {
        try {
            final TextView emaillabel = (TextView) findViewById(R.id.emailaddresslabel);
            emaillabel.setText("User: " + this.myprefs.getEmail() + "\nServer: " + this.myprefs.getServer() + "\n");
        } catch (Exception e) {

        }
    }

}
