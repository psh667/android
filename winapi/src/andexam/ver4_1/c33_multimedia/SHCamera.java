package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import java.io.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.hardware.Camera;
import android.hardware.Camera.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class SHCamera extends Activity {
	String mRootPath;
	LinearLayout mTakePicture;
	ImageView mReview;
	SHCameraSurface mSurface;
	int mPicWidth, mPicHeight;
	int mSelect;
	String mLastPicture = "";
	static final String PICFOLDER = "SHCamera";
	static final int TAKEDELAY = 300;
	Context mMainContext;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shcamera);
		
		mMainContext = this;
		
		// SD 카드가 없을 시 에러 처리한다.
		String ext = Environment.getExternalStorageState();
		if (ext.equals(Environment.MEDIA_MOUNTED) == false) {
			Toast.makeText(this, "SD 카드가 반드시 필요합니다.", 1).show();
			finish();
			return;
		}
		
		mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + 
			"/" + PICFOLDER;
		File fRoot = new File(mRootPath);
		if (fRoot.exists() == false) {
			if (fRoot.mkdir() == false) {
				Toast.makeText(this, "사진을 저장할 폴더가 없습니다.", 1).show();
				finish();
				return;
			}
		}
		
		// 프레퍼런스에서 크기 읽어 옴
		SharedPreferences pref = getSharedPreferences("SHCamera",0);
		mPicWidth = pref.getInt("PicWidth", -1);
		mPicHeight = pref.getInt("PicHeight", -1);

		// 버튼들의 클릭 리스너 지정
		mTakePicture = (LinearLayout)findViewById(R.id.takepicture);
		mReview = (ImageView)findViewById(R.id.imgreview);
		mSurface = (SHCameraSurface)findViewById(R.id.preview);
		findViewById(R.id.btnsize).setOnClickListener(mSizeClick);
		findViewById(R.id.btnreview).setOnClickListener(mReviewClick);
		findViewById(R.id.btntake).setOnClickListener(mTakeClick);
		((CheckBox)findViewById(R.id.btnmacro)).setOnCheckedChangeListener(toggleMacro);
	}

	public void onDestroy() {
		super.onDestroy();
	}

	// 사진 확인 상태에서 Back누르면 촬영 모드로 전환
	public boolean onKeyUp (int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mReview.getVisibility() == View.VISIBLE) {
			mReview.setVisibility(View.GONE);
			mTakePicture.setVisibility(View.VISIBLE);
			return true;
		} else {
			super.onKeyUp(keyCode, event);
			return false;
		}
	}

	// 사진 촬영
	Button.OnClickListener mTakeClick = new Button.OnClickListener() {
		public void onClick(View v) {
			mSurface.mCamera.autoFocus(mAutoFocus);
		}
	};
	
	// 포커싱 성공하면 촬영
	AutoFocusCallback mAutoFocus = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				mTakePicture.postDelayed(new Runnable() {
					public void run() {
						mSurface.mCamera.takePicture(null, null, mPicture);
					}
				}, TAKEDELAY);
			} else {
				Toast.makeText(mMainContext, "초점을 잡을 수 없습니다.", 1).show();
			}
		}
	};

	// 사진 저장. 날짜와 시간으로 파일명 결정하고 저장후 미디어 스캔 실행
	PictureCallback mPicture = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Calendar calendar = Calendar.getInstance();
			String FileName = String.format("SH%02d%02d%02d-%02d%02d%02d.jpg", 
					calendar.get(Calendar.YEAR) % 100, calendar.get(Calendar.MONTH)+1, 
					calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), 
					calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
			String path = mRootPath + "/" + FileName;

			File file = new File(path);
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				fos.close();
			} catch (Exception e) {
				Toast.makeText(mMainContext, "파일 저장 중 에러 발생 : " + 
						e.getMessage(), 0).show();
				return;
			}

			// 스캐닝 요청
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.parse("file://" + path);
			intent.setData(uri);
			sendBroadcast(intent);

			mLastPicture = path;
			mSurface.mCamera.startPreview();
		}
	};
	
	// 크기 변경
	Button.OnClickListener mSizeClick = new Button.OnClickListener() {
		public void onClick(View v) {
			Parameters params = mSurface.mCamera.getParameters();
			final List<Size> arSize = params.getSupportedPictureSizes();
			String[] arName = new String[arSize.size()];
			for (int i = 0; i < arSize.size(); i++) {
				Size s = arSize.get(i);
				arName[i] = String.format("%d * %d", s.width, s.height);
				if (mPicWidth == s.width && mPicHeight == s.height) {
					mSelect = i;
				}
			}
			
			new AlertDialog.Builder(mMainContext)
			.setTitle("사진 해상도 선택")
			.setSingleChoiceItems(arName, mSelect, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					mSelect = which;
				}
			})
			.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Parameters params = mSurface.mCamera.getParameters();
					int width = arSize.get(mSelect).width;
					int height = arSize.get(mSelect).height;
					params.setPictureSize(width, height);
					mSurface.mCamera.setParameters(params);

					SharedPreferences pref = getSharedPreferences("SHCamera",0);
					SharedPreferences.Editor edit = pref.edit();
					edit.putInt("PicWidth", width);
					edit.putInt("PicHeight", height);
					edit.commit();
				}
			})
			.setNegativeButton("취소", null)
			.show();
		}
	};
	
	// 사진 확인
	Button.OnClickListener mReviewClick = new Button.OnClickListener() {
		public void onClick(View v) {
			if (mLastPicture.length() != 0) {
				mReview.setVisibility(View.VISIBLE);
				mTakePicture.setVisibility(View.GONE);
				try {
					BitmapFactory.Options opt = new BitmapFactory.Options();
					opt.inSampleSize = 4;
					Bitmap bm = BitmapFactory.decodeFile(mLastPicture, opt);
					mReview.setImageBitmap(bm);
				}
				catch (OutOfMemoryError e) {
					Toast.makeText(mMainContext,"이미지를 읽을 수 없습니다", 0).show();
				}
				
				mReview.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						mReview.setVisibility(View.GONE);
						mTakePicture.setVisibility(View.VISIBLE);
						return false;
					}
				});
			} else {
				Toast.makeText(mMainContext,"아직 찍은 사진이 없습니다.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}; 

	// 매크로 모드 토글
	CompoundButton.OnCheckedChangeListener toggleMacro = new CompoundButton.OnCheckedChangeListener() {
		public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
			if (buttonView.getId() == R.id.btnmacro) {
				if (isChecked) {
					Parameters params = mSurface.mCamera.getParameters();
					params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
					mSurface.mCamera.setParameters(params);
				} else {
					Parameters params = mSurface.mCamera.getParameters();
					params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
					mSurface.mCamera.setParameters(params);
				}
			}
		}
	};
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	
		menu.add(0,1,0,"소개");
		menu.add(0,2,0,"종료");

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			new AlertDialog.Builder(this)
			.setTitle("프로그램 소개")
			.setMessage("이 프로그램은 필수 기능만 제공하는 카메라입니다. 기존 카메라는 화면의 셔터 버튼을 " + 
					"놓을 때 장비의 떨림으로 인해 사진의 정확도가 떨어지나 이 프로그램은 터치를 놓은 후 초점을 " + 
					"맞추고 잠시 대기 후 사진을 찍어 선명도를 극대화합니다. 잘생긴 사람은 누구나 무료로 사용할 수 있습니다.")
			.setPositiveButton("닫기", null)
			.show();
			return true;
		case 2:
			finish();
			System.exit(0);
			return true;
		}
		return false;
	}
}

