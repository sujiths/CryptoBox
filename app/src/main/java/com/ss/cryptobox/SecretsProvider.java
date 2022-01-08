package com.ss.cryptobox;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SecretsProvider extends ContentProvider {
    private final String TAG="SecretsProvider";
    private SecretsDBHelper secretsDBHelper;
    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate");
        secretsDBHelper = new SecretsDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i(TAG, "query");
        final SQLiteDatabase db = secretsDBHelper.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.query(
                Constants.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i(TAG, "insert");
        long _id;
        final SQLiteDatabase db = secretsDBHelper.getWritableDatabase();
        _id = db.insert(Constants.TABLE_NAME, null, values);
        if(_id > 0) {
            Uri _uri = ContentUris.withAppendedId(Constants.BASE_CONTENT_URI, _id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        else {
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = secretsDBHelper.getWritableDatabase();
        String table = Constants.TABLE_NAME;
        String whereClause = Constants._ID+"=?";
        String[] whereArgs = selectionArgs;
        Log.e(TAG, " "+whereClause+" "+whereArgs[0]);
        return db.delete(table, whereClause, whereArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
