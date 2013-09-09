package com.androidside.listviewdemoe1;

import android.app.ListActivity;
import android.os.Bundle;

public class ListViewDemoE1 extends ListActivity
{
    static final String LOG_TAG = "PAGELIST";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        PageAdapter itemAdapter = new PageAdapter( 
                this,
                R.layout.item_row,
                R.layout.loading_row ); 
        setListAdapter( itemAdapter );
    }
}
