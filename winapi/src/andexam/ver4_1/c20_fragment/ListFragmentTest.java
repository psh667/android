package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ListFragmentTest extends Activity {
	public static String[] WORDS = {
		"boy", "girl", "school", "hello", "go"
	};
	public static String[] DESC = {
		"소년. 남자(사내) 아이. 어머나, 맙소사(놀람, 기쁨, 아픔 등을 나타내는 소리)", 
		"소녀. 여자(계집) 아이. 여자친구.", 
		"학교(주로 초, 중, 고등학교). 교습소. 훈련소. 훈련시키다. 단련시키다. 교육하다.", 
		"안녕하세요. 여보세요. Say hello to : ~에게 안부를 전하다.", 
		"가다. 나아가다(move along). go for walk : 산책하러 가다."
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentManager fm = getFragmentManager(); 
		if (fm.findFragmentById(android.R.id.content) == null) {
			WordListFragment wordlist = new WordListFragment();
			fm.beginTransaction().add(android.R.id.content, wordlist).commit();
		}
	}

	public class WordListFragment extends ListFragment {
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, WORDS));
		}

		public void onListItemClick(ListView l, View v, int position, long id) {
			String toast = WORDS[position] + " : " + DESC[position];
			Toast.makeText(getActivity(), toast, 0).show();
		}
	}	
}
