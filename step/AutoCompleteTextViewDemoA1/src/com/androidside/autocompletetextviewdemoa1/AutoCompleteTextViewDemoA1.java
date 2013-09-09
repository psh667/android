package com.androidside.autocompletetextviewdemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AutoCompleteTextViewDemoA1 extends Activity {    
    String[] items = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.edit);
        
        edit.setAdapter(new ArrayAdapter<String>(this,
              android.R.layout.simple_dropdown_item_1line, items));
    }
}