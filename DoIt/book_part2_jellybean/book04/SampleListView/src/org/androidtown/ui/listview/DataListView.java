package org.androidtown.ui.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 리스트뷰를 상속하여 새로 정의한 리스트뷰 클래스
 * 
 * @author Mike
 *
 */
public class DataListView extends ListView {

	/**
	 * 설정한 어댑터 객체
	 */
	private IconTextListAdapter adapter;
	
	/**
	 * 설정한 리스너 객체
	 */
	private OnDataSelectionListener selectionListener;
	
	public DataListView(Context context) {
		super(context);

		init();
	}

	public DataListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}
	
	/**
	 * 초기화
	 */
	private void init() {
		setOnItemClickListener(new OnItemClickAdapter());
	}

	/**
	 * 어댑터 설정
	 * 
	 * @param adapter
	 */
	public void setAdapter(BaseAdapter adapter) {
		super.setAdapter(adapter);

	}

	/**
	 * 어댑터 객체 리턴
	 * 
	 * @return
	 */
	public BaseAdapter getAdapter() {
		return (BaseAdapter)super.getAdapter();
	}
	
	/**
	 * 리스너 설정
	 * 
	 * @param listener
	 */
	public void setOnDataSelectionListener(OnDataSelectionListener listener) {
		this.selectionListener = listener;
	}

	/**
	 * 리스너 객체 리턴
	 * 
	 * @return
	 */
	public OnDataSelectionListener getOnDataSelectionListener() {
		return selectionListener;
	}
	
	class OnItemClickAdapter implements OnItemClickListener {
		
		public OnItemClickAdapter() {
			
		}

		public void onItemClick(AdapterView parent, View v, int position, long id) {
			
			if (selectionListener == null) {
				return;
			}
			
			// 이벤트 전달
			selectionListener.onDataSelected(parent, v, position, id);
			
		}
		
	}
	
}