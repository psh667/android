package andexam.ver4_1.c09_menu;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class ContextMenuTest extends Activity {
	Button mBtn;
	EditText mEdit;
	MyImage mImage;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contextmenutest);
		
		mBtn = (Button)findViewById(R.id.button);
		registerForContextMenu(mBtn);
		
		mEdit = (EditText)findViewById(R.id.edittext);
		registerForContextMenu(mEdit);

		mImage = (MyImage)findViewById(R.id.myimage);
		registerForContextMenu(mImage);
	}
	
	public void onCreateContextMenu (ContextMenu menu, View v, 
			ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		if (v == mBtn) {
			menu.setHeaderTitle("Button Menu");
			menu.add(0,1,0,"Red");
			menu.add(0,2,0,"Green");
			menu.add(0,3,0,"Blue");
		}

		if (v == mEdit) {
			menu.add(0,4,0,"번역하기");
			menu.add(0,5,0,"필기 인식");
		}
	}
	
	public boolean onContextItemSelected (MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			mBtn.setTextColor(Color.RED);
			return true;
		case 2:
			mBtn.setTextColor(Color.GREEN);
			return true;
		case 3:
			mBtn.setTextColor(Color.BLUE);
			return true;
		case 4:
			Toast.makeText(this,"번역했다.",Toast.LENGTH_SHORT).show();
			return true;
		case 5:
			Toast.makeText(this,"필기 인식했다.",Toast.LENGTH_SHORT).show();
			return true;
		case 100:
			Toast.makeText(this,"회전했다 치고.",Toast.LENGTH_SHORT).show();
			return true;
		case 101:
			Toast.makeText(this,"크기 변경 했다 치고.",Toast.LENGTH_SHORT).show();
			return true;
		}
		
		return true;
	}
}

class MyImage extends ImageView {
	public MyImage(Context context) {
		super(context);
	}
	
	public MyImage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void onCreateContextMenu(ContextMenu menu) {
		super.onCreateContextMenu(menu);

		menu.setHeaderTitle("MyImage Menu");
		menu.add(0,100,0,"이미지 회전");
		menu.add(0,101,0,"크기 변경");
	}
}
