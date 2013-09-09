package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class ActionProviderTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setText("액션 프로바이더를 테스트합니다.");
		setContentView(text);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionprovidermenu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "Counter Menu Item selected - onOptionsItemSelected", 0).show();

		return true;
	}	

	public static class CounterProvider extends ActionProvider {
		Context mContext;
		TextView mCountText;

		public CounterProvider(Context context) {
			super(context);
			mContext = context;
		}

		public View onCreateActionView() {
			LayoutInflater Inflater = LayoutInflater.from(mContext);
			View linear = Inflater.inflate(R.layout.counterprovider, null);
			mCountText = (TextView)linear.findViewById(R.id.count);

			Button btnInc = (Button)linear.findViewById(R.id.btnincrease);
			btnInc.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(mCountText.getText().toString());
					mCountText.setText(Integer.toString(count + 1));
				}
			});

			Button btnDec = (Button)linear.findViewById(R.id.btndecrease);
			btnDec.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(mCountText.getText().toString());
					mCountText.setText(Integer.toString(count - 1));
				}
			});
			return linear;
		}

		public boolean onPerformDefaultAction() {
			Toast.makeText(mContext, "Counter Menu Item selected - " + 
					"onPerformDefaultAction", 0).show();
			return true;
		}
	}
}
