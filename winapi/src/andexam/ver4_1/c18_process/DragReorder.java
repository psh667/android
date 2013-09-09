package andexam.ver4_1.c18_process;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class DragReorder extends Activity {
	// 로그용 태그
	final static String TAG = "DragReorder";
	ReorderScrollView mScroll;
	LinearLayout mList;
	WindowManager mWindowManager;
	WindowManager.LayoutParams mParamsBar;
	WindowManager.LayoutParams mParamImage;
	int mStartDragIdx;
	int mStartDragY;
	boolean mDragging;
	ImageView mDragBar;
	ImageView mDragImage;
	int mDragImageOffset;
	int mSlop;
	
	// 아래 위 변에서 이 범위안에 들어오면 스크롤한다.
	final static int SCRRANGE = 50;
	// 벗어난 정도에서 이 값을 나눈만큼 스크롤하여 속도를 느리게 한다.
	final static int SCRSPEEDDIV = 5;
	// 드래그 바의 폭
	final static int DRAGBARTHICK = 6;
	// 최대 스크롤 거리
	final static int MAXSCROLL = 20;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dragreorder);

		String[] arTerm = new String[] {
				"00.입춘", "01.우수", "02.경칩", "03.춘분","04.청명", "05.곡우", 
				"06.입하", "07.소만", "08.망종", "09.하지","10.소서", "11.대서", 
				"12.입추", "13.처서", "14.백로", "15.추분","16.한로", "17.상강", 
				"18.입동", "19.소설", "20.대설", "21.동지","22.소한", "23.대한"};
		mScroll = (ReorderScrollView)findViewById(R.id.scroll);
		mScroll.mParent = this;
		mList = (LinearLayout)findViewById(R.id.list);

		// 테스트용 항목 삽입
		for (int i = 0; i < arTerm.length; i++) {
			LayoutInflater Inflater = (LayoutInflater)getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout item = (LinearLayout)Inflater.inflate(R.layout.listitem, null);
			TextView title = (TextView)item.findViewById(R.id.title);
			title.setText(arTerm[i]);
			mList.addView(item);

			// 항목 클릭하면 텍스트만 읽어서 출력
			item.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					TextView title = (TextView)v.findViewById(R.id.title);
					Toast.makeText(DragReorder.this, title.getText(), 0).show();
				}
			});
			//*/

			// 클릭은 일단 날라옴. 드래그를 시작했으면 클릭 동작은 하지 않되 
			// 이때 띡하는 소리를 어찌할 방법이 없다.
			ImageButton btn = (ImageButton)item.findViewById(R.id.btnreorder);
			btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (mDragging == false) {
						Log.d("tag", "click");
						mList.removeViewAt(mStartDragIdx);
					}
				}
			});
		}

		prepareDragReorder();
	}

	// 이미지 뷰를 제거하지 않으면 윈도우 릭이 발생한다.
	public void onDestroy() {
		super.onDestroy();
		mWindowManager.removeView(mDragBar);
		mWindowManager.removeView(mDragImage);
	}
	
	// 드래그 처리에 필요한 이미지 및 배치 파라미터 등을 생성해 둠.
	void prepareDragReorder() {
		mSlop = ViewConfiguration.get(this).getScaledTouchSlop();

		// 배치 파라미터 미리 생성해 둠
		mParamsBar = new WindowManager.LayoutParams();
		mParamsBar.gravity = Gravity.TOP;
		mParamsBar.x = 0;
		mParamsBar.y = 0;
		mParamsBar.height = DRAGBARTHICK;
		mParamsBar.width = WindowManager.LayoutParams.MATCH_PARENT;
		mParamsBar.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
			| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
			| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
			| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		mParamsBar.format = PixelFormat.TRANSLUCENT;
		mParamsBar.windowAnimations = 0;
		
		// 드래그 바 생성해서 배치해 놓고 살짝 숨겨 놓음
		mDragBar = new ImageView(this);
		int backGroundColor = Color.parseColor("red");
		mDragBar.setBackgroundColor(backGroundColor);
		mDragBar.setVisibility(View.GONE);
		mWindowManager = (WindowManager)getSystemService("window");
		mWindowManager.addView(mDragBar, mParamsBar);

		// 드래그 이미지 생성해서 숨겨 둠
		mParamImage = new WindowManager.LayoutParams();
		mParamImage.copyFrom(mParamsBar);
		mParamImage.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mParamImage.alpha = 0.5f;

		mDragImage = new ImageView(this);
		mDragImage.setVisibility(View.GONE);
		mWindowManager.addView(mDragImage, mParamImage);
	}

	// 스크롤 뷰가 터치를 가로채서 전달한다. 
	// 전달된 좌표는 스크롤 뷰 좌상단 기준 좌표이며 스크롤 상태와는 무관하다.
	public boolean onInterceptTouchEvent(ReorderScrollView scrview, MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		int idx;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, "down x=" + event.getX() + "y=" + event.getY());
			// 버튼 영역을 확실히 눌렀는지 확인해야 드래그가 시작된다.
			// 시작 인덱스가 -1이면 드래그가 아니다.
			mStartDragIdx = -1;
			mDragging = false;
			// 차일드가 없으면 아무것도 할 필요가 없다.
			if (mList.getChildCount() == 0) {
				return true;
			}
			// 버튼 영역을 눌렀을 때만 드래그 시작한다.
			LinearLayout item = (LinearLayout)mList.getChildAt(0);
			ImageButton btn = (ImageButton)item.findViewById(R.id.btnreorder);
			if (x < btn.getLeft()) {
				return true;
			}

			// 클릭한 항목 인덱스와 드래그 시작 좌표를 기록해 놓으며 
			// 드래그는 아직 시작하지 않는다.
			mStartDragIdx = findChildFromPos(x, y);
			mStartDragY = y;

			// 드래그를 시작한 항목의 이미지를 떠 놓는다.
			View child = mList.getChildAt(mStartDragIdx);
			child.setDrawingCacheEnabled(true);
			Bitmap bit = Bitmap.createBitmap(child.getDrawingCache());
			mDragImage.setImageBitmap(bit);
			mDragImageOffset = y - (child.getTop() - mScroll.getScrollY());
			
			return false;
		case MotionEvent.ACTION_MOVE:
			// ACTION_DOWN에서 드래그 시작을 하지 않았으면 그냥 리턴. 
			// 여기서 true를 리턴해야 정상적인 목록 스크롤이 된다.
			if (mStartDragIdx == -1) {
				return true;
			}
			// 일정 거리 이상 움직여야만 드래그가 시작된다. 
			// 한번 시작되면 더 이상 점검하지 않는다.
			if (mDragging == false) {
				if (Math.abs(y - mStartDragY) > mSlop) {
					mDragging = true;
				}
			}
			
			if (mDragging) {
				int scrby;
				// 위쪽으로 범위 안에 들어오면 위로 스크롤한다.
				if (y < SCRRANGE) {
					// 스크롤 속도는 y와 경계선간의 거리에 일정값을 
					// 나누어 느리게 하되, 최대값을 적용한다.
					scrby = (SCRRANGE - y) / SCRSPEEDDIV;
					scrby = Math.min(scrby, MAXSCROLL);
					mScroll.scrollBy(0, -scrby);
				}
				if (y > mScroll.getHeight() - SCRRANGE) {
					scrby = (y - (mScroll.getHeight() - SCRRANGE)) / SCRSPEEDDIV;
					scrby = Math.min(scrby, MAXSCROLL);
					mScroll.scrollBy(0, scrby);
				}
				
				// 현재 위치의 항목을 찾아 드래그 바를 이동한다.
				idx = findChildFromPos(x, y);
				moveDragBar(idx);

				// 현재 위치에 드래그 이미지를 표시한다.
				mDragImage.setVisibility(View.VISIBLE);
				mParamImage.y = y + getScrollViewTop() - mDragImageOffset;
				mWindowManager.updateViewLayout(mDragImage, mParamImage);
			}
			return false;
		case MotionEvent.ACTION_UP:
			if (mStartDragIdx == -1) {
				return true;
			}
			if (mDragging) {
				if (mDragBar != null) {
					mDragBar.setVisibility(View.GONE);
					mDragImage.setVisibility(View.GONE);
				}
				idx = findChildFromPos(x, y);
				// 무의미한 이동은 하지 않는다.
				if (idx != mStartDragIdx && idx != mStartDragIdx - 1) {
					// idx위치는 삽입할 위치의 위쪽을 가리키므로 1을 더해야 한다.
					Log.d(TAG, "from " + mStartDragIdx + " to " + (idx + 1));
					MoveItem(mStartDragIdx, idx + 1);
				}
			}
			return false;
		}
		return true;
	}

	void MoveItem(int from, int to) {
		// 먼저 원본을 제거한다.
		View child = mList.getChildAt(from);
		mList.removeViewAt(from);
		// 삽입시 원본이 더 앞이었으면 to가 한칸 위로 올라오므로 to도 1 감소시킨다.
		if (from < to) {
			to--;
		}
		mList.addView(child, to);
	}
	
	int getScrollViewTop() {
		int[] loc = new int[2];
		mScroll.getLocationOnScreen(loc);

		return loc[1];
	}

	// idx 번째 항목에 드래그 바를 표시한다.
	void moveDragBar(int idx) {
		// 첫 항목 위쪽이면 첫 항목의 위에 드래그 바를 그린다.
		int y;
		if (idx == -1) {
			y = mList.getChildAt(0).getTop();
		} else {
			y = mList.getChildAt(idx).getBottom();
		}
		
		// 차일드의 좌표는 리니어상의 스크롤된 좌표이므로 
		// 스크롤된만큼 빼 스크롤 뷰 기준으로 맞춘다.
		y = y - mScroll.getScrollY();

		// 두께의 절반을 빼서 바닥보다 조금 위로 올려 항목간의 경계에 배치한다.
		y = y - DRAGBARTHICK/2;

		// 범위를 벗어나면 숨긴다. 단 위쪽은 드래그 바의 폭 절반만큼을 허용한다.
		if (y >= -DRAGBARTHICK && y < mScroll.getHeight()) {
			mDragBar.setVisibility(View.VISIBLE);
		} else {
			mDragBar.setVisibility(View.GONE);
		}
		// 스크롤 바의 윈도우상의 위치를 더해야 윈도우에 배치되는 뷰의 좌표가 조정된다.
		mParamsBar.y = y + getScrollViewTop();
		mWindowManager.updateViewLayout(mDragBar, mParamsBar);
	}

	// y 위치의 차일드 번호를 조사한다.
	int findChildFromPos(int x, int y) {
		// 스크롤 영역보다 더 위쪽이면 -1을 리턴한다. 첫 항목 바로 위쪽을 가리킨다.
		if (y < 0) {
			return -1;
		}

		// 차일드 전체 순회하며 클릭된 지점과 좌표 비교한다. 
		// 차일드의 좌표는 리니어 기준이며 y는 스크롤 뷰
		// 기준이므로 스크롤된 정도를 뺀 후 비교해야 한다.
		int count = mList.getChildCount();
		int top, bottom;
		for (int i = 0 ; i < count; i++) {
			View child = mList.getChildAt(i);
			top = child.getTop() - mScroll.getScrollY();
			bottom = child.getBottom() - mScroll.getScrollY();
			if (y > top && y <= bottom) {
				return i;
			}
		}

		// 끝까지 다 돌았는데도 찾지 못했으면 마지막 차일드를 리턴한다.
		return count - 1;
	}
}

// 드래그를 지원하는 스크롤 뷰. 터치 가로채기 메서드를 
// 재정의하여 부모에게 보내주는 역할만 한다.
class ReorderScrollView extends ScrollView {
	DragReorder mParent;
	public ReorderScrollView(Context context) {
		super(context);
	}

	public ReorderScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ReorderScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 좌표는 리스트 뷰 좌상단 기준 좌표이며 스크롤 상태는 고려 안됨.
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (mParent.onInterceptTouchEvent(this, event) == false) {
			return false;
		}
		boolean result = super.onInterceptTouchEvent(event);
		Log.d(DragReorder.TAG, "onInterceptTouchEvent return = " + result);
		return result; 
	}
};


