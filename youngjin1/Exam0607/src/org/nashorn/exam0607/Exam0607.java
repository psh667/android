package org.nashorn.exam0607;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class Exam0607 extends Activity {
	private CalendarView mCalendarView;
	private Button		mPrevYearButton;
	private Button		mNextYearButton;
	private Button 		mPrevButton;
	private Button 		mNextButton;
	private EditText	mDateText;
	
	protected CalendarEngine calendar = new CalendarEngine();
	
	public void displayCalendar()
	{
		mDateText.setText(calendar.getCurrentYear()+"년 "+
        		calendar.getCurrentMonth()+"월 "+
        		calendar.getCurrentDay()+"일");
		
		mCalendarView.invalidate();
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        calendar.moveCurrentDate();
        
        // Create a simple layout
        RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(
        		LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        mPrevYearButton = new Button(this); 
        layout.addView(mPrevYearButton, 
        		new RelativeLayout.LayoutParams(
                		60,LayoutParams.WRAP_CONTENT));
        mPrevYearButton.setText("<<");
        mPrevYearButton.setId(1);
        mPrevYearButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				calendar.movePrevYear();
				displayCalendar();
			}
        });
        
        mPrevButton = new Button(this);
        mPrevButton.setText("<");        
        layout.addView(mPrevButton);
        {
	        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
	        		50,LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.RIGHT_OF, 1);
			mPrevButton.setLayoutParams(lp);
        }
        mPrevButton.setId(11);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				calendar.movePrevMonth();
				displayCalendar();
			}
        });
        
        mNextButton = new Button(this);
        mNextButton.setText(">");        
        layout.addView(mNextButton);
        {
	        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
	        		50,LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.LEFT_OF, 3);
	        mNextButton.setLayoutParams(lp);
        }
        mNextButton.setId(13);
        mNextButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				calendar.moveNextMonth();
				displayCalendar();
			}
        });
        
        mNextYearButton = new Button(this);
        mNextYearButton.setText(">>");        
        layout.addView(mNextYearButton);
        {
	        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
	        		60,LayoutParams.WRAP_CONTENT);
			//lp.addRule(RelativeLayout.RIGHT_OF, 2);
	        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
	        mNextYearButton.setLayoutParams(lp);
        }
        mNextYearButton.setId(3);
        mNextYearButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				calendar.moveNextYear();
				displayCalendar();
			}
        });

        mDateText = new EditText(this);
        mDateText.setFocusableInTouchMode(false);
        layout.addView(mDateText);
        {
	        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		                		LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	        lp.addRule(RelativeLayout.RIGHT_OF, 11);
	        lp.addRule(RelativeLayout.LEFT_OF, 13);
	        mDateText.setLayoutParams(lp);
        }
        mDateText.setId(2);        
        mDateText.setText(calendar.getCurrentYear()+"년 "+
        		calendar.getCurrentMonth()+"월 "+
        		calendar.getCurrentDay()+"일");
        
        /////////////////////////////////////////////////////////////////
        
       
        // Create Sheet View
        mCalendarView = new CalendarView(this);
        layout.addView(mCalendarView);
        {
        	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
	                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT); 
        	lp.addRule(RelativeLayout.BELOW, 2);
        	mCalendarView.setLayoutParams(lp);
        }
        mCalendarView.setId(5);
        
        // Set the layout as our content view
        setContentView(layout);    
    }
}