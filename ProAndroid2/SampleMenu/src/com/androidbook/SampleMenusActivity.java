package com.androidbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
//import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

public class SampleMenusActivity extends Activity {

	// 다음을 onCreateOptions에서 초기화
	Menu myMenu = null; 

	@Override 
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.main); 
	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) 
	{
    // 부모를 호출해서 시스템 계층의 모든 메뉴를 부속시킴
	super.onCreateOptionsMenu(menu); 
	
	this.myMenu = menu; 
	
	// 일반 메뉴 항목 추가
	addRegularMenuItems(menu); 
	
	// 2차 메뉴 항목 추가
	add5SecondaryMenuItems(menu); 

	// 서브 메뉴 항목 추가
	addSubMenu(menu);
	// 메뉴를 표시하려면 return를 반환해야 함
	// false가 반환되면 메뉴는 표시되지 않음
	return true; 
	}

	private void addRegularMenuItems(Menu menu)
	{
		int base=Menu.FIRST;  // 값은 1이다

	    menu.add(base,base,base,"추가"); 
	    menu.add(base,base+1,base+1,"항목 2"); 
	    menu.add(base,base+2,base+2,"제거"); 

	    menu.add(base,base+3,base+3,"2차 메뉴 숨기기"); 
	    menu.add(base,base+4,base+4,"2차 메뉴 보이기"); 

	    menu.add(base,base+5,base+5,"2차 메뉴 가용화"); 
	    menu.add(base,base+6,base+6,"2차 메뉴 비가용화"); 

	    menu.add(base,base+7,base+7,"2차 메뉴 체크"); 
	    menu.add(base,base+8,base+8,"2차 메뉴 체크 해제"); 
	}

	private void add5SecondaryMenuItems(Menu menu)
	{
		// 2차 항목들은 다른 모든 메뉴 항목과 똑같이 보인다.
		int base=Menu.CATEGORY_SECONDARY; 
	
		menu.add(base,base+1,base+1,"2차. 항목 1"); 
		menu.add(base,base+2,base+2,"2차. 항목 2"); 
		menu.add(base,base+3,base+3,"2차. 항목 3"); 
		menu.add(base,base+3,base+3,"2차. 항목 4"); 
		menu.add(base,base+4,base+4,"2차. 항목 5"); 
	}

	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			appendText(" $nhello"); 
		}
		else if (item.getItemId() == 2) {
			appendText("\nitem2");
		}
		else if (item.getItemId() == 3) {
			emptyText();
		}
		else if (item.getItemId() == 4) {
			// 2차 메뉴 항목 숨기기
			this.appendMenuItemText(item); 
			this.myMenu.setGroupVisible(Menu.CATEGORY_SECONDARY,false); 
		}
		else if (item.getItemId() == 5) {
			// 2차 메뉴 항목 보이기
			this.appendMenuItemText(item); 
			this.myMenu.setGroupVisible(Menu.CATEGORY_SECONDARY,true); 
		}
		else if (item.getItemId() == 6) {
			// 2차 메뉴 항목 가용화
			this.appendMenuItemText(item); 
			this.myMenu.setGroupEnabled(Menu.CATEGORY_SECONDARY,true); 
		}
		else if (item.getItemId() == 7) {
			// 2차 메뉴 항목 비가용화
			this.appendMenuItemText(item); 
			this.myMenu.setGroupEnabled(Menu.CATEGORY_SECONDARY,false); 
		}
		else if (item.getItemId() == 8) {
			// 2차 메뉴 항목 체크
			this.appendMenuItemText(item); 
			myMenu.setGroupCheckable(Menu.CATEGORY_SECONDARY,true,false); 
		}
		else if (item.getItemId() == 9) {
			// 2차 메뉴 항목 체크 해제
			this.appendMenuItemText(item); 
			myMenu.setGroupCheckable(Menu.CATEGORY_SECONDARY,false,false); 
		}
		else {
			this.appendMenuItemText(item); 
		}
		// 메뉴 항목 처리가 완료되면 반드시 true를 반환해야 한다.
		return true;
	}

	// 덧붙일 텍스트 문자열을 TextView에 할당
	private void appendText(String text) {
		TextView tv = (TextView)this.findViewById(R.id.textViewId); 
		tv.setText(tv.getText() + text); 
	}

	// 제목에 덧붙일 메뉴 항목을 TextView에 할당
	private void appendMenuItemText(MenuItem menuItem) {
		String title = menuItem.getTitle().toString(); 
		TextView tv = (TextView)this.findViewById(R.id.textViewId); 
		tv.setText(tv.getText() + " _n" + title); 
	}

	// TextView의 내용을 비움
	private void emptyText() {
		TextView tv = (TextView)this.findViewById(R.id.textViewId); 
		tv.setText(""); 
	}

	private void addSubMenu(Menu menu) 
	{
	  // 2차 항목들은 다른 메뉴들과 똑같이 표시된다.
	  int base=Menu.FIRST + 100; 
	  SubMenu sm = menu.addSubMenu(base,base+1,Menu.NONE,"하위 메뉴"); 
	  sm.add(base,base+2,base+2, "하위 항목 1"); 
	  sm.add(base,base+3,base+3, "하위 항목 2"); 
	  sm.add(base,base+4,base+4, "하위 항목 3"); 

	  //MenuItem item1 = null;
	// 하위 메뉴 항목 아이콘은 지원되지 않는다.
	  //item1.setIcon(R.drawable.icon48x48_2);

	  // 이렇게 하면 괜찮지만,
	  sm.setIcon(R.drawable.icon48x48_1);

	  // 이렇게 하면 런타임 예외가 발생한다.
	  //sm.addSubMenu("눌러봐"); 
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	  MenuInflater inflater = getMenuInflater(); // 액티비티로부터
	  inflater.inflate(R.menu.my_menu, menu); 

	  // 반드시 true를 반환해야만 메뉴가 보인다.
	  return true; 
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item) 
	{
	  this.appendMenuItemText(item); 
	  if (item.getItemId() == R.id.menu_clear) 
	  {
	    this.emptyText(); 
	  }
	  else if (item.getItemId() == R.id.menu_dial) 
	  {
	    this.dial();
	  }
	  else if (item.getItemId() == R.id.menu_testPick) 
	  {
	    IntentsUtils.invokePick(this); 
	  }
	  else if (item.getItemId() == R.id.menu_testGetContent) 
	  {
	    IntentsUtils.invokeGetContent(this); 
	  }
	  else if (item.getItemId() == R.id.menu_show_browser) 
	  {
	    IntentsUtils.tryOneOfThese(this); 
	  }
	  // 메뉴 항목 처리가 완료되면 반드시 true를 반환해야 한다.
		return true;
	}
	
	private void dial() {
		// TODO Auto-generated method stub
		
	}
	
	private void emptyText() {
		// TODO Auto-generated method stub
		
	}
	private void appendMenuItemText(MenuItem item) {
		// TODO Auto-generated method stub
		
	}*/
}
