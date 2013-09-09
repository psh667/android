package com.androidside.spinnerdemoa2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerDemoA2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String[] numbers = { "1", "2", "3", "4", "5" };

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, numbers);
        spinner.setAdapter(adapter);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setSelection(2);
    }
}