package pro.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class  RegularActivity extends Activity
{
	//private final String tag = "RegularActivity";
	
	@Override
	public void onCreate(Bundle  savedInstanceState)
	{
		super.onCreate(savedInstanceState); setContentView(R.layout.main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// 부모를 호출해서 시스템 계층의 메뉴들을 부착
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater(); // 액티비티로부터
		inflater.inflate(R.menu.main_menu,  menu);
		return true;
	}
	@Override
	public boolean  onOptionsItemSelected(MenuItem  item)
	{
		appendMenuItemText(item);
		if (item.getItemId() == R.id.menu_clear)  {
		this.emptyText();
		return true;
	}
	
	if (item.getItemId() == R.id.mid_no_search)  {
		this.invokeNoSearchActivity();
		return true;
	}
	if (item.getItemId() == R.id.mid_local_search)  {
		this.invokeLocalSearchActivity();
		return true;
	}
	if (item.getItemId() == R.id.mid_invoke_search)  {
		this.invokeSearchInvokerActivity();
		return true;
	}
	return true;
	}
	
	private TextView getTextView()
	{
		return (TextView)this.findViewById(R.id.text1);
	}
	
	private void  appendMenuItemText(MenuItem menuItem)
	{
		String title = menuItem.getTitle().toString();
		TextView  tv = getTextView();
		tv.setText(tv.getText() + "\n" + title);
	}
	private void  emptyText()
	{
		TextView  tv = getTextView();
		tv.setText("");
	}
	private void  invokeNoSearchActivity()
	{
		Intent intent = new Intent(this,NoSearchActivity.class);
		startActivity(intent);
	}
	private void  invokeSearchInvokerActivity()
	{
		Intent intent = new Intent(this,SearchInvokerActivity.class);
		startActivity(intent);
	}
	private void  invokeLocalSearchActivity()
	{
		Intent intent = new Intent(this,LocalSearchEnabledActivity.class);
		startActivity(intent);
	}
}
