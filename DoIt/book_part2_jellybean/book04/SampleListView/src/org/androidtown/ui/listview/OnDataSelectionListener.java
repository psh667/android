package org.androidtown.ui.listview;

import android.view.View;
import android.widget.AdapterView;

/**
 * 이벤트를 처리하기 위한 새로운 인터페이스 정의
 * 
 * @author Mike
 */
public interface OnDataSelectionListener {

	/**
	 * 이벤트 처리 시 호출되는 메소드
	 */
	public void onDataSelected(AdapterView parent, View v, int position, long id);
	
}
