package com.novoda.peepz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public abstract class BaseActivity extends AppCompatActivity {

    private FirebaseApi firebaseApi;
    private GoogleApiClientApi googleApiClientApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiClientApi = new GoogleApiClientApi(this);
        googleApiClientApi.setupGoogleApiClient();
        firebaseApi = new FirebaseApi(FirebaseAuth.getInstance(), googleApiClientApi);
    }

    protected FirebaseApi firebaseApi() {
        return firebaseApi;
    }

    protected GoogleApiClientApi googleApiClientApi() {
        return googleApiClientApi;
    }

    protected void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        log("toast: " + text);
    }

    protected void log(String text) {
        Log.d("!!!", text);
    }

}
