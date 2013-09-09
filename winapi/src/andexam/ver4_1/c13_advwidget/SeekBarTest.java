package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

public class SeekBarTest extends Activity {
	SeekBar mSeekBar;
	TextView mVolume;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seekbartest);

		mSeekBar = (SeekBar)findViewById(R.id.seekbar);
		mVolume = (TextView)findViewById(R.id.volume);

		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress, 
					boolean fromUser) {
				mVolume.setText("Now Volume : " + progress);
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
	}
}