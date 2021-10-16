package com.ss.cryptobox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.savedstate.SavedStateRegistryOwner;

public class SecretsViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int message_id = bundle.getInt("message_id");
        switch (message_id) {
            case Constants.VALIDATE_SECRET_VIEW:
                setContentView(R.layout.secrets_view);
                break;
        }
        ImageButton addSecret = findViewById(R.id.add_secret);
        addSecret.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SecretsDialogActivity.class));
        });
    }

    @Override
    protected void onNewIntent (Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        int message_id = bundle.getInt("message_id");
        switch (message_id) {
            case Constants.ADD_ELEMENT_TO_SCROLL:
                LinearLayout parent = findViewById(R.id.scroll_parent);
                View view = LayoutInflater.from(this).inflate(R.layout.scroll_item_view, parent,false);
                parent.addView(view);
                break;
        }
    }

}