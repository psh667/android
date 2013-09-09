package pro.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RelativeLayoutActivity extends Activity 
{
	@Override 
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.layout_relative);

        findViewById(R.id.userNameLbl);
        EditText et1 =(EditText)this.findViewById(R.id.userNameText);
        et1.setText("");

        findViewById(R.id.pwdLbl);
        EditText et2 =(EditText)this.findViewById(R.id.pwdText);
        et2.setText("");
        
        findViewById(R.id.pwdHintLbl);
        findViewById(R.id.disclaimerLbl);
        
        Button btn_frmlayout = (Button)this.findViewById(R.id.ccbtn_frmLO);
        btn_frmlayout.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.FrameLayoutActivity"));
    			startActivity(intent);
	        }
        });
        
        Button btn_frmlayout2 = (Button)this.findViewById(R.id.ccbtn_frmLO2);
        btn_frmlayout2.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.FrameLayout2Activity"));
    			startActivity(intent);
	        }
        });
    }
}
