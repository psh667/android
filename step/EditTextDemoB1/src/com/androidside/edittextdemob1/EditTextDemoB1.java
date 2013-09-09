package com.androidside.edittextdemob1;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;

public class EditTextDemoB1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditText edit = (EditText) findViewById(R.id.edit);
         
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(10);
        
        edit.setFilters(FilterArray);

    }
}