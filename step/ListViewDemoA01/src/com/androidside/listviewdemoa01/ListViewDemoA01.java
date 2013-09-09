package com.androidside.listviewdemoa01;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewDemoA01 extends Activity {    
    private String[] cars = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView list = (ListView) findViewById(R.id.list);
        
        list.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cars));      
    }
}
