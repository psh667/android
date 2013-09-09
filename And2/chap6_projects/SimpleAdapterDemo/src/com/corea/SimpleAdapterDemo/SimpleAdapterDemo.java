package com.corea.SimpleAdapterDemo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SimpleAdapterDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView list = (ListView) findViewById(R.id.fruits);
        
        ArrayList<HashMap<String, String>> fruitlist 
        	= new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("seq", "5");
        map.put("fruit", "사과");       
        fruitlist.add(map);
        
        map = new HashMap<String, String>();
        map.put("seq", "2");
        map.put("fruit", "배");
        fruitlist.add(map);

        map = new HashMap<String, String>();
        map.put("seq", "3");
        map.put("fruit", "감");
        fruitlist.add(map);

        map = new HashMap<String, String>();        
        map.put("seq", "2");
        map.put("fruit", "배");
        fruitlist.remove(map);
        
        SimpleAdapter sa 
        	= new SimpleAdapter(this, fruitlist, R.layout.row,
                new String[] {"seq", "fruit"}, 
                new int[] {R.id.initial, R.id.fruit});
        list.setAdapter(sa);
    }
}