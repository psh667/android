package org.androidtown.ui.pager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager sample project
 *
 * @author Mike
 */
public class SampleViewPagerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // get a ViewPager reference and set adapter for it
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        pager.setAdapter(adapter);

    }

    /**
     * Adapter for the ViewPager
     */
    public class ViewPagerAdapter extends PagerAdapter {
    	// sample names
		private String[] names = { "John", "Mike", "Sean" };
		// sample image resource ids
		private int[] resIds = {R.drawable.dream01, R.drawable.dream02, R.drawable.dream03};
		// sample call numbers
		private String[] callNumbers = {"010-7777-1234", "010-7788-1234", "010-7799-1234"};

		/**
		 * Context
		 */
		private Context mContext;

		/**
		 * Initialize
		 *
		 * @param context
		 */
		public ViewPagerAdapter( Context context ) {
			mContext = context;
		}

		/**
		 * Count of pages
		 */
		public int getCount() {
			return names.length;
		}

		/**
		 * Called before a page is created
		 */
		public Object instantiateItem(View pager, int position) {
			// create a instance of the page and set data
			PersonPage page = new PersonPage(mContext);
			page.setNameText(names[position]);
			page.setImage(resIds[position]);
			page.setCallNumber(callNumbers[position]);

			// add to the ViewPager
			ViewPager curPager = (ViewPager) pager;
			curPager.addView(page, position);

			return page;
		}

		/**
		 * Called to remove the page
		 */
		public void destroyItem(View pager, int position, Object view) {
			((ViewPager)pager).removeView((PersonPage)view);
		}

		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		public void finishUpdate( View view ) {

		}

		public void restoreState( Parcelable p, ClassLoader c ) {

		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate( View view ) {

		}

	}


}