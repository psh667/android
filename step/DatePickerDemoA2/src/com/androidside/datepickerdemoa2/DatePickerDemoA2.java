package com.androidside.datepickerdemoa2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerDemoA2 extends Activity implements DatePicker.OnDateChangedListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        DatePicker date = (DatePicker) findViewById(R.id.date);
        //date.setOnDateChangedListener(this);
        
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {
        // TODO Auto-generated method stub
        
    }
    
}