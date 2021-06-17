package com.basicknowledgehub.basicknowledgehub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends AppCompatActivity {

    //initialized variable
    SignInButton signInButton;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //assign variable
        signInButton = findViewById(R.id.btn_sign_in);

        //initialize sign in option
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("689986635132-1thn09bfu2gbds17hcl9erjhph8e70qm.apps.googleusercontent.com")
                .requestEmail().build();

        //initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(LoginPage.this, googleSignInOptions);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize sign in intent
                Intent intent = googleSignInClient.getSignInIntent();
                //start activity for result
                startActivityForResult(intent, 100);
            }
        });

        //initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();
        //initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //check condition
        if (firebaseUser != null){
            //when user already sign in
            //redirect to profile activity
            startActivity(new Intent(LoginPage.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check condition
        if (requestCode == 100) {
            //when request code is equal to 100
            //initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            //check condition
            if (signInAccountTask.isSuccessful()) {
                //when google sign in successful
                //initialize string
                String s = "Google sign in successful";
                //display toast
                displayToast(s);
                try {
                    //initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    //check condition
                    if (googleSignInAccount != null){
                        //when sign in account is not equal to null
                        //initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        //check credential
                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        //check condition
                                        if (task.isSuccessful()){
                                            //when task is successful
                                            //redirect to profile activity
                                            startActivity(new Intent(LoginPage.this, MainActivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                            //display toast
                                            displayToast("Firebase authentication successful");
                                        }
                                        else {
                                            //when task is successful
                                            //display toast
                                            displayToast("Authentication Failed:"+task.getException().getMessage());
                                        }
                                    }
                                });
                    }
                }
                catch(ApiException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void btn_skip(View view) {
        Intent intent =  new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}