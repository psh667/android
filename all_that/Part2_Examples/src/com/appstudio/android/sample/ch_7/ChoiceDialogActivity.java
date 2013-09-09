package com.appstudio.android.sample.ch_7;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ChoiceDialogActivity extends Activity {
	final CharSequence[] items = {"사과", "바나나", "오렌지", "키위"};
	boolean[] selections =  new boolean[ items.length ];
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choicedialogmain);
		Button listbutton = (Button) findViewById(R.id.listbutton);
		
    	final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		listbutton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	builder.setTitle("다음 과일을 골라 주세요")
		    	        .setItems(items, new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int item) {
		    	    	Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
		    	    }
		    	});
		    	AlertDialog alert = builder.create();
		    	alert.show();
		    }
		});

		final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		Button radiobutton = (Button) findViewById(R.id.radiobutton);
		radiobutton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	builder1.setTitle("다음 과일 중 한개만 골라 주세요")
		    	        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int item) {
		    	    	selections [item] = true;
		    	    	Toast.makeText(getApplicationContext(), "선택된 아이템은 "+items[item] + " 입니다.", Toast.LENGTH_SHORT).show();
		    	    }
		    	}).setPositiveButton("확인", new DialogButtonClickHandler());
		    	AlertDialog alert = builder1.create();
		    	alert.show();
		    }
		});

		final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		Button checkboxbutton = (Button) findViewById(R.id.checkboxbutton);
		checkboxbutton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // Perform action on clicks
		    	builder2.setTitle("다음 과일 중 마음껏 골라 주세요")
		    	        .setMultiChoiceItems(items, selections, new DialogSelectionClickHandler(
		    			)).setPositiveButton("확인", new DialogButtonClickHandler());
		    	AlertDialog alert = builder2.create();
		    	alert.show();
		    }
		});
	}
	
	public class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener
	{
		@Override
		public void onClick(DialogInterface arg0, int item, boolean checked) {
			selections [item] = checked;
			Log.i( "선택 과일 ", items[ item ] + " selections: " + checked );
		}
	}
	
	public class DialogButtonClickHandler implements DialogInterface.OnClickListener
	{
		public void onClick( DialogInterface dialog, int clicked )
		{
			switch( clicked )
			{
				case DialogInterface.BUTTON_POSITIVE:
					printSelectedFruit();
					break;
			}
		}
	}
	
	protected void printSelectedFruit(){
		CharSequence retValue = "";
		for( int i = 0; i < items.length; i++ ){
			retValue = retValue + ( items[ i ] + " selected: " + selections[i]  + "\n");
			Log.i( "선택 과일 ", items[ i ] + " selected: " + selections[i] );
		}
        Toast.makeText(getApplicationContext(), retValue, Toast.LENGTH_LONG).show();
	}
}
