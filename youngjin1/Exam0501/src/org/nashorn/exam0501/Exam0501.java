package org.nashorn.exam0501;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Exam0501 extends Activity {
	private EditText editText;
	private DatePicker datePicker;
	private Spinner spinner;
	
	private String[] spinnerItems = {
			"스피너 항목1",
			"스피너 항목2",
			"스피너 항목3",
			"스피너 항목4"
	};
	
	private int year = 2010;
	private int month = 8;
	private int day = 10;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        editText = (EditText)findViewById(R.id.edittext);
        
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radio);
        radioGroup.check(R.id.radio1);

        spinner = (Spinner)findViewById(R.id.spinner);
        
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String> 
        (this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinner.setAdapter(spinnerAdapter);
        
        datePicker = (DatePicker)findViewById(R.id.date);
        datePicker.init(year, month-1, day, 
				new DatePicker.OnDateChangedListener() {
					
					@Override
					public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
						// TODO Auto-generated method stub
						year = arg1;
						month = arg2+1;
						day = arg3;
					}
				}
		);
        
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radio);
		        RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
		        
		        CheckBox checkBox = (CheckBox)findViewById(R.id.checkbox);
				
				Toast.makeText(getBaseContext(), String.format("레이블:%s\n체크박스:%s\n라디오버튼 선택:%s\n스피너 선택:%s\n날짜 선택:%d년 %d월 %d일",
						editText.getText().toString(),
						String.valueOf(checkBox.isChecked()),
						radioButton.getText().toString(),
						spinner.getSelectedItem().toString(),
						year, month, day), Toast.LENGTH_LONG).show();
			}
        });
        
		
		
    }
}