package com.msi.manning.chapter5.widget;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Widget implements BaseColumns {

    public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
    public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
    public static final String MIME_ITEM = "vnd.msi.widget";
    public static final String MIME_TYPE_SINGLE = Widget.MIME_ITEM_PREFIX + "/" + Widget.MIME_ITEM;
    public static final String MIME_TYPE_MULTIPLE = Widget.MIME_DIR_PREFIX + "/" + Widget.MIME_ITEM;

    public static final String AUTHORITY = "com.msi.manning.chapter5.Widget";
    public static final String PATH_SINGLE = "widgets/#";
    public static final String PATH_MULTIPLE = "widgets";
    public static final Uri CONTENT_URI = Uri.parse("content://" + Widget.AUTHORITY + "/" + Widget.PATH_MULTIPLE);

    public static final String DEFAULT_SORT_ORDER = "updated DESC";

    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String CATEGORY = "category";
    public static final String CREATED = "created";
    public static final String UPDATED = "updated";
}
