package com.androidside.listviewdemob1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewDemoB1 extends Activity implements AdapterView.OnItemClickListener  {
    private String[] cars = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};
    private TextView selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView list = (ListView) findViewById(R.id.list);
        
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cars);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        selected = (TextView) findViewById(R.id.selected);
    }


    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        selected.setText(cars[position]);
        Toast.makeText(this, ""+id, 10000).show();
    }
}