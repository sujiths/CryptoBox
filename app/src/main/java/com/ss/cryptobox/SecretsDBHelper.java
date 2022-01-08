package com.ss.cryptobox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class SecretsDBHelper extends SQLiteOpenHelper {
    private final String TAG="SecretsDBHelper";

    public SecretsDBHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        createSecretsTable(db);
    }

    private void createSecretsTable(SQLiteDatabase db){
        Log.i(TAG, "createSecretsTable");
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + " (" +
                        Constants._ID + " INTEGER PRIMARY KEY, " +
                        Constants.SECRET_NAME + " BLOB UNIQUE NOT NULL, " +
                        Constants.SECRET_NAME_IV + " BLOB NOT NULL, " +
                        Constants.SECRET_USRNAME + " BLOB NOT NULL, " +
                        Constants.SECRET_USRNAME_IV + " BLOB NOT NULL, " +
                        Constants.SECRET_PASSWD + " BLOB NOT NULL, " +
                        Constants.SECRET_PASSWD_IV + " BLOB NOT NULL" +
                        ");"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
