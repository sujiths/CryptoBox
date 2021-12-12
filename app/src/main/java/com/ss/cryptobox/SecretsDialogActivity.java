package com.ss.cryptobox;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecretsDialogActivity extends AppCompatActivity {
    private final String TAG = "SecretsDialogActivity";
    private AppKeyStore appKeyStore = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_pass);

        Button save_button = findViewById(R.id.save);
        save_button.setOnClickListener(view -> {
            appKeyStore = new AppKeyStore();

            ContentValues contentValues = new ContentValues();
            Pair<byte[], byte[]> encPair = appKeyStore.Encrypt(((EditText) findViewById(R.id.username)).getText().toString());

            contentValues.put(Constants.SECRET_USRNAME_IV, encPair.first);
            contentValues.put(Constants.SECRET_USRNAME, encPair.second);
            encPair = appKeyStore.Encrypt(((EditText) findViewById(R.id.password)).getText().toString());

            contentValues.put(Constants.SECRET_PASSWD_IV, encPair.first);
            contentValues.put(Constants.SECRET_PASSWD, encPair.second);
            encPair = appKeyStore.Encrypt(((EditText) findViewById(R.id.secret_name)).getText().toString());

            contentValues.put(Constants.SECRET_NAME_IV, encPair.first);
            contentValues.put(Constants.SECRET_NAME, encPair.second);
            if (getContentResolver().insert(Constants.BASE_CONTENT_URI, contentValues) == null) {
                Log.e(TAG, "Insertion failed");
                Toast.makeText(this, "Failed to save secret", Toast.LENGTH_SHORT).show();
            }
            Intent gotoSecretsIntent = new Intent(getApplicationContext(), SecretsViewActivity.class);
            Bundle bundle = new Bundle();
            gotoSecretsIntent.putExtras(bundle);
            startActivity(gotoSecretsIntent);
        });


    }
}