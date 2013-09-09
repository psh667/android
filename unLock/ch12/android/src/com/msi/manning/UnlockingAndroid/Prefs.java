/*
 * prefs.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */

package com.msi.manning.UnlockingAndroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Prefs {

    private SharedPreferences _prefs = null;
    private Editor _editor = null;
    private String _useremailaddress = "Unknown";
    private String _serverurl = "http://android12.msi-wireless.com/getjoblist.php";

    public Prefs(Context context) {
        this._prefs = context.getSharedPreferences("PREFS_PRIVATE", Context.MODE_PRIVATE);
        this._editor = this._prefs.edit();
    }

    public String getValue(String key, String defaultvalue) {
        if (this._prefs == null) {
            return "Unknown";
        }

        return this._prefs.getString(key, defaultvalue);
    }

    public void setValue(String key, String value) {
        if (this._editor == null) {
            return;
        }

        this._editor.putString(key, value);

    }

    public String getEmail() {
        if (this._prefs == null) {
            return "Unknown";
        }

        this._useremailaddress = this._prefs.getString("emailaddress", "Unknown");
        return this._useremailaddress;
    }

    public String getServer() {
        if (this._prefs == null) {
            return "http://android12.msi-wireless.com/";
        }

        this._serverurl = this._prefs.getString("serverurl", "http://android12.msi-wireless.com/");
        return this._serverurl;
    }

    public void setEmail(String newemail) {
        if (this._editor == null) {
            return;
        }

        this._editor.putString("emailaddress", newemail);
    }

    public void setServer(String serverurl) {
        if (this._editor == null) {
            return;
        }
        this._editor.putString("serverurl", serverurl);
    }

    public void save() {
        if (this._editor == null) {
            return;
        }
        this._editor.commit();
    }
}
