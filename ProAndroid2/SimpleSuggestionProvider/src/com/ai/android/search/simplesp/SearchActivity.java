package com.ai.android.search.simplesp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;

public class  SearchActivity extends Activity
{
	private final static String tag ="SearchActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(tag,"저는 생성되고 있습니다.");
		// 그렇지 않으면 다음을 수행 setContentView(R.layout.layout_test_search_activity);
		//this.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_GLOBAL);
		this.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL);
		
		// 검색 질의를 가져와서 처리
		final Intent queryIntent = getIntent();
		final String queryAction  = queryIntent.getAction();
		if (Intent.ACTION_SEARCH.equals(queryAction))
		{
			Log.d(tag,"검색용 새 인텐트");
			this.doSearchQuery(queryIntent);
		}
		else  {
			Log.d(tag,"비 검색용 새 인텐트");
		}
		return;
	}

	@Override
	public void  onNewIntent(final Intent  newIntent)
	{
		super.onNewIntent(newIntent);
		Log.d(tag,"new intent  calling  me");
		
		// 검색 질의를 가져와서 처리
		final  Intent queryIntent = getIntent();
		final String  queryAction = queryIntent.getAction();
		if (Intent.ACTION_SEARCH.equals(queryAction))
		{
			this.doSearchQuery(queryIntent); Log.d(tag,"검색용 새 인텐트");
		}
		else  {
			Log.d(tag,"비 검색용 새 인텐트");
		}
	}
	private void  doSearchQuery(final Intent queryIntent)
	{
		final  String queryString = queryIntent.getStringExtra(SearchManager.QUERY);
		
		// 최근 질의 제시항목 프로바이더에 질의 문자열을 기록
		SearchRecentSuggestions suggestions  = new SearchRecentSuggestions(this, SimpleSuggestionProvider.AUTHORITY, SimpleSuggestionProvider.MODE);
		suggestions.saveRecentQuery(queryString, null);
	}
}
