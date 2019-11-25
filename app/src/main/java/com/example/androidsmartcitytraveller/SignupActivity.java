package com.example.androidsmartcitytraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,pass;
    Button signup;

    @Override
    protected void onStart() {
        super.onStart();
        //check if user is is signed(not null)
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null) {
            Toast.makeText(this, "Already Signed in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_signup2);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        signup=(Button)findViewById(R.id.button2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equalsIgnoreCase(""))
                {
                    email.setError("This field can't be Empty");
                }
                if(pass.getText().toString().trim().equalsIgnoreCase("")){
                    pass.setError("This field can't be Empty");
                }
                if((!email.getText().toString().trim().equalsIgnoreCase(""))&&(!pass.getText().toString().trim().equalsIgnoreCase("")))
                {
                    createAccount(view);
                }

            }
        });
        mAuth=FirebaseAuth.getInstance();

    }

    private void createAccount(View view) {
        String Email=email.getText().toString();
        String Password=pass.getText().toString();

        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "Succesfully Logged in ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user=mAuth.getCurrentUser();
                        }
                        else{

                            Toast.makeText(SignupActivity.this, "Authentication failed."+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}
