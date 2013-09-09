package kr.co.company.ratingbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RatingBar ratingbar = (RatingBar) 
			findViewById(R.id.ratingbar);
        ratingbar.setOnRatingBarChangeListener(new 
			OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float 
				rating, boolean fromUser) 
            {
                Toast.makeText(getApplicationContext(), "New Rating: " + 
					rating, Toast.LENGTH_SHORT).show();
            }
        });

    }
}