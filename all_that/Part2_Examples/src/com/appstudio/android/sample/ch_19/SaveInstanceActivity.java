package com.appstudio.android.sample.ch_19;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SaveInstanceActivity extends Activity {

	private EditText editeText;
	private String mapKey = "mapKey";
	private String bundleKey = "bundleKey";
	private TextView tv_saveInstance = null;
	private TextView tv_status_view = null;
	private static String statusStr = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_instance_activity);
		editeText = (EditText) findViewById(R.id.editText1);

		tv_saveInstance = (TextView) findViewById(R.id.saved_instance);
		tv_status_view = (TextView) findViewById(R.id.status_view);
		tv_status_view.setText(getString(R.string.on_create));
		statusStr = statusStr + "\n" + tv_status_view.getText().toString();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		tv_status_view.setText(statusStr + "\n"
				+ getString(R.string.on_save_instance_state));
		statusStr = tv_status_view.getText().toString();

		if (editeText != null) {
			Bundle map = new Bundle();
			map.putString(mapKey, editeText.getText().toString());
			outState.putBundle(bundleKey, map);
		}

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		tv_status_view.setText(statusStr + "\n"
				+ getString(R.string.on_restore_instance_state));
		statusStr = tv_status_view.getText().toString();

		if (savedInstanceState != null) {
			Bundle map = savedInstanceState.getBundle(bundleKey);

			if (map != null) {
				editeText.setText(map.getString(mapKey));
				tv_saveInstance.setText(editeText.getText().toString());
			}
		}
	}

}
