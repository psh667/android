package org.androidtown.ui.listview;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * 리스트뷰를 사용하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	/**
	 * 리스트뷰 객체
	 */
	DataListView list;
	
	/**
	 * 어댑터 객체
	 */
	IconTextListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        // 타이틀 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 리스트뷰 객체 생성
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        list = new DataListView(this);

        // 어댑터 객체 생성
        adapter = new IconTextListAdapter(this);

		// 아이템 데이터 만들기
		Resources res = getResources();
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "추억의 테트리스", "30,000 다운로드", "900 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "고스톱 - 강호동 버전", "26,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "친구찾기 (Friends Seeker)", "300,000 다운로드", "900 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "강좌 검색", "120,000 다운로드", "900 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "지하철 노선도 - 서울", "4,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "지하철 노선도 - 도쿄", "6,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "지하철 노선도 - LA", "8,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "지하철 노선도 - 워싱턴", "7,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), "지하철 노선도 - 파리", "9,000 다운로드", "1500 원"));
		adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon06), "지하철 노선도 - 베를린", "38,000 다운로드", "1500 원"));

		// 리스트뷰에 어댑터 설정
		list.setAdapter(adapter);

		// 새로 정의한 리스너로 객체를 만들어 설정
		list.setOnDataSelectionListener(new OnDataSelectionListener() {
			public void onDataSelected(AdapterView parent, View v, int position, long id) {
				IconTextItem curItem = (IconTextItem) adapter.getItem(position);
				String[] curData = curItem.getData();

				Toast.makeText(getApplicationContext(), "Selected : " + curData[0], 2000).show();
			}
		});


        // 화면을 리스트뷰 객체로 채움
        setContentView(list, params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
