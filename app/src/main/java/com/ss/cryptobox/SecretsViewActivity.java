package com.ss.cryptobox;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SecretsViewActivity extends AppCompatActivity {
    private final String TAG = "SecretsViewActivity";
    private boolean bGoneBackground = false;
    private AuthenticationManager authenticationManager = null;
    private AppKeyStore appKeyStore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int message_id = bundle.getInt("message_id");
        switch (message_id) {
            case Constants.VALIDATE_SECRET_VIEW:
                break;
        }

        appKeyStore = new AppKeyStore();
        authenticationManager = new AuthenticationManager();
        authenticationManager.Initialise(this, new SecretsViewActivityAuthListenerImpl(this));
        DisplaySecretsView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.add_new:
                startActivity(new Intent(getApplicationContext(), SecretsDialogActivity.class));
                return true;
            case R.id.settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onNewIntent (Intent intent) {
        Log.i(TAG, "onNewIntent");

        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
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
            authenticationManager.RequestAuthentication(this);
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        ListView listView = findViewById(R.id.scroller);
        Cursor cursor = getContentResolver().query(Constants.BASE_CONTENT_URI, null, null, null, null);

        listView.setAdapter(new SecretListAdapter(this, cursor));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor selection = getContentResolver().query(Constants.BASE_CONTENT_URI, null, null, null, null);
                selection.move(position + 1);
                Intent intent = new Intent(getApplicationContext(), ClearDialogActivity.class);
                String name = appKeyStore.Decrypt(new Pair<byte[], byte[]>(selection.getBlob(selection.getColumnIndex(Constants.SECRET_NAME_IV)),selection.getBlob(selection.getColumnIndex(Constants.SECRET_NAME))));
                String user = appKeyStore.Decrypt(new Pair<byte[], byte[]>(selection.getBlob(selection.getColumnIndex(Constants.SECRET_USRNAME_IV)),selection.getBlob(selection.getColumnIndex(Constants.SECRET_USRNAME))));
                String pass = appKeyStore.Decrypt(new Pair<byte[], byte[]>(selection.getBlob(selection.getColumnIndex(Constants.SECRET_PASSWD_IV)),selection.getBlob(selection.getColumnIndex(Constants.SECRET_PASSWD))));
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("user", user);
                bundle.putString("pass", pass);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private class SecretListAdapter extends CursorAdapter {

        public SecretListAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.scroll_item_view, parent, false);
        }


        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView textView = view.findViewById(R.id.secret_name_text);
            String name = appKeyStore.Decrypt(new Pair<>(cursor.getBlob(cursor.getColumnIndex(Constants.SECRET_NAME_IV)),cursor.getBlob(cursor.getColumnIndex(Constants.SECRET_NAME))));
            textView.setText(name);
        }

    }
}