package com.androidside.listviewdemoa2;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewDemoA2 extends ListActivity {
    private String[] cars = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};
    private TextView selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, cars));

        ListView listView = getListView();
        //listView.setItemsCanFocus(false);
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        selected = (TextView) findViewById(R.id.selected);
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        selected.setText(cars[position]);
    }
}
