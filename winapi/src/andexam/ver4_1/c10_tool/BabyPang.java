package andexam.ver4_1.c10_tool;

import java.util.*;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.util.*;
import android.view.*;

public class BabyPang extends Activity {
	GameView mView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = new GameView(this);
		setContentView(mView);
	}

	public void onPause() {
		super.onPause();
		mView.onPause();
	}

	public void onResume() {
		super.onResume();
		mView.onResume();
	}

	class GameView extends View {
		Activity mParent;					// 부모 액티비티
		int mMaxNum = 6;					// 숫자의 개수. 최대 9
		int mWidth = 7;						// 게임판의 가로 크기. 최대 29
		int mHeight = 8;					// 게임판의 세로 크기. 최대 39
		int mTileWidth, mTileHeight;		// 타일 하나의 크기
		int mTileMargin;					// 타일간의 간격
		Rect mTimeRect;						// 시간을 출력할 영역
		Point mScorePt;						// 점수를 출력할 좌표
		Point mBoardPt;						// 게임판 좌상단 좌표
		int mRemoveSpeed = 20;				// 타일 제거 속도
		int mRemoveDelay = 200;				// 타일 제거후 대기 시간
		int mMakeDelay = 200;				// 새 타일 만든 후 대기 시간
		int mGameTime = 60;					// 게임 지속 시간
		int mEllapse;						// 게임 경과 시간
		int mScore;							// 현재 점수
		int mCombo;							// 콤보 회수
		boolean mStarting;					// 게임 초기화중. 점수 계산하지 않음
		int mStatus;						// 현재 상태
		final static int PLAY = 0;			// 게임중. 입력 가능
		final static int REMOVE = 1;		// 제거중
		final static int MAKE = 2;			// 새로 만드는 중
		int mNowX = -1, mNowY = -1;			// 최초 터치한 타일 좌표
		float mScale = 1.0f;				// 축소 진행값 
		Paint mTimeEdgePaint, mTimeGaugePaint, mScorePaint;
		// 비트맵 배열
		Bitmap[] mBaby = new Bitmap[7];
		// 게임판 배열
		int[][] mBoard = new int[30][40];
		// 삭제할 타일 목록 배열
		boolean[][] mDeadTile = new boolean[30][40];
		// 난수, 진동, 사운드 관련 객체
		Random Rnd = new Random();
		Vibrator mVib;
		SoundPool mPool;
		int mRemoveSound;
		
		public GameView(Context context) {
			super(context);
			mParent = (Activity)context;

			// 비트맵 읽음
			Resources res = getResources();
			mBaby[1] = BitmapFactory.decodeResource(res, R.drawable.baby1);
			mBaby[2] = BitmapFactory.decodeResource(res, R.drawable.baby2);
			mBaby[3] = BitmapFactory.decodeResource(res, R.drawable.baby3);
			mBaby[4] = BitmapFactory.decodeResource(res, R.drawable.baby4);
			mBaby[5] = BitmapFactory.decodeResource(res, R.drawable.baby5);
			mBaby[6] = BitmapFactory.decodeResource(res, R.drawable.baby6);
			
			// 진동 및 사운드 재생 준비
			mVib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
			mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
			mRemoveSound = mPool.load(context, R.raw.remove, 1);

			// 타일 하나의 크기를 계산해 둔다.
			DisplayMetrics dm = getResources().getDisplayMetrics();
			mTileWidth = mTileHeight = dm.widthPixels / mWidth;
			mTileMargin = (int)(mTileWidth * 0.02f);
			
			// 시간 및 점수 출력 영역
			mTimeRect = new Rect(0, 0, dm.widthPixels * 7/10, dm.heightPixels / 10);
			mTimeRect.inset(mTimeRect.width() * 5/100, mTimeRect.height() * 20/100);
			mScorePt = new Point(dm.widthPixels * 95/100, mTimeRect.bottom);
			mBoardPt = new Point((dm.widthPixels - mTileWidth * mWidth) / 2,
					dm.heightPixels/10);
			
			// Paint 객체 생성
			mTimeEdgePaint = new Paint();
			mTimeEdgePaint.setStyle(Paint.Style.STROKE);
			mTimeEdgePaint.setColor(Color.BLUE);
			mTimeEdgePaint.setStrokeWidth(mTimeRect.height()/10);
			mTimeGaugePaint = new Paint();
			mTimeGaugePaint.setColor(0xff008000);
			mScorePaint = new Paint();
			mScorePaint.setTextAlign(Paint.Align.RIGHT);
			mScorePaint.setTextSize(mTimeRect.height());

			startGame();
		}
		
		// 게임 일시 정지 및 재개 처리
		boolean mHasTimer;
		void onPause() {
			mHasTimer = mHandler.hasMessages(100); 
			if (mHasTimer) {
				mHandler.removeMessages(100);
			}
		}
		
		void onResume() {
			if (mHasTimer) {
				mHandler.sendEmptyMessage(100);
			}
		}
		
		// 새 게임을 시작한다.
		void startGame() {
			// 게임판을 난수로 채워 넣는다.
			for (int x=0;x<mWidth;x++) {
				for (int y=0;y<mHeight;y++) {
					mBoard[x][y] = Rnd.nextInt(mMaxNum) + 1;
				}
			}

			// 점수와 시간을 초기화한다.
			mScore = 0;
			mEllapse = 0;

			// 연속된 타일이 있으면 제거한다. 없으면 바로 시작한다.
			if (checkContinuousTile()) {
				mStarting = true;
				mStatus = REMOVE;
				mScale = 1.0f;
				mHandler.sendEmptyMessageDelayed(0, mMakeDelay);
			} else {
				mStarting = false;
				mStatus = PLAY;
				mHandler.sendEmptyMessage(100);
			}
		}
		
		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);

			// 남은 시간과 점수를 그린다. 시작중일 때는 꽉 찬 형태로 그림
			Rect timert = new Rect(mTimeRect);
			if (mStarting == false) {
				timert.right = Math.max(mTimeRect.right - mEllapse * 
						mTimeRect.width() / mGameTime, mTimeRect.left);
			}
			canvas.drawRect(timert, mTimeGaugePaint);
			canvas.drawRect(mTimeRect, mTimeEdgePaint);

			// 점수를 출력한다.
			canvas.drawText("" + mScore, mScorePt.x, mScorePt.y, mScorePaint);
			
			// 게임판을 그린다.
			for (int x=0;x<mWidth;x++) {
				for (int y=0;y<mHeight;y++) {
					// 일시적으로 제거된 빈칸은 그리지 않는다.
					if (mBoard[x][y] == -1) continue;
					
					// 타일의 영역에 마진을 뺀다.
					int tx = mBoardPt.x + x * mTileWidth;
					int ty = mBoardPt.y + y * mTileHeight;
					Rect rt = new Rect(tx, ty, tx + mTileWidth, ty + mTileHeight);
					rt.inset(mTileMargin, mTileMargin);
					
					// 삭제된 타일은 점점 작아진다.
					if (mStatus == REMOVE && mDeadTile[x][y]) {
						rt.inset((int)(rt.width() / 2 * (1 - mScale)), 
								(int)(rt.height() / 2 * (1 - mScale)));
					}

					// 비트맵을 출력한다.
					canvas.drawBitmap(mBaby[mBoard[x][y]], null, rt, null);
				}
			}
		}
		
		public boolean onTouchEvent(MotionEvent event) {
			// 애니메이션 중에는 터치 입력을 무시한다.
			if (mStatus != PLAY) {
				return true;
			}
			// 최초 터치시 터치한 좌표를 기억해 둔다.
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				mNowX = getBoardX(event.getX());
				mNowY = getBoardY(event.getY());
				return true;
			}

			// 이동시 이동한 방향을 판별한다.
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				// 한번 이동했으면 다음 터치시까지 무시한다.
				if (mNowX == -1) return true;
				
				int nx = getBoardX(event.getX());
				int ny = getBoardY(event.getY());
				
				// 교체 가능하면 교체한다.
				if (checkSwappable(mNowX, mNowY, nx, ny)) {
					swapTile(mNowX, mNowY, nx, ny);
					mNowX = mNowY = -1;
					invalidate();
					mCombo = 1;
					mStatus = REMOVE;
					mScale = 1.0f;
					mHandler.sendEmptyMessage(0);
				}
			}
			return true;
		}

		// 화면상의 좌표를 게임판상의 좌표로 바꾼다.
		// 게임판을 벗어나면 안쪽으로 넣어 준다. 
		int getBoardX(float scrx) {
			int x = (int)((scrx - mBoardPt.x) / mTileWidth);
			if (x < 0) x = 0;
			if (x > mWidth - 1) x = mWidth - 1;
			return x;
		}
		
		int getBoardY(float scry) {
			int y = (int)((scry - mBoardPt.y) / mTileHeight);
			if (y < 0) y = 0;
			if (y > mHeight - 1) y = mHeight - 1;
			return y;
		}
		
		// 교체 가능한지 점검한다.
		boolean checkSwappable(int x1, int y1, int x2, int y2) {
			// 같은 위치이면 교체 불가하다.
			if (x1 == x2 && y1 == y2) return false;
			
			// 대각선 이동은 불가능하다.
			if (x1 != x2 && y1 != y2) return false;
			
			// 두칸 이상 이동은 불가하다.
			if (Math.abs(x1 - x2) > 1 || Math.abs(y1 - y2) > 1) return false;
			
			// 타일을 바꿔 보고 연속성 여부를 판별한다.
			swapTile(x1, y1, x2, y2);
			boolean swappable = checkContinuousTile(); 
			swapTile(x1, y1, x2, y2);
			return swappable;
		}

		// 배열의 두 타일을 교환한다.
		void swapTile(int x1, int y1, int x2, int y2) {
			int swaptemp = mBoard[x1][y1];
			mBoard[x1][y1] = mBoard[x2][y2];
			mBoard[x2][y2] = swaptemp;
		}
		
		// 3회 이상 같은 숫자가 있는지 점검하고 삭제 대상 타일에 마킹한다.
		boolean checkContinuousTile() {
			boolean bFound = false;
			// 모든 칸을 리셋한다.
			for (int x=0;x<mWidth;x++) {
				for (int y=0;y<mHeight;y++) {
					mDeadTile[x][y] = false;
				}
			}

			// 전체 순회하며 3연속을 찾는다.
			for (int x=0;x<mWidth;x++) {
				for (int y=0;y<mHeight;y++) {
					int num = mBoard[x][y];
					
					// 수평 3개가 같으면 제거 대상이다. 첫열, 끝열은 검사하지 않는다.
					if (x > 0 && x < mWidth - 1) {
						if (mBoard[x-1][y] == num && mBoard[x+1][y] == num) {
							mDeadTile[x-1][y] = true;
							mDeadTile[x][y] = true;
							mDeadTile[x+1][y] = true;
							bFound = true;
						}
					}
					
					// 수직 3개가 같으면 제거 대상이다. 첫행, 끝행은 검사하지 않는다.
					if (y > 0 && x < mHeight - 1) {
						if (mBoard[x][y-1] == num && mBoard[x][y+1] == num) {
							mDeadTile[x][y-1] = true;
							mDeadTile[x][y] = true;
							mDeadTile[x][y+1] = true;
							bFound = true;
						}
					}
				}
			}
			
			return bFound;
		}
		
		// 연속된 타일을 제거한다.
		void removeTiles() {
			mPool.play(mRemoveSound, 1, 1, 0, 0, 1);
			mVib.vibrate(100);
			int count = 0;
			// 제거할 타일을 기록한다.
			for (int x=0;x<mWidth;x++) {
				for (int y=0;y<mHeight;y++) {
					if (mDeadTile[x][y]) {
						mBoard[x][y] = 0;
						count++;
					}
				}
			}

			// 시작 중에는 점수를 증가시키지 않는다.
			if (mStarting == false) {
				mScore += (count * mCombo);
			}
			
			// 밑에서부터 순서대로 제거해온다.
			boolean bFound;
			for (int y = mHeight - 1;y >= 0;y--) {
				bFound = false;
				for (int x=0;x<mWidth;x++) {
					if (mBoard[x][y] == 0) {
						bFound = true;
						for (int ty = y;ty > 0;ty--) {
							mBoard[x][ty] = mBoard[x][ty-1];
						}
						// 새로 만들어야 할 타일에 -1을 기록한다.
						mBoard[x][0] = -1;
					}
				}
				
				// 제거했으면 그 줄은 다시 검사한다.
				if (bFound) y++;
			}
		}
		
		// 새로 생긴 빈칸에 새 타일을 만든다.
		void makeNewTile() {
			for (int x=0;x<mWidth;x++) {
				for (int y=0;y<mHeight;y++) {
					if (mBoard[x][y] == -1) {
						mBoard[x][y] = Rnd.nextInt(mMaxNum) + 1;
					}
				}
			}
		}

		Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				// 제거할 타일을 점점 작게 축소한다.
				case 0:
					mScale -= 0.1f;
					if (mScale > 0.1f) {
						sendEmptyMessageDelayed(0, mRemoveSpeed);
						invalidate();
					} else {
						mStatus = MAKE;
						sendEmptyMessage(1);
					}
					break;
				// 타일을 제거한다.
				case 1:
					removeTiles();
					invalidate();
					sendEmptyMessageDelayed(2, mRemoveDelay);
					break;
				// 새 타일을 만든다.
				case 2:
					makeNewTile();
					invalidate();
					sendEmptyMessageDelayed(3, mMakeDelay);
					break;
				// 다시 점검한다.
				case 3:
					if (checkContinuousTile()) {
						mStatus = REMOVE;
						mScale = 1.0f;
						mCombo++;
						sendEmptyMessage(0);
					} else {
						mStatus = PLAY;
						if (mStarting) {
							mStarting = false;
							// 규칙에 맞지 않은 벽돌 완전 제거 후 타이머를 시작한다. 
							mHandler.sendEmptyMessage(100);
						}
					}
					invalidate();
					break;
				case 100:
					if (mEllapse > mGameTime) {
						new AlertDialog.Builder(mParent)
						.setTitle("Game Over")
						.setMessage("다시 시작할까요?")
						.setPositiveButton("예", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								startGame();
							}
						})
						.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								mParent.finish();
							}
						})
						.show();
					} else {
						mEllapse++;
						invalidate();
						mHandler.sendEmptyMessageDelayed(100, 1000);
					}
					break;
				}
			}
		};
	}
}
