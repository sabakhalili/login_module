package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity {

    EditText name,password;
    TextView infoin;
    Button Signin;
    FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        name = findViewById(R.id.email);
        password = findViewById(R.id.password);
        infoin = findViewById(R.id.text);
        Signin = findViewById(R.id.button_signin);
        mFireBaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFireBaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(SecondActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SecondActivity.this, Home.class);
                    startActivity(i);
                } else {
                    Toast.makeText(SecondActivity.this, "Please login", Toast.LENGTH_SHORT).show();
                }

            }
        };
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = name.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    name.setError("Please enter email");
                    name.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter correct password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(SecondActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFireBaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(SecondActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SecondActivity.this, "Login error, Please login again", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(SecondActivity.this, Home.class));
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SecondActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        Signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(SecondActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFireBaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
