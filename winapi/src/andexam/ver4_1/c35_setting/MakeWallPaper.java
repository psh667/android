package andexam.ver4_1.c35_setting;

import andexam.ver4_1.*;
import java.io.*;

import android.app.*;
import android.graphics.*;
import android.graphics.Shader.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class MakeWallPaper extends Activity {
	ImageView mPreview;
	WallpaperManager mWm;
	Spinner mSpinner1, mSpinner2, mSpinnerDir;
	RadioGroup mRadioType;
	final static int WIDTH = 120;
	final static int HEIGHT = 100;
	int[] arColor = new int[] {0xff000000, 0xffffffff, 0xff404040, 0xff808080, 
			0xffc0c0c0,	0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffff00, 
			0xff00ffff, 0xffff00ff, 0xff008000, 0xff000080};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makewallpaper);
		
		mRadioType = (RadioGroup)findViewById(R.id.radiotype);
		
		ArrayAdapter<CharSequence> adColor = ArrayAdapter.createFromResource(this, 
				R.array.colors, android.R.layout.simple_spinner_item);
		adColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner1 = (Spinner)findViewById(R.id.colorspinner1);
		mSpinner1.setPrompt("첫 번째 색상을 고르세요.");
		mSpinner1.setAdapter(adColor);
		mSpinner1.setOnItemSelectedListener(mSpinListener);

		mSpinner2 = (Spinner)findViewById(R.id.colorspinner2);
		mSpinner2.setPrompt("두 번째 색상을 고르세요.");
		mSpinner2.setAdapter(adColor);
		mSpinner2.setOnItemSelectedListener(mSpinListener);
		mSpinner2.setSelection(1);

		ArrayAdapter<CharSequence> adDir = ArrayAdapter.createFromResource(this, 
				R.array.gradientdir, android.R.layout.simple_spinner_item);
		adDir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerDir = (Spinner)findViewById(R.id.dirspinner);
		mSpinnerDir.setPrompt("방향을 고르세요.");
		mSpinnerDir.setAdapter(adDir);
		mSpinnerDir.setOnItemSelectedListener(mSpinListener);

		mPreview = (ImageView)findViewById(R.id.preview);
		mWm = WallpaperManager.getInstance(this);
		mPreview.postDelayed(new Runnable() {
			public void run() {
				Drawable wallpaperDrawable = mWm.getDrawable();
				mPreview.setImageDrawable(wallpaperDrawable);
			}
		}, 500);
	}

	OnItemSelectedListener mSpinListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			UpdatePreview();
		}
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};
	
	public void mOnClick(View v) {
		int color1, color2;
		int dir;
		switch (v.getId()) {
		case R.id.btnsolid:
			if (mRadioType.getCheckedRadioButtonId() == R.id.type1) {
				color1 = mSpinner1.getSelectedItemPosition();
				SetWallpaper(MakeSolidBitmap(arColor[color1]));
			} else {
				color1 = mSpinner1.getSelectedItemPosition();
				color2 = mSpinner2.getSelectedItemPosition();
				dir = mSpinnerDir.getSelectedItemPosition();
				SetWallpaper(MakeGradientBitmap(arColor[color1], arColor[color2], dir));
			}
			Toast.makeText(this, "벽지를 변경하였습니다.", 0).show();
			break;
		case R.id.type1:
			mSpinner2.setEnabled(false);
			mSpinnerDir.setEnabled(false);
			UpdatePreview();
			break;
		case R.id.type2:
			mSpinner2.setEnabled(true);
			mSpinnerDir.setEnabled(true);
			UpdatePreview();
			break;
		}
	}
	
	void SetWallpaper(Bitmap bitmap) {
		try {
			mWm.setBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void UpdatePreview() {
		if (mRadioType.getCheckedRadioButtonId() == R.id.type1) {
			mPreview.setImageBitmap(MakeSolidBitmap(
					arColor[mSpinner1.getSelectedItemPosition()]));
		} else {
			mPreview.setImageBitmap(MakeGradientBitmap( 
					arColor[mSpinner1.getSelectedItemPosition()],
					arColor[mSpinner2.getSelectedItemPosition()],
					mSpinnerDir.getSelectedItemPosition()));
		}
	}
	
	Bitmap MakeSolidBitmap(int color) {
		Bitmap BackBit = Bitmap.createBitmap(WIDTH,HEIGHT,Bitmap.Config.ARGB_8888);
		Canvas offscreen = new Canvas(BackBit);
		offscreen.drawColor(color);
		return BackBit;
	}
	
	Bitmap MakeGradientBitmap(int color1, int color2, int dir) {
		Bitmap BackBit;
		Canvas offscreen;
		Paint Pnt;
		BackBit = Bitmap.createBitmap(WIDTH,HEIGHT,Bitmap.Config.ARGB_8888);
		offscreen = new Canvas(BackBit);
		Pnt = new Paint();
		Pnt.setAntiAlias(true);
		switch (dir) {
		case 0:
			Pnt.setShader(new LinearGradient(0,0,0,HEIGHT,
					color1, color2, TileMode.CLAMP));
			break;
		case 1:
			Pnt.setShader(new LinearGradient(0,0,WIDTH,0,
					color1, color2, TileMode.CLAMP));
			break;
		case 2:
			Pnt.setShader(new LinearGradient(0,HEIGHT,WIDTH,0,
					color1, color2, TileMode.CLAMP));
			break;
		case 3:
			Pnt.setShader(new LinearGradient(0,0,WIDTH,HEIGHT,
					color1, color2, TileMode.CLAMP));
			break;
		}
		offscreen.drawRect(0,0,WIDTH,HEIGHT,Pnt);
		return BackBit;
	}
}
