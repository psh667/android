package pro.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Table3Activity extends Activity 
{
	@Override 
	protected void  onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_table_sub2);

		this.findViewById(R.id.table3);

		// padding 지정 예제로 가는 버튼
		Button btn_padding = (Button)this.findViewById(R.id.ccbtn_padding);
        btn_padding.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.PaddingActivity"));
    			startActivity(intent);
	        }
        });
	}
}
