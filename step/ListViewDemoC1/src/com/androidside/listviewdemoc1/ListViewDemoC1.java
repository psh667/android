package com.androidside.listviewdemoc1;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewDemoC1 extends ListActivity {
    String[] cars = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};
    TextView selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setListAdapter(new ArrayAdapter<String>(this,
                    R.layout.list_row, R.id.label, cars));

        selected = (TextView) findViewById(R.id.selected);
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        selected.setText(cars[position]);
    }
}