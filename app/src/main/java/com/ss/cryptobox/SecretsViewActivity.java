package com.ss.cryptobox;

import android.content.Context;
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
    private final String TAG = "SecretsViewActivity";
    private boolean bGoneBackground = false;
    private AuthenticationManager authenticationManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int message_id = bundle.getInt("message_id");
        switch (message_id) {
            case Constants.VALIDATE_SECRET_VIEW:
                break;
        }

        authenticationManager = new AuthenticationManager();
        authenticationManager.Initialise(this, new SecretsViewActivityAuthListenerImpl(this));

        DisplaySecretsView();
    }

    @Override
    protected void onNewIntent (Intent intent) {
        Log.i(TAG, "onNewIntent");

        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        int message_id = bundle.getInt("message_id");
        switch (message_id) {
            case Constants.ADD_ELEMENT_TO_SCROLL:
                LinearLayout parent = findViewById(R.id.scroll_parent);
                if(parent != null) {
                    View view = LayoutInflater.from(this).inflate(R.layout.scroll_item_view, parent, false);
                    if(view != null)
                        parent.addView(view);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "OnPause");
        super.onPause();
        DisplayGreyScreen();
        bGoneBackground = true;
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "OnResume");
        super.onResume();

        if(bGoneBackground)
        {
            authenticationManager.requestAuthentication();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private class SecretsViewActivityAuthListenerImpl implements IAuthListener {

        private Context context = null;

        SecretsViewActivityAuthListenerImpl(Context context) {
            this.context = context;
        }

        @Override
        public void onAuthenticationSuccess() {
            bGoneBackground = false;
            DisplaySecretsView();
        }

        @Override
        public void onAuthenticationFailure() {

        }
    }

    private void DisplayGreyScreen() {
        setContentView(R.layout.grey_window);
    }

    private void DisplaySecretsView() {
        setContentView(R.layout.secrets_view);
        ImageButton addSecret = findViewById(R.id.add_secret);
        addSecret.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SecretsDialogActivity.class));
        });
    }
}