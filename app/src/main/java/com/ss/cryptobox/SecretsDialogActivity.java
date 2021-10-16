package com.ss.cryptobox;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecretsDialogActivity extends AppCompatActivity {
    private final String TAG = "SecretsDialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_pass);

        Button save_button = findViewById(R.id.save);
        save_button.setOnClickListener(view -> {

            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.SECRET_USRNAME, ((EditText) findViewById(R.id.username)).getText().toString());
            contentValues.put(Constants.SECRET_PASSWD, ((EditText) findViewById(R.id.password)).getText().toString());
            contentValues.put(Constants.SECRET_NAME, ((EditText) findViewById(R.id.secret_name)).getText().toString());
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