package com.androidside.dbdemoa2;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DBDemoA2 extends ListActivity implements View.OnClickListener {
    EditText carNameEdit;
    EditText selectIdEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        carNameEdit = (EditText) findViewById(R.id.carname);
        selectIdEdit = (EditText) findViewById(R.id.select_id);
                
        ((Button) findViewById(R.id.insert)).setOnClickListener(this);
        ((Button) findViewById(R.id.selectone)).setOnClickListener(this);
        ((Button) findViewById(R.id.selectall)).setOnClickListener(this);
    }

    public void onClick(View v) {
        DBHandler dbhandler = DBHandler.open(this);
        
        if (v.getId() == R.id.insert) {          
            String carName = carNameEdit.getText().toString();
            
            long cnt = dbhandler.insert(carName);
            
            if (cnt == -1) {
                Toast.makeText(this, carName + "가 테이블에 추가되지 않았습니다.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, carName + "가 테이블에 추가되었습니다.", Toast.LENGTH_LONG).show();                
            }            
        } else if (v.getId() == R.id.selectone) {            
            String selectIdStr = selectIdEdit.getText().toString();
            int selectId = Integer.parseInt(selectIdStr);
            
            Cursor cursor = dbhandler.select(selectId);
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_LONG).show();
            } else {            
                String name = cursor.getString(cursor.getColumnIndex("car_name"));
                Toast.makeText(this, "자동차 이름 " + name, Toast.LENGTH_LONG).show();
            }
            cursor.close(); 
        } else if (v.getId() == R.id.selectall) {
            Cursor cursor = dbhandler.selectAll();
            startManagingCursor(cursor);
            
            SimpleCursorAdapter cursorAdapter = 
                new SimpleCursorAdapter(this, R.layout.list_row, cursor, 
                        new String[] {"car_name"}, new int[] {R.id.text1}  );
            setListAdapter(cursorAdapter);
        }
        
        dbhandler.close();
    }
}