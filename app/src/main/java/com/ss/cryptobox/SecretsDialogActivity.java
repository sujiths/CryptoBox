package com.ss.cryptobox;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecretsDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_pass);

        Button savebutton = findViewById(R.id.save);
        savebutton.setOnClickListener(view -> {
            Intent gotoSecretsIntent = new Intent(getApplicationContext(), SecretsViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("message_id", Constants.ADD_ELEMENT_TO_SCROLL);
            bundle.putString("token", "my_token");
            gotoSecretsIntent.putExtras(bundle);
            startActivity(gotoSecretsIntent);
        });


    }
}