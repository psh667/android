package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class OrderDialog extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
	}
	
	public void mOnClick(View v) {
		final LinearLayout linear = (LinearLayout)
			View.inflate(this, R.layout.order, null);
	
		new AlertDialog.Builder(this)
		.setTitle("주문 정보를 입력하시오.")
		.setIcon(R.drawable.androboy)
		.setView(linear)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				EditText product = (EditText)linear.findViewById(R.id.product);
				EditText number = (EditText)linear.findViewById(R.id.number);
				CheckBox paymethod = (CheckBox)linear.findViewById(R.id.paymethod);
				TextView text = (TextView)findViewById(R.id.text);
				text.setText("주문 정보 " + product.getText() + " 상품 " + 
						number.getText() + "개." +
						(paymethod.isChecked() ? "착불결제":""));
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				TextView text = (TextView)findViewById(R.id.text);
				text.setText("주문을 취소했습니다.");
			}
		})
		.show();
	}
}

