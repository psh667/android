package andexam.ver4_1.c15_resource;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

public class ResDensity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resdensity);
		
		mHandler.sendEmptyMessageDelayed(0,500);
	}
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			ImageView imgnodpi = (ImageView)findViewById(R.id.imgnodpi);
			((TextView)findViewById(R.id.txtnodpi)).setText(String.format("%d*%d", 
					imgnodpi.getWidth(), imgnodpi.getHeight()));

			ImageView imgldpi = (ImageView)findViewById(R.id.imgldpi);
			((TextView)findViewById(R.id.txtldpi)).setText(String.format("%d*%d", 
					imgldpi.getWidth(), imgldpi.getHeight()));

			ImageView imgmdpi = (ImageView)findViewById(R.id.imgmdpi);
			((TextView)findViewById(R.id.txtmdpi)).setText(String.format("%d*%d", 
					imgmdpi.getWidth(), imgmdpi.getHeight()));
			
			ImageView imghdpi = (ImageView)findViewById(R.id.imghdpi);
			((TextView)findViewById(R.id.txthdpi)).setText(String.format("%d*%d", 
					imghdpi.getWidth(), imghdpi.getHeight()));
		}
	};
}
