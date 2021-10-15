package com.ss.cryptobox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.savedstate.SavedStateRegistryOwner;

public class SecretsViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String s = b.getString("secrets");
        if(s.equals("message"))
            setContentView(R.layout.secrets_view);

        Button biometricLoginButton = findViewById(R.id.add_item);
        biometricLoginButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SecretsDialogActivity.class));
//            DialogFragment newFragment = new AddSecretDataFragment();
//            newFragment.show(getSupportFragmentManager(), "user_pass");
        });
    }
}