package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

public class AutoComplete extends Activity {
	String[] arWords = new String[] {
		"가구", "가로수", "가방", "가슴", "가치", "가훈", "나그네", "다리미", 
		"above", "about", "absolute", "access", "activity", "adjust" 
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autocomplete);

		ArrayAdapter<String> adWord = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, arWords);
		AutoCompleteTextView autoEdit = (AutoCompleteTextView)
			findViewById(R.id.autoedit);
		autoEdit.setAdapter(adWord);
	}
}
