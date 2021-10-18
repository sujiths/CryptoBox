package com.ss.cryptobox;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ClearDialogActivity extends AppCompatActivity {
    private final String TAG = "ClearDialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.display_secrets);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView name = findViewById(R.id.display_name);
        name.setText(bundle.getString("name"));

        TextView user = findViewById(R.id.display_user);
        user.setText(bundle.getString("user"));

        TextView pass = findViewById(R.id.display_pass);
        pass.setText(bundle.getString("pass"));

        Button close_button = findViewById(R.id.close_dialogue);
        close_button.setOnClickListener(view -> {
            finish();
        });


    }
}