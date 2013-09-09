package org.androidtown.ui.gridview;

import org.ubiworks.mobile.protocol.mdbc.android.MRecord;
import org.ubiworks.mobile.protocol.mdbc.android.MTable;
import org.ubiworks.mobile.protocol.mdbc.android.MType;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

/**
 * 그리드뷰 위젯을 사용하여 테이블 모양으로 데이터를 보여주는 방법에 대해 알 수 있습니다.
 * 가상 테이블을 이용해 데이터를 구성하는 방법도 들어 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	// 그리드뷰 객체
	GridView gridview;
	
	// 어댑터 객체
	DataAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 타이틀 감추기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        // 그리드뷰 객체 참조
        gridview = (GridView) findViewById(R.id.gridview);

        // 어댑터 객체 생성
        adapter = new DataAdapter(this);

        // 가상 테이블로 데이터 생성
        MTable table = createSampleTable();
        adapter.setTable(table);
        gridview.setNumColumns(this.adapter.getTable().countColumn);

        gridview.setAdapter(adapter);

        // 이벤트 처리
        gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				int countColumn = adapter.getTable().countColumn;
				int rowIndex = position / countColumn;

				Toast.makeText(getApplicationContext(), "Selected position : " + position + ", rowIndex : " + rowIndex, Toast.LENGTH_SHORT).show();

				adapter.setSelectedRow(rowIndex);
				adapter.notifyDataSetChanged();
			}
		});

    }

    /**
     * 가상 테이블로 데이터 만들기
     */
    private MTable createSampleTable() {
    	MTable outTable = new MTable("Contacts");

    	try {
	        outTable.addColumn("Name", MType.STRING, 60);
	        outTable.addColumn("Address", MType.STRING, 120);
	        outTable.addColumn("Group", MType.STRING, 40);

	        MRecord aRecord = outTable.createRecord();
	        aRecord.addCell("Mike");
	        aRecord.addCell("Seoul");
	        aRecord.addCell("Friends");
	        outTable.add(aRecord);

	        aRecord = outTable.createRecord();
	        aRecord.addCell("Ginnie");
	        aRecord.addCell("Busan");
	        aRecord.addCell("Friends");
	        outTable.add(aRecord);

	        aRecord = outTable.createRecord();
	        aRecord.addCell("John");
	        aRecord.addCell("Daejeon");
	        aRecord.addCell("Family");
	        outTable.add(aRecord);

	        aRecord = outTable.createRecord();
	        aRecord.addCell("홍길동");
	        aRecord.addCell("대전");
	        aRecord.addCell("가족");
	        outTable.add(aRecord);

	        aRecord = outTable.createRecord();
	        aRecord.addCell("홍문수");
	        aRecord.addCell("부산");
	        aRecord.addCell("가족");
	        outTable.add(aRecord);

	        aRecord = outTable.createRecord();
	        aRecord.addCell("김지민");
	        aRecord.addCell("대구");
	        aRecord.addCell("가족");
	        outTable.add(aRecord);

	        aRecord = outTable.createRecord();
	        aRecord.addCell("박진명");
	        aRecord.addCell("춘천");
	        aRecord.addCell("가족");
	        outTable.add(aRecord);

	        aRecord = outTable.createRecord();
	        aRecord.addCell("정수연");
	        aRecord.addCell("광주");
	        aRecord.addCell("가족");
	        outTable.add(aRecord);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}

        return outTable;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
