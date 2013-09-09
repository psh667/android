package com.androidside.listviewdemob3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewDemoB3 extends Activity  {
    String[] cars = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView list = (ListView) findViewById(R.id.listview);
        
        EditText header = new EditText(this);
        header.setText("HEADER");
        
        EditText footer = new EditText(this);
        footer.setText("FOOTER");
        
        list.addHeaderView(header);
        list.addFooterView(footer);
        
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cars);
        list.setAdapter(adapter);        
    }
}
