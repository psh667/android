package andexam.ver4_1.c10_tool;

import java.util.*;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

// 액티비티 : 게임 뷰를 담는 껍데기.
public class MemoryPowerBaby extends Activity {
	GameView gv;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gv = new GameView(this);
		setContentView(gv);
	}
	
	//도형 하나에 대한 정보
	class Shape {
		int what;
		Rect rt;
	}

	// 게임 뷰 : 실질적인 메인이다.
	class GameView extends View {
		// 그리기 모드. 빈화면 또는 도형 출력
		final static int BLANK = 0;
		final static int PLAY = 1;
		final static int SHOW = 2;
		// 게임 진행 속도
		final static int DELAY = 1500;
		// 현재 그리기 모드
		int status;
		// 생성된 도형의 목록
		ArrayList<Shape> arShape = new ArrayList<Shape>();
		Random Rnd = new Random();
		Activity mParent;
		Bitmap[] mBaby = new Bitmap[3];
		Vibrator mVib;
		int mShowCount;

		public GameView(Context context) {
			super(context);
			mParent = (Activity)context;
			Resources res = getResources();
			mBaby[0] = BitmapFactory.decodeResource(res, R.drawable.hanseul);
			mBaby[1] = BitmapFactory.decodeResource(res, R.drawable.hangyul);
			mBaby[2] = BitmapFactory.decodeResource(res, R.drawable.miyoung);
			mVib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

			// 빈 화면으로 시작하고 잠시 후에 게임 시작
			status = BLANK;
			mHandler.sendEmptyMessageDelayed(0,DELAY);
		}

		public void onDraw(Canvas canvas) {
			// 검은색 배경으로 지운다. 빈 화면이면 지우기만 하고 리턴 
			canvas.drawColor(Color.BLACK);
			if (status == BLANK) {
				return;
			}

			// 도형 목록을 순회하면서 도형 정보대로 출력한다.
			int idx;
			for (idx = 0; idx < arShape.size(); idx ++) {
				Rect rt = arShape.get(idx).rt;
				// SHOW 상태이고 홀수 카운트이면 마지막 도형은 그리지 않는다.
				if (status == SHOW && mShowCount % 2 == 1 && 
						idx == arShape.size() - 1) {
					break;
				}
				canvas.drawBitmap(mBaby[arShape.get(idx).what], null, rt, null);
			}
		}

		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int sel;
				sel = FindShapeIdx((int)event.getX(), (int)event.getY());

				// 빈 바닥을 찍었으면 무시한다.
				if (sel == -1) {
					return true;
				}

				// 진동
				mVib.vibrate(40);

				// 마지막 추가된 도형을 제대로 찍었으면 다음 단계로 진행. 
				// 빈 화면 잠시 보여준 후 새 도형 추가
				if (sel == arShape.size()-1) {
					status = BLANK;
					invalidate();
					mHandler.sendEmptyMessageDelayed(0,DELAY);
				// 엉뚱한 도형을 찍었으면 깜박거림을 시작한다.
				} else {
					status = SHOW;
					mShowCount = 0;
					mHandler.sendEmptyMessage(1);
				}
				return true;
			}
			return false;
		}

		// 새로운 도형을 목록에 추가한다.
		void AddNewShape() {
			Shape shape = new Shape();
			int idx;
			boolean bFindIntersect;
			Rect rt = new Rect();

			// 기존 도형과 겹치지 않는 새 위치를 찾는다.
			for (;;) {
				// 크기는 48, 64, 80 중 하나 선택
				int Size = 48 + 16 * Rnd.nextInt(3);

				// 위치는 난수로 선택
				rt.left = Rnd.nextInt(getWidth());
				rt.top = Rnd.nextInt(getHeight());
				rt.right = rt.left + Size;
				rt.bottom = rt.top + Size;

				// 화면을 벗어나면 안된다.
				if (rt.right > getWidth() || rt.bottom > getHeight()) {
					continue;
				}

				// 기존 도형 순회하며 겹치는지 조사한다.
				bFindIntersect = false;
				for (idx = 0; idx < arShape.size(); idx ++) {
					if (rt.intersect(arShape.get(idx).rt) == true) {
						bFindIntersect = true;
					}
				}

				// 겹치지 않을 때 확정한다. 겹치면 계속 새 위치 선정한다.
				if (bFindIntersect == false) {
					break;
				}
			}

			// 새 도형 정보 작성. 사진은 난수 선택한다.
			shape.what = Rnd.nextInt(3);
			shape.rt = rt;
			arShape.add(shape);
		}

		// x, y 위치의 도형 번호를 찾는다. 도형이 없는 위치면 -1 리턴
		int FindShapeIdx(int x, int y) {
			int idx;

			for (idx = 0; idx < arShape.size(); idx ++) {
				if (arShape.get(idx).rt.contains(x, y)) {
					return idx;
				}
			}
			return -1;
		}

		// 새 도형을 추가하고 화면을 다시 그린다. 딜레이를 주기 위해 핸들러를 사용했다.
		Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					AddNewShape();
					status = PLAY;
					invalidate();

					String title;
					title = "MemoryPowerBaby - " + arShape.size() + " 단계";
					mParent.setTitle(title);
					break;
				case 1:
					mShowCount++;
					invalidate();
					// 종료하기 전에 총 8회 화면을 갱신한다.
					if (mShowCount == 8) {
						new AlertDialog.Builder(getContext())
						.setMessage("재미있지! 또 할래?")
						.setTitle("게임 끝")
						.setPositiveButton("함더", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								arShape.clear();
								status = BLANK;
								invalidate();
								mHandler.sendEmptyMessageDelayed(0,DELAY);
							}
						})
						.setNegativeButton("안해", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								mParent.finish();
							}
						})
						.show();
					} else {
						mHandler.sendEmptyMessageDelayed(1,400);
					}
					
					break;
				}
			}
		};
	}
}
