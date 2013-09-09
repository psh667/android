package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.app.ActionBar.Tab;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class ActionTab extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actiontab);

		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (int i=0;i<3;i++) {
			ActionBar.Tab tab = ab.newTab();
			String Cap = "Tab" + (i + 1);
			tab.setText(Cap);
			TabFragment frag = TabFragment.newInstance(Cap);
			tab.setTabListener(new TabListener(frag));
			ab.addTab(tab);
		}

		if (savedInstanceState != null) {
			int seltab = savedInstanceState.getInt("seltab");
			ab.setSelectedNavigationItem(seltab);
		}

	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("seltab", getActionBar().getSelectedNavigationIndex());
	}

	private class TabListener implements ActionBar.TabListener {
		private Fragment mFragment;

		public TabListener(Fragment fragment) {
			mFragment = fragment;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.add(R.id.tabparent, mFragment, "tag");
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(mFragment);
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}    

	public static class TabFragment extends Fragment {
		public static TabFragment newInstance(String text) {
			TabFragment frag = new TabFragment();

			Bundle args = new Bundle();
			args.putString("text", text);
			frag.setArguments(args);

			return frag;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			String text = "";
			Bundle args = getArguments();
			if (args != null) {
				text = args.getString("text");
			}

			View linear = inflater.inflate(R.layout.actiontabfragment, container, false);
			TextView textview = (TextView)linear.findViewById(R.id.content);
			textview.setText(text);

			return linear;
		}
	}
}
