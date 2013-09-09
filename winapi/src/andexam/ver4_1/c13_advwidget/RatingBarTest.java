package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

public class RatingBarTest extends Activity {
	RatingBar mRating;
	TextView mRateText;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ratingbartest);

		mRating = (RatingBar)findViewById(R.id.ratingbar);
		mRateText = (TextView)findViewById(R.id.ratetext);

		mRating.setOnRatingBarChangeListener(new 
				RatingBar.OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating, 
					boolean fromUser) {
				mRateText.setText("Now Rate : " + rating);
			}
		});
	}
}