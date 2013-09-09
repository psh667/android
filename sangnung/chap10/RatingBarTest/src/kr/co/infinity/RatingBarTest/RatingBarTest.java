package kr.co.infinity.RatingBarTest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

public class RatingBarTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final RatingBar ratingbar = (RatingBar) findViewById(R.id.ratingbar);
		ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				Toast.makeText(RatingBarTest.this, "Á¡¼ö: " + rating,
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}