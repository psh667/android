package com.androidside.listviewdemob2;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ListViewDemoB2 extends Activity  {
    String[] cars = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};
    
    ArrayAdapter<String> adapter;
    ArrayList<String> carList;
    EditText nameText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        nameText = (EditText) findViewById(R.id.name);
        
        
        carList = new ArrayList<String>();
        carList.addAll(Arrays.asList(cars));
                
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, carList);
        
        ListView list = (ListView) findViewById(R.id.listview);        
        list.setAdapter(adapter);        
        
        Button addButton = (Button) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carList.add(0, nameText.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
