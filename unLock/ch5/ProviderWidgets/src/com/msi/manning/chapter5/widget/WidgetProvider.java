package com.msi.manning.chapter5.widget;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public class WidgetProvider extends ContentProvider {

    private static final String CLASSNAME = WidgetProvider.class.getSimpleName();
    private static final int WIDGETS = 1;
    private static final int WIDGET = 2;

    public static final String DB_NAME = "widgets_db";
    public static final String DB_TABLE = "widget";
    public static final int DB_VERSION = 1;

    private static UriMatcher URI_MATCHER = null;
    private static HashMap<String, String> PROJECTION_MAP;

    private SQLiteDatabase db;

    static {
        WidgetProvider.URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        WidgetProvider.URI_MATCHER.addURI(Widget.AUTHORITY, Widget.PATH_MULTIPLE, WidgetProvider.WIDGETS);
        WidgetProvider.URI_MATCHER.addURI(Widget.AUTHORITY, Widget.PATH_SINGLE, WidgetProvider.WIDGET);

        WidgetProvider.PROJECTION_MAP = new HashMap<String, String>();
        WidgetProvider.PROJECTION_MAP.put(BaseColumns._ID, "_id");
        WidgetProvider.PROJECTION_MAP.put(Widget.NAME, "name");
        WidgetProvider.PROJECTION_MAP.put(Widget.TYPE, "type");
        WidgetProvider.PROJECTION_MAP.put(Widget.CATEGORY, "category");
        WidgetProvider.PROJECTION_MAP.put(Widget.CREATED, "created");
        WidgetProvider.PROJECTION_MAP.put(Widget.UPDATED, "updated");
    }

    private static class DBOpenHelper extends SQLiteOpenHelper {

        private static final String DB_CREATE = "CREATE TABLE "
            + WidgetProvider.DB_TABLE
            + " (_id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, type TEXT, category TEXT, updated INTEGER, created INTEGER);";

        // private static final String DB_UPDATE = "";

        public DBOpenHelper(final Context context) {
            super(context, WidgetProvider.DB_NAME, null, WidgetProvider.DB_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            Log.v(Constants.LOGTAG, WidgetProvider.CLASSNAME + " OpenHelper Creating database");
            try {
                db.execSQL(DBOpenHelper.DB_CREATE);
            } catch (SQLException e) {
                Log.e(Constants.LOGTAG, WidgetProvider.CLASSNAME, e);
            }
        }

        @Override
        public void onOpen(final SQLiteDatabase db) {
            super.onOpen(db);
            Log.v(Constants.LOGTAG, WidgetProvider.CLASSNAME + " OpenHelper Opening database");
        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            Log.w(Constants.LOGTAG, WidgetProvider.CLASSNAME + " OpenHelper Upgrading database from version "
                + oldVersion + " to " + newVersion + " all data will be clobbered");
            db.execSQL("DROP TABLE IF EXISTS " + WidgetProvider.DB_TABLE);
            this.onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        DBOpenHelper dbHelper = new DBOpenHelper(getContext());
        this.db = dbHelper.getWritableDatabase();

        if (this.db == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Per Android JavaDoc:
     * 
     * Return the MIME type of the data at the given URI. This should start with
     * vnd.android.cursor.item/ for a single record, or vnd.android.cursor.dir/ for multiple items.
     * 
     * (Though this is NOT how the samples provided by Google work.)
     * 
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
    @Override
    public String getType(final Uri uri) {
        switch (WidgetProvider.URI_MATCHER.match(uri)) {
            case WIDGETS:
                return Widget.MIME_TYPE_MULTIPLE;
            case WIDGET:
                return Widget.MIME_TYPE_SINGLE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs,
        final String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String orderBy = null;

        switch (WidgetProvider.URI_MATCHER.match(uri)) {
            case WIDGETS:
                queryBuilder.setTables(WidgetProvider.DB_TABLE);
                queryBuilder.setProjectionMap(WidgetProvider.PROJECTION_MAP);
                break;
            case WIDGET:
                queryBuilder.setTables(WidgetProvider.DB_TABLE);
                queryBuilder.appendWhere("_id=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Widget.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues initialValues) {
        long rowId = 0L;
        ContentValues values = null;

        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        if (WidgetProvider.URI_MATCHER.match(uri) != WidgetProvider.WIDGETS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Long now = System.currentTimeMillis();

        // default fields if not set
        if (!values.containsKey(Widget.NAME)) {
            values.put(Widget.NAME, "NA");
        }
        if (!values.containsKey(Widget.TYPE)) {
            values.put(Widget.NAME, "NA");
        }
        if (!values.containsKey(Widget.CATEGORY)) {
            values.put(Widget.NAME, "NA");
        }
        if (!values.containsKey(Widget.CREATED)) {
            values.put(Widget.NAME, now);
        }
        if (!values.containsKey(Widget.UPDATED)) {
            values.put(Widget.UPDATED, 0);
        }

        rowId = this.db.insert(WidgetProvider.DB_TABLE, "widget_hack", values);

        if (rowId > 0) {
            Uri result = ContentUris.withAppendedId(Widget.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(result, null);
            return result;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        int count = 0;
        switch (WidgetProvider.URI_MATCHER.match(uri)) {
            case WIDGETS:
                count = this.db.update(WidgetProvider.DB_TABLE, values, selection, selectionArgs);
                break;
            case WIDGET:
                String segment = uri.getPathSegments().get(1);
                String where = "";
                if (!TextUtils.isEmpty(selection)) {
                    where = " AND (" + selection + ")";
                }
                count = this.db.update(WidgetProvider.DB_TABLE, values, "_id=" + segment + where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        int count;

        switch (WidgetProvider.URI_MATCHER.match(uri)) {
            case WIDGETS:
                count = this.db.delete(WidgetProvider.DB_TABLE, selection, selectionArgs);
                break;
            case WIDGET:
                String segment = uri.getPathSegments().get(1);
                String where = "";
                if (!TextUtils.isEmpty(selection)) {
                    where = " AND (" + selection + ")";
                }
                count = this.db.delete(WidgetProvider.DB_TABLE, "_id=" + segment + where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