// 미리보기 표면 클래스
class SHCameraSurface extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Context mContext;
	Camera mCamera;

	public SHCameraSurface(Context context) {
		super(context);
		init(context);
	}

	public SHCameraSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SHCameraSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	void init(Context context) {
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	// 표면 생성시 카메라 오픈하고 미리보기 설정
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(mHolder);
		} catch (IOException e) {
			mCamera.release();
			mCamera = null;
		}
	}

	// 표면 파괴시 카메라도 파괴한다.
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	// 표면의 크기가 결정될 때 최적의 미리보기 크기를 구해 설정한다.
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		Camera.Parameters params = mCamera.getParameters();
		List<Size> arSize = params.getSupportedPreviewSizes();
		if (arSize == null) {
			params.setPreviewSize(width, height);
		} else {
			int diff = 10000;
			Size opti = null;
			for (Size s : arSize) {
				if (Math.abs(s.height - height) < diff) {
					diff = Math.abs(s.height - height);
					opti = s;
					
				}
			}
			params.setPreviewSize(opti.width, opti.height);
		}
		
		SHCamera SHCamera = (SHCamera)mContext;
		if (SHCamera.mPicWidth != -1) {
			params.setPictureSize(SHCamera.mPicWidth, SHCamera.mPicHeight);
		}
		mCamera.setParameters(params);
		mCamera.startPreview();
	}
}
