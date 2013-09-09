package com.msi.manning.restaurant;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.msi.manning.restaurant.data.Review;
import com.msi.manning.restaurant.data.ReviewFetcher;

import java.util.List;

/**
 * "List" of reviews screen - show reviews that match Criteria user selected. Users ReviewFetcher
 * which makes a Google Base call via Rome.
 * 
 * @author charliecollins
 */
public class ReviewList extends ListActivity {

    private static final String CLASSTAG = ReviewList.class.getSimpleName();
    private static final int MENU_CHANGE_CRITERIA = Menu.FIRST + 1;
    private static final int MENU_GET_NEXT_PAGE = Menu.FIRST;
    private static final int NUM_RESULTS_PER_PAGE = 8;
    
    private TextView empty;    
    private ProgressDialog progressDialog;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviews;
    
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            Log.v(Constants.LOGTAG, " " + ReviewList.CLASSTAG + " worker thread done, setup ReviewAdapter");
            progressDialog.dismiss();
            if ((reviews == null) || (reviews.size() == 0)) {
                empty.setText("No Data");
            } else {
                reviewAdapter = new ReviewAdapter(ReviewList.this, reviews);
                setListAdapter(reviewAdapter);
            }
        }
    };   

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(Constants.LOGTAG, " " + ReviewList.CLASSTAG + " onCreate");

        // NOTE* This Activity MUST contain a ListView named "@android:id/list"
        // (or "list" in code) in order to be customized
        // http://code.google.com/android/reference/android/app/ListActivity.html
        this.setContentView(R.layout.review_list);

        this.empty = (TextView) findViewById(R.id.empty);

        // set list properties
        final ListView listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setEmptyView(this.empty);
    }   

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(Constants.LOGTAG, " " + ReviewList.CLASSTAG + " onResume");
        // get the current review criteria from the Application (global state placed there)
        RestaurantFinderApplication application = (RestaurantFinderApplication) getApplication();
        String criteriaCuisine = application.getReviewCriteriaCuisine();
        String criteriaLocation = application.getReviewCriteriaLocation();

        // get start from, an int, from extras
        int startFrom = getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1);

        loadReviews(criteriaLocation, criteriaCuisine, startFrom);
    }    
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, ReviewList.MENU_GET_NEXT_PAGE, 0, R.string.menu_get_next_page).setIcon(
            android.R.drawable.ic_menu_more);
        menu.add(0, ReviewList.MENU_CHANGE_CRITERIA, 0, R.string.menu_change_criteria).setIcon(
            android.R.drawable.ic_menu_edit);
        return true;
    }    

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case MENU_GET_NEXT_PAGE:
                // increment the startFrom value and call this Activity again
                intent = new Intent(Constants.INTENT_ACTION_VIEW_LIST);
                intent.putExtra(Constants.STARTFROM_EXTRA, getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1)
                    + ReviewList.NUM_RESULTS_PER_PAGE);
                startActivity(intent);
                return true;
            case MENU_CHANGE_CRITERIA:
                intent = new Intent(this, ReviewCriteria.class);
                startActivity(intent);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // set the current review to the Application (global state placed there)
        RestaurantFinderApplication application = (RestaurantFinderApplication) getApplication();
        application.setCurrentReview(this.reviews.get(position));

        // startFrom page is not stored in application, for example purposes it's a simple "extra"
        Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_DETAIL);
        intent.putExtra(Constants.STARTFROM_EXTRA, getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1));
        startActivity(intent);
    }    
    
    private void loadReviews(String location, String cuisine, int startFrom) {

        Log.v(Constants.LOGTAG, " " + ReviewList.CLASSTAG + " loadReviews");

        final ReviewFetcher rf = new ReviewFetcher(location, cuisine, "ALL", startFrom,
            ReviewList.NUM_RESULTS_PER_PAGE);

        this.progressDialog = ProgressDialog.show(this, " Working...", " Retrieving reviews", true, false);

        // get reviews in a separate thread for ProgressDialog/Handler
        // when complete send "empty" message to handler
        new Thread() {
            @Override
            public void run() {
                reviews = rf.getReviews();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}
