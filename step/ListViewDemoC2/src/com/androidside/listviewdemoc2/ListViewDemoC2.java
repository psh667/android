package com.androidside.listviewdemoc2;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewDemoC2 extends ListActivity {
    String[] cars = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};
    TextView selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        selected = (TextView) findViewById(R.id.selected);
        
        setListAdapter(new NewArrayAdapter(this));        
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        selected.setText(cars[position]);
    }

    class NewArrayAdapter extends ArrayAdapter {
        Activity context;

        @SuppressWarnings("unchecked")
        NewArrayAdapter(Activity context) {
            super(context, R.layout.list_row, cars);

            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View row = inflater.inflate(R.layout.list_row, null);

            TextView textView = (TextView) row.findViewById(R.id.label);
            textView.setText(cars[position]);

            if (cars[position].equals("SONATA")) {
                ImageView imageView = (ImageView) row.findViewById(R.id.icon);
                imageView.setImageResource(R.drawable.sonata);
            }

            return row;
        }
    }
}
