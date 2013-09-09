package com.andro;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GridViewActivity extends Activity implements OnItemClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // main.xml의 레이아웃에 있는 GridView를 인식함 
        GridView gridview = (GridView) findViewById(R.id.gridview);
        // GridView에 이미지들을 배치함  
        gridview.setAdapter(new ImageAdapter(this));    
        // GridView에 나타나는 아이템에 대한 클릭 대기, 클릭되면 onItemClick() 메소드를 실행함  
        gridview.setOnItemClickListener(this);
    }
    
	// @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	///// 알림창을 띄움: 시작 /////
    	AlertDialog.Builder alert = new AlertDialog.Builder(GridViewActivity.this);
        alert.setTitle("알림창");
        // 클릭된 이미지 위치 출력
        alert.setMessage(position + "번째 이미지를 누르셨네요!");
        alert.setIcon(R.drawable.ic_launcher);
        alert.setPositiveButton("확인", null);
        alert.show();
    	///// 알림창을 띄움: 끝 /////
	}
}