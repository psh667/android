package org.androidtown.sns.faceapp;

import java.text.SimpleDateFormat;

import android.graphics.Bitmap;

import com.facebook.android.Facebook;

public class BasicInfo {

	public static final int REQ_CODE_FACEBOOK_LOGIN = 1001;

	public static boolean FacebookLogin = false;
	public static boolean RetryLogin = false;

	public static Facebook FacebookInstance = null;

	public static String[] FACEBOOK_PERMISSIONS = {"publish_stream", "read_stream", "user_photos", "email"};

	public static String FACEBOOK_ACCESS_TOKEN = "";
	public static String FACEBOOK_APP_ID = "221338014552043";
	public static String FACEBOOK_API_KEY = "224a8cee1a78a9fbb040bdad7153674a";
	public static String FACEBOOK_APP_SECRET = "b7fb049c5f64f2db51b34f6206376220";

	public static String FACEBOOK_NAME = "";

	public static SimpleDateFormat OrigDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
	public static SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");

	public static Bitmap BasicPicture = null;
}
