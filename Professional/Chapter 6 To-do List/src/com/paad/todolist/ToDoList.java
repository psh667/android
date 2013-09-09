package com.paad.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoList extends Activity {
	
	private static final String TEXT_ENTRY_KEY = "TEXT_ENTRY_KEY";
	private static final String ADDING_ITEM_KEY = "ADDING_ITEM_KEY";
	private static final String SELECTED_INDEX_KEY = "SELECTED_INDEX_KEY";
	
	private static final int ADD_NEW_TODO = Menu.FIRST;
	private static final int REMOVE_TODO = Menu.FIRST + 1;
	
	private ArrayList<ToDoItem> todoItems;
	private ListView myListView;
	private EditText myEditText;
	private ToDoItemAdapter aa;
	private boolean addingNew = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    // 뷰를 인플레이트 시킨다.
	    setContentView(R.layout.main);

	    // UI 위젯들에 대한 레퍼런스를 얻어온다.
	    myListView = (ListView)findViewById(R.id.myListView);
	    myEditText = (EditText)findViewById(R.id.myEditText);

	    // 해야 할 일들을 담기 위한 배열 리스트(array list)를 만든다.
	    todoItems = new ArrayList<ToDoItem>();
	    
	    // 이 배열을 리스트 뷰와 묶기 위한 배열 어댑터(array adapter)를 만든다.
	    int resID = R.layout.todolist_item;
	    aa = new ToDoItemAdapter(this, resID, todoItems);
	    
	    // 이 배열 어댑터를 리스트 뷰와 묶는다.
	    myListView.setAdapter(aa);

	    myEditText.setOnKeyListener(new OnKeyListener() {
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            if (event.getAction() == KeyEvent.ACTION_DOWN)
	                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
	                {
	                	ToDoItem newItem = new ToDoItem(myEditText.getText().toString());
	                    todoItems.add(0, newItem);
	                    myEditText.setText("");
	                    aa.notifyDataSetChanged();
	                    cancelAdd();
	                    return true;
	                }
	            return false;
	        }
	    });

	    registerForContextMenu(myListView);
	    restoreUIState();
	}
	
	private void restoreUIState() {
	    // 액티비티 환경설정 객체를 얻어온다.
	    SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);

	    // UI 상태 값들을 읽어온다.
	    // 기본 값으로 쓰일 값들도 지정한다.
	    String text = settings.getString(TEXT_ENTRY_KEY, "");
	    Boolean adding = settings.getBoolean(ADDING_ITEM_KEY, false);

	    // UI를 이전 상태로 복구한다.
	    if (adding) {
	        addNewItem();
	        myEditText.setText(text);
	    }
	}
	
	@Override
	protected void onPause(){
	    super.onPause();

	    // 액티비티 환경설정 객체를 얻어온다.
	    SharedPreferences uiState = getPreferences(0);
	    // 환경설정 에디터를 얻어온다.
	    SharedPreferences.Editor editor = uiState.edit();

	    // UI 상태 환경설정 값들을 추가한다.
	    editor.putString(TEXT_ENTRY_KEY, myEditText.getText().toString());
	    editor.putBoolean(ADDING_ITEM_KEY, addingNew);
	    // 변경된 내용을 적용한다.
	    editor.commit();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    savedInstanceState.putInt(SELECTED_INDEX_KEY, myListView.getSelectedItemPosition());
	    super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    int pos = -1;

	    if (savedInstanceState != null)
	        if (savedInstanceState.containsKey(SELECTED_INDEX_KEY))
	            pos = savedInstanceState.getInt(SELECTED_INDEX_KEY, -1);

	    myListView.setSelection(pos);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);

	    // 새 메뉴 아이템을 만들어 추가한다.
	    MenuItem itemAdd = menu.add(0, ADD_NEW_TODO, Menu.NONE, R.string.add_new);
	    MenuItem itemRem = menu.add(0, REMOVE_TODO, Menu.NONE, R.string.remove);

	    // 아이콘을 할당한다.
	    itemAdd.setIcon(R.drawable.add_new_item);
	    itemRem.setIcon(R.drawable.remove_item);

	    // 이들 각각에 단축키를 할당한다.
	    itemAdd.setShortcut('0', 'a');
	    itemRem.setShortcut('1', 'r');

	    return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);

	    menu.setHeaderTitle("할 일 아이템 선택");
	    menu.add(0, REMOVE_TODO, Menu.NONE, R.string.remove);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);

	    int idx = myListView.getSelectedItemPosition();

	    String removeTitle = getString(addingNew ? R.string.cancel : R.string.remove);

	    MenuItem removeItem = menu.findItem(REMOVE_TODO);
	    removeItem.setTitle(removeTitle);
	    removeItem.setVisible(addingNew || idx > -1);

	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);

	    int index = myListView.getSelectedItemPosition();

	    switch (item.getItemId()) {
	        case (REMOVE_TODO): {
	            if (addingNew) {
	                cancelAdd();
	            }
	            else {
	                removeItem(index);
	            }
	            return true;
	        }
	        case (ADD_NEW_TODO): {
	            addNewItem();
	            return true;
	        }
	    }

	    return false;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    super.onContextItemSelected(item);

	    switch (item.getItemId()) {
	        case (REMOVE_TODO): {
	            AdapterView.AdapterContextMenuInfo menuInfo;
	            menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	            int index = menuInfo.position;

	            removeItem(index);
	            return true;
	        }
	    }
	    return false;
	}
	
	private void cancelAdd() {
	    addingNew = false;
	    myEditText.setVisibility(View.GONE);
	}

	private void addNewItem() {
	    addingNew = true;
	    myEditText.setVisibility(View.VISIBLE);
	    myEditText.requestFocus();
	}

	private void removeItem(int _index) {
	    todoItems.remove(_index);
	    aa.notifyDataSetChanged();
	}
}