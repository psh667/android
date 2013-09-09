package andexam.ver4_1.c26_cp;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.os.*;
import android.widget.*;

public class ProductList extends Activity {
	ProductDBHelper mHelper;
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productlist);
		
		mHelper = new ProductDBHelper(this);
		Cursor cursor;
		SQLiteDatabase db = mHelper.getWritableDatabase();

		cursor = db.rawQuery("SELECT * FROM product", null);
		startManagingCursor(cursor);

		SimpleCursorAdapter Adapter = null;
		Adapter = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_2,
				cursor, new String[] { "name", "price" }, 
				new int[] { android.R.id.text1, android.R.id.text2});
		ListView list = (ListView)findViewById(R.id.list);
		list.setAdapter(Adapter);
	}
}

class ProductDBHelper extends SQLiteOpenHelper {
	public ProductDBHelper(Context context) {
		super(context, "Product.db", null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE product ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"name TEXT, price INTEGER);");
		db.execSQL("INSERT INTO product VALUES (null, '오징어 땅콩', 900);");
		db.execSQL("INSERT INTO product VALUES (null, '농심 포테이토 칩', 2000);");
		db.execSQL("INSERT INTO product VALUES (null, '로보트 태권 V', 1000);");
		db.execSQL("INSERT INTO product VALUES (null, '꼬마 자동차 붕붕', 1500);");
		db.execSQL("INSERT INTO product VALUES (null, '윈도우즈 API 정복', 32000);");
		db.execSQL("INSERT INTO product VALUES (null, '롯데 인벤스 아파트', 190000000);");
		db.execSQL("INSERT INTO product VALUES (null, '88 라이트', 1900);");
		db.execSQL("INSERT INTO product VALUES (null, '프라이드 1.6 CVVT 골드', 8900000);");
		db.execSQL("INSERT INTO product VALUES (null, '캐리비안 베이 입장권', 25000);");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS product");
		onCreate(db);
	}
}
