package org.androidtown.multimemo;

import org.androidtown.multimemo.common.TitleBitmapButton;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MultiMemoActivity extends Activity {

	public static final String TAG = "MultiMemoActivity";

	/**
	 * 메모 리스트뷰
	 */
	ListView mMemoListView;

	/**
	 * 메모 리스트 어댑터
	 */
	MemoListAdapter mMemoListAdapter;

	/**
	 * 메모 갯수
	 */
	int mMemoCount = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 메모 리스트
        mMemoListView = (ListView)findViewById(R.id.memoList);
    	mMemoListAdapter = new MemoListAdapter(this);
    	mMemoListView.setAdapter(mMemoListAdapter);
    	mMemoListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				viewMemo(position);
			}
		});


        // 새 메모 버튼 설정
        TitleBitmapButton newMemoBtn = (TitleBitmapButton)findViewById(R.id.newMemoBtn);
    	newMemoBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "newMemoBtn clicked.");

			}
		});

    	// 닫기 버튼 설정
        TitleBitmapButton closeBtn = (TitleBitmapButton)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

        loadMemoListData();
    }

    private void loadMemoListData() {
    	MemoListItem aItem = new MemoListItem("1", "2011-06-10 10:20", "오늘은 좋은 날!",
    			null, null,
    			null, null,
    			null, null,
    			null, null);

    	mMemoListAdapter.addItem(aItem);
    	mMemoListAdapter.notifyDataSetChanged();
    }

    private void viewMemo(int position) {

    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}