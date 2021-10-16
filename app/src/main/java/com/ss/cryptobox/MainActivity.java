package com.ss.cryptobox;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private AuthenticationManager authenticationManager = null;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticationManager = new AuthenticationManager();
        if (!authenticationManager.Initialise(this, new MainActivityAuthListenerImpl(this))) {
            Toast.makeText(this, "Biometric is not available or not enrolled", Toast.LENGTH_LONG).show();
            this.finish();
        }
        // Initialise KeyStore
        AppKeyStore appKeyStore = new AppKeyStore();

        Button biometricLoginButton = findViewById(R.id.login_button);
        biometricLoginButton.setOnClickListener(view -> {
            if (appKeyStore.IsInitialised()) {
                authenticationManager.RequestAuthentication(this);
            }
        });
    }

    private class MainActivityAuthListenerImpl implements IAuthListener {

        private Context context = null;

        MainActivityAuthListenerImpl(Context context) {
            this.context = context;
        }

        @Override
        public void onAuthenticationSuccess() {
            Intent gotoSecretsIntent = new Intent(context, SecretsViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("message_id", Constants.VALIDATE_SECRET_VIEW);
            bundle.putString("token", "my_token");
            gotoSecretsIntent.putExtras(bundle);
            startActivity(gotoSecretsIntent);
        }

        @Override
        public void onAuthenticationFailure() {

        }
    }
}