package com.example.androidsmartcitytraveller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    Button signu,login;
    EditText username,password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    Button learn;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        signu=findViewById(R.id.signup2);
        login=findViewById(R.id.login);
        learn=findViewById(R.id.B_learn);
        signu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(in);
            }
        });
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,LearnMore.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().trim().equalsIgnoreCase(""))
                {
                    username.setError("This field can't be empty");
                }

                if(password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError("This field can't be empty");
                }
                if((!username.getText().toString().trim().equalsIgnoreCase(""))&&(!password.getText().toString().trim().equalsIgnoreCase("")))
                {
                    onLogin(view);
                }

            }
        });
        mAuth=FirebaseAuth.getInstance();

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(LoginActivity.this,TravelActivity.class));
                }
            }
        };

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(true)
                .setNegativeButton("Cancel",null);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void onLogin(View view) {
        String email=username.getText().toString();
        String pass=password.getText().toString();
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentuser=mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Signed in with "+currentuser.getEmail(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Kindly SignUp", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}

