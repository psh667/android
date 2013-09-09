package pro.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn_txts1 = (Button)this.findViewById(R.id.ccbtn_texts1);
        btn_txts1.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.Text1Activity"));
    			startActivity(intent);
	        }
        });

        Button btn_txts2 = (Button)this.findViewById(R.id.ccbtn_texts2);
        btn_txts2.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.Text2Activity"));
    			startActivity(intent);
	        }
        });

        Button btn_btns = (Button)this.findViewById(R.id.ccbtn_btns);
        btn_btns.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.ButtonActivity"));
    			startActivity(intent);
	        }
        });

        Button btn_listview = (Button)this.findViewById(R.id.ccbtn_listview);
        btn_listview.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.ListDemoActivity"));
    			startActivity(intent);
	        }
        });
        
        Button btn_gridview = (Button)this.findViewById(R.id.ccbtn_gridview);
        btn_gridview.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.GridDemoActivity"));
    			startActivity(intent);
	        }
        });

        Button btn_datetime = (Button)this.findViewById(R.id.ccbtn_datetime);
        btn_datetime.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.DateTimeActivity"));
    			startActivity(intent);
	        }
        });
        
        Button btn_dwtgt1 = (Button)this.findViewById(R.id.ccbtn_wtgt1);
        btn_dwtgt1.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.WtGtActivity"));
    			startActivity(intent);
	        }
        });
        
        Button btn_tb1 = (Button)this.findViewById(R.id.ccbtn_tbLO1);
        btn_tb1.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.TableActivity"));
    			startActivity(intent);
	        }
        });
        
        Button btn_rellayout = (Button)this.findViewById(R.id.ccbtn_relLO);
        btn_rellayout.setOnClickListener(new OnClickListener()
        {
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent();
	        	intent.setComponent(new ComponentName(
    			"pro.android"
    			,"pro.android.RelativeLayoutActivity"));
    			startActivity(intent);
	        }
        });
        
        
        
        
    }
}
