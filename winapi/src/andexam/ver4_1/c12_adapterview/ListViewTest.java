package andexam.ver4_1.c12_adapterview;

import java.util.*;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

public class ListViewTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewtest);

		//* 데이터 원본 준비
		ArrayList<String> arGeneral = new ArrayList<String>();
		arGeneral.add("김유신");
		arGeneral.add("이순신");
		arGeneral.add("강감찬");
		arGeneral.add("을지문덕");
		//*/
		
		/* 배열로 준비
		String[] arGeneral = {"김유신", "이순신", "강감찬", "을지문덕"};
		//*/

		// 어댑터 준비
		ArrayAdapter<String> Adapter;
		Adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, arGeneral);

		// 어댑터 연결
		ListView list = (ListView)findViewById(R.id.list);
		list.setAdapter(Adapter);
	}
}
