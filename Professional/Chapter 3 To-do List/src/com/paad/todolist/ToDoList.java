package com.paad.todolist;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoList extends Activity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // 뷰를 인플레이트 시킨다.
	    setContentView(R.layout.main);

	    // UI 위젯들에 대한 레퍼런스를 얻어온다.
	    ListView myListView = (ListView)findViewById(R.id.myListView);
	    final EditText myEditText = (EditText)findViewById(R.id.myEditText);
	    
	    // 해야 할 일들을 담기 위한 배열 리스트(array list)를 만든다.
	    final ArrayList<String> todoItems = new ArrayList<String>();
	    
	    // 이 배열을 리스트 뷰와 묶기 위한 배열 어댑터(array adapter)를 만든다.
	    final ArrayAdapter<String> aa;
	    aa = new ArrayAdapter<String>(this,
                                      android.R.layout.simple_list_item_1,
                                      todoItems);
	    
	    // 이 배열 어댑터를 리스트 뷰와 묶는다.
	    myListView.setAdapter(aa);

	    myEditText.setOnKeyListener(new OnKeyListener() {
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            if (event.getAction() == KeyEvent.ACTION_DOWN)
	                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
	                    todoItems.add(0, myEditText.getText().toString());
	                    aa.notifyDataSetChanged();
	                    myEditText.setText("");
	                    return true;
	                }
	            return false;
	        }
	    });
	}
}