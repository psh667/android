package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class MultiPaneOrient extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multipaneorient);
	}

	public static class PlanetListFragment extends ListFragment {
		boolean mMiltiPane;
		int mLastIndex = 0;

		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_activated_1, PlanetInfo.PLANET));
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

			if (savedInstanceState != null) {
				mLastIndex = savedInstanceState.getInt("lastindex");
			}

			View descPanel = getActivity().findViewById(R.id.descpanel);
			if (descPanel != null && descPanel.getVisibility() == View.VISIBLE) {
				mMiltiPane = true;
				onListItemClick(getListView(), null, mLastIndex, 0);
			}
		}

		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt("lastindex", mLastIndex);
		}

		public void onListItemClick(ListView l, View v, int position, long id) {
			mLastIndex = position;
			getListView().setItemChecked(position, true);

			if (mMiltiPane) {
				FragmentManager fm = getFragmentManager();
				DescFragment df = (DescFragment)fm.findFragmentById(R.id.descpanel);
				if (df == null || df.mNowIndex != position) {
					df = DescFragment.newInstance(position);
					FragmentTransaction tr = fm.beginTransaction();
					tr.replace(R.id.descpanel, df);
					tr.commit();
				}
			} else {
				Intent intent = new Intent(getActivity(), DescActivity.class);
				intent.putExtra("index", position);
				startActivity(intent);
			}
		}
	}

	// 설명을 보여주는 프래그먼트
	public static class DescFragment extends Fragment {
		int mNowIndex;

		public static DescFragment newInstance(int index) {
			DescFragment df = new DescFragment();
			df.mNowIndex = index;
			return df; 
		}

		// 텍스트 뷰를 생성하고 아규먼트로 전달받은 단어의 설명을 보여 준다.
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.descfragment, container, false);
			TextView text = (TextView)root.findViewById(R.id.txtdescription);
			text.setText(PlanetInfo.DESC[mNowIndex]);
			return root;
		}
	}

	// 설명을 보여주는 액티비티
	public static class DescActivity extends Activity {
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// 세로 모드에서 가로로 전환하면 즉시 종료한다.
			if (getResources().getConfiguration().orientation
					== Configuration.ORIENTATION_LANDSCAPE) {
				finish();
				return;
			}

			// 인텐트로 전달된 첨자의 단어를 보여준다.
			int index = getIntent().getExtras().getInt("index");
			DescFragment details = DescFragment.newInstance(index);
			getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
		}
	}
}
