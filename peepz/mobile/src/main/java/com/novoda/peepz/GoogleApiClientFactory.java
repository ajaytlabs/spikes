package com.novoda.peepz;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

class GoogleApiClientFactory {

    private final String webClientId;
    private final FragmentActivity activity;

    public GoogleApiClientFactory(String webClientId, FragmentActivity activity) {
        this.webClientId = webClientId;
        this.activity = activity;
    }

    public GoogleApiClient createGoogleApiClient(final AuthenticationCallbacks authenticationCallbacks) {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();

        return new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        authenticationCallbacks.onAuthenticationFailure();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }
}
