package com.novoda.peepz;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

class FirebaseWrapper {

    private final FirebaseAuth firebaseAuth;

    FirebaseWrapper(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void getFirebaseUser(GoogleSignInAccount account, final AuthenticationCallbacks authenticationCallbacks) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth
                .signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            authenticationCallbacks.onSuccess(task.getResult().getUser());
                        } else {
                            authenticationCallbacks.onAuthenticationFailure();
                        }
                    }
                });
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public FirebaseUser getSignedInUser() {
        return firebaseAuth.getCurrentUser();
    }
}
