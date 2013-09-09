package pro.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Table2Activity extends Activity 
{
	@Override 
	protected void  onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_table_sub1);

		this.findViewById(R.id.table2);

		// EditText가 들어있는 테이블 레이아웃으로 가는 버튼
		Button btn_tb2 = (Button)this.findViewById(R.id.ccbtn_tbLO3);
        btn_tb2.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.Table3Activity"));
    			startActivity(intent);
	        }
        });
	}
}
