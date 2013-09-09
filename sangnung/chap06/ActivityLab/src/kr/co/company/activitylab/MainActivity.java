package kr.co.company.activitylab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements OnClickListener {
	Button button;
	RadioGroup genderRadioGroup;
	EditText name;
	EditText age;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Get the ids of view objects
		findAllViewsId();
		button.setOnClickListener(this);
	}

	private void findAllViewsId() {
		button = (Button) findViewById(R.id.submit);
		name = (EditText) findViewById(R.id.name);
		age = (EditText) findViewById(R.id.age);
		genderRadioGroup = (RadioGroup) findViewById(R.id.gender);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getApplicationContext(),
				DisplayActivity.class);
		// Create a bundle object
		Bundle b = new Bundle();
		// Inserts a String value into the mapping of this Bundle
		b.putString("name", name.getText().toString());
		b.putString("age", age.getText().toString());
		int id = genderRadioGroup.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton) findViewById(id);
		b.putString("gender", radioButton.getText().toString());
		// Add the bundle to the intent.
		intent.putExtras(b);
		// start the DisplayActivity
		startActivity(intent);
	}
}
