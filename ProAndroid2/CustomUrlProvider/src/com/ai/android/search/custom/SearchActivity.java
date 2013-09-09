package com.ai.android.search.custom;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SearchActivity extends Activity
{
	private final static String tag = "SearchActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(tag,"나는 생성되는 중");
		setContentView(R.layout.layout_search_activity);
		
		// 검색 질의를 가져와서 처리
		final  Intent queryIntent = getIntent();
		
		// 질의 액션
		final  String queryAction = queryIntent.getAction();
		Log.d(tag,"인텐트 액션 생성:"+queryAction);
		
		final String  queryString = queryIntent.getStringExtra(SearchManager.QUERY);
		Log.d(tag,"인텐트 질의 생성:"+queryString);
		
		if (Intent.ACTION_SEARCH.equals(queryAction))
		{
			this.doSearchQuery(queryIntent);
		}
		else if (Intent.ACTION_VIEW.equals(queryAction))
		{
			this.doView(queryIntent);
		}
		else  {
			Log.d(tag,"검색 이외로부터 인텐트 생성");
		}
		return;
	}
	
	@Override
	public void  onNewIntent(final Intent  newIntent)
	{
		super.onNewIntent(newIntent); Log.d(tag,"나를 호출하는 새 인텐트");

		// 검색 질의를 가져와서 처리
		final  Intent queryIntent = newIntent;

		// 질의 액션
		final String  queryAction = queryIntent.getAction();
		Log.d(tag,"새 인텐트 액션:"+queryAction);

		final  String queryString =
		queryIntent.getStringExtra(SearchManager.QUERY);
		Log.d(tag,"새 인텐트 질의:"+queryString);

		if (Intent.ACTION_SEARCH.equals(queryAction))
		{
			this.doSearchQuery(queryIntent);
		}
		else if (Intent.ACTION_VIEW.equals(queryAction))
		{
			this.doView(queryIntent);
		}
		else  {
		Log.d(tag,"검색에서 호출되지 않은 새 인텐트");
		}
		return;
	}
	private void  doSearchQuery(final Intent queryIntent)
	{
		final  String queryString =
		queryIntent.getStringExtra(SearchManager.QUERY);
		appendText("검색 중인 질의:"  + queryString);
	}
	private void  appendText(String msg)
	{
		TextView tv = (TextView)this.findViewById(R.id.text1);
		tv.setText(tv.getText() + "\n" + msg);
	}
	private void  doView(final Intent  queryIntent)
	{
		Uri uri = queryIntent.getData();
		String action = queryIntent.getAction();
		Intent i = new Intent(action);
		i.setData(uri);
		startActivity(i);
		this.finish();
	}
}
