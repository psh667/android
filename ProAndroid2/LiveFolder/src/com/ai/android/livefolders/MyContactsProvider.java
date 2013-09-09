package com.ai.android.livefolders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.LiveFolders;
//import android.provider.Contacts.People; // 이 API는 2.0 버전부터 사양화됨
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

public class MyContactsProvider extends ContentProvider {

    public static final String AUTHORITY = "com.ai.livefolders.contacts";

    // 라이브 폴더 생성 메서드로 전달되는 Uri
    public static final Uri CONTACTS_URI = Uri.parse("content://" + 
            AUTHORITY + "/contacts" );

    // 이 URI를 구별
    private static final int TYPE_MY_URI = 0;
    private static final UriMatcher URI_MATCHER;
    static{
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH); 
        URI_MATCHER.addURI(AUTHORITY, "contacts", TYPE_MY_URI);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int bulkInsert(Uri arg0, ContentValues[] values) {
        return 0; // 삽입할 것이 없음
    }
    // 라이브 폴더에 필요한 열들
    // 다음은 라이브 폴더의 콘트랙트다.
    private static final String[] CURSOR_COLUMNS = new String[]
    {
        BaseColumns._ID,
        LiveFolders.NAME,
        LiveFolders.DESCRIPTION, 
        LiveFolders.INTENT,
        LiveFolders.ICON_PACKAGE, 
        LiveFolders.ICON_RESOURCE
    };

    // 행이 없을 경우 에러 메시지를 표시하는 대신에
    // 같은 라이브 폴더 열들이 존재함을 알림
    private static final String[] CURSOR_ERROR_COLUMNS = new String[]
    {
        BaseColumns._ID, 
        LiveFolders.NAME,
        LiveFolders.DESCRIPTION
    };


    // 에러 메시지 행
    private static final Object[] ERROR_MESSAGE_ROW =
    new Object[]
    {
        -1,                                 // ID
        "연락처가 없습니다",                  // 이름
        "연락처 데이터베이스를 확인하세요"    // 설명
    };

    // 사용할 에러 커서
    private static MatrixCursor sErrorCursor = new   
            MatrixCursor(CURSOR_ERROR_COLUMNS);
    static
    {
        sErrorCursor.addRow(ERROR_MESSAGE_ROW);
    }

    // 연락처 데이터베이스에서 가져올 열들
    private static final String[] CONTACTS_COLUMN_NAMES = new String[]
    {
        Data._ID,
        Data.DISPLAY_NAME, 
        Data.TIMES_CONTACTED,
        Data.STARRED
    };

    public Cursor query(Uri uri, String[] projection, String selection, 
            String[] selectionArgs, String sortOrder)
    {
        // URI를 알아내서 일치하지 않으면 에러를 반환
        int type = URI_MATCHER.match(uri);
        if(type == UriMatcher.NO_MATCH)
        {
            return sErrorCursor;
        }

        Log.i("ss", "질의가 호출되었습니다");

        try
        {
            MatrixCursor mc = loadNewData(this);
            mc.setNotificationUri(getContext().getContentResolver(),
                    Uri.parse("content://contacts/people/"));
            MyCursor wmc = new MyCursor(mc,this);
            return wmc;
        }
        catch (Throwable e)
        {
            return sErrorCursor;
        }
    }

    public static MatrixCursor loadNewData(ContentProvider cp)
    {
        MatrixCursor mc = new MatrixCursor(CURSOR_COLUMNS); 
        Cursor allContacts = null;
        try
        {
            allContacts = cp.getContext().getContentResolver().query(
            	Contacts.CONTENT_URI, 
                CONTACTS_COLUMN_NAMES,
                null, // 행 필터
                null,
                Contacts.DISPLAY_NAME); // 정렬 기준

            while(allContacts.moveToNext())
            {
                String timesContacted = "연락 횟수: "+allContacts.getInt(2);

                Object[] rowObject = new Object[]
                {
                    allContacts.getLong(0),                  // ID
                    allContacts.getString(1),                 // 이름
                    timesContacted,                        // 설명
                    Uri.parse("content://contacts/people/"
                            +allContacts.getLong(0)),        // 인텐트 URI
                    cp.getContext().getPackageName(),       // 패키지
                    R.drawable.icon                        // 아이콘
                };
                mc.addRow(rowObject);
            }
            return mc;
        }
        finally
        {
            allContacts.close();
        }
    }

    @Override
    public String getType(Uri uri)
    {
        // 이 래퍼 프로바이더를 대상으로 하는
        // 전달받은 URI의 마임 타입을 반환
        // 일반적 형태는 다음과 같다.
        // "vnd.android.cursor.dir/vnd.google.note"
        return Data.CONTENT_TYPE;
    }

    public Uri insert(Uri uri, ContentValues initialValues) {
        throw new UnsupportedOperationException(
                "이것은 래퍼에 불과하므로 삽입할 수 없습니다");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException(
                "이것은 래퍼에 불과하므로 삭제할 수 없습니다");
    }

    public int  update(Uri uri, ContentValues values, 
            String selection, String[] selectionArgs)
    {
        throw new UnsupportedOperationException(
            "이것은 래퍼에 불과하므로 업데이트할 수 없습니다");
    }
}