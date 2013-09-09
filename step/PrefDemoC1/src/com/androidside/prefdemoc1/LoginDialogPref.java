package com.androidside.prefdemoc1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginDialogPref extends DialogPreference {    
    SharedPreferences prefs;
    
    EditText idEdit;
    EditText pwEdit;
    
    public LoginDialogPref(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        prefs = getContext().getSharedPreferences(getKey(), Context.MODE_PRIVATE);
    } 

    @Override
    protected View onCreateDialogView() { 
        LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.logindialog, null);
                        
        idEdit = (EditText) layout.findViewById(R.id.id);
        pwEdit = (EditText) layout.findViewById(R.id.pw);
        
        idEdit.setText(prefs.getString("id", ""));
        pwEdit.setText(prefs.getString("pw", ""));   
        
        return layout;
    }
    
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON1) { //확인 버튼이라면            
            prefs = getContext().getSharedPreferences(getKey(), Context.MODE_PRIVATE);
            Editor editor = prefs.edit();
            
            editor.putString("id", idEdit.getText().toString());
            editor.putString("pw", pwEdit.getText().toString());
            editor.commit();            
        }
    }
}
