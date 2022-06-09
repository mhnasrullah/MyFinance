package com.example.pampa;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LandingActivity extends AppCompatActivity {
    Button btn_start;
    FirebaseAuth mAuth;
    GoogleSignInClient mSignClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signWGoogle();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclientid))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();

        mSignClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
// Check if user is signed in (non-null) and update UIaccordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LandingActivity.this,
                    MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LandingActivity.this,"Log In First",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void signWGoogle(){
        Intent intent = mSignClient.getSignInIntent();
//        (intent,1);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("DGOOGLE", "ACTIVITYRAEDY");

        if(requestCode == 0){
            Task<GoogleSignInAccount> usrdata = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acclog = usrdata.getResult(ApiException.class);
                handlesignr(acclog);
            } catch (ApiException e) {
                Log.d("DGOOGLE", "ERROR");
                e.printStackTrace();
            }
        }
    }

    public void handlesignr(GoogleSignInAccount acc){
        AuthCredential cre = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        mAuth.signInWithCredential(cre).addOnSuccessListener(LandingActivity.this, authr -> {
            FirebaseUser afu = mAuth.getCurrentUser();
            updateUI(afu);
        });
    }
}

