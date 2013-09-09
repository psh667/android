package pro.android;

import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class ButtonActivity extends Activity 
{
	@Override 
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.buttons);

//		Button btn1 = (Button)this.findViewById(R.id.ccbtn1);
//		btn1.setOnClickListener(new OnClickListener()
//		{
//			public void onClick(View v)
//			{
//			Intent intent = getButtonIntent();
//			intent.setAction("여기에 인텐트 지정");
//			setResult(RESULT_OK, intent);
//			finish();
//		}
//		});

//		public void myClickHandler(View target) {
//			switch(target.getId()) {
//			case   R.id.ccbtn1:
//			;	
//			break;
//			}
//		}

		ImageButton btn2 = (ImageButton)this.findViewById(R.id.imageBtn1);
//		btn2.setImageResource(R.drawable.icon);

		ToggleButton btn3 = (ToggleButton)this.findViewById(R.id.cctglBtn1);
		
		CheckBox btn4 = (CheckBox)this.findViewById(R.id.ccchbBtn1);
		CheckBox btn5 = (CheckBox)this.findViewById(R.id.ccchbBtn2);
		CheckBox btn6 = (CheckBox)this.findViewById(R.id.ccchbBtn3);
		
		RadioGroup btnRgp = (RadioGroup)this.findViewById(R.id.rBtnGrp);
		RadioButton rbtn1  = (RadioButton)this.findViewById(R.id.fishRBtn);
		rbtn1.setChecked(true);

		RadioButton rbtn2  = (RadioButton)this.findViewById(R.id.anotherRadBtn);
		RadioGroup btnRgp2 = (RadioGroup)this.findViewById(R.id.rdGrp);
		RadioButton newRadioBtn  = new RadioButton(this);
		newRadioBtn.setText("돼지고기");
		btnRgp2.addView(newRadioBtn);

	}
}
