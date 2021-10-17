package com.ss.cryptobox;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ClearDialogActivity extends AppCompatActivity {
    private final String TAG = "ClearDialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_secrets);

        Button close_button = findViewById(R.id.close_dialogue);
        close_button.setOnClickListener(view -> {
            finish();
        });


    }
}