package com.ss.cryptobox;

import android.net.Uri;

public class Constants {
    public static final int ADD_ELEMENT_TO_SCROLL = 1;
    public static final int VALIDATE_SECRET_VIEW = 2;
    public static final String EC_KEY_ALIAS = "PrimaryPKEAlias";

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "secrets.db";
    public static final String TABLE_NAME = "secrets";
    public static final String _ID = "_id";

    public static final String SECRET_NAME = "name";
    public static final String SECRET_USRNAME = "user";
    public static final String SECRET_PASSWD = "passwd";

    public static final String CONTENT_AUTHORITY = "com.ss.cryptobox";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final int EXIT_SLEEP_DURATION = 5000; // 5 seconds
}
