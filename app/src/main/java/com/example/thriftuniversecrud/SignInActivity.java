package com.example.thriftuniversecrud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private TextInputLayout SignInMail, SignInPass;
    private FirebaseAuth auth;
    private Button SignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get Firebase Auth instance
        auth = FirebaseAuth.getInstance();
        //set the view now
        setContentView(R.layout.activity_sign_in);
        SignInMail = findViewById(R.id.SignInMail);
        SignInPass = findViewById(R.id.SignInPass);
        SignInButton =(Button) findViewById(R.id.SignInButton);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = SignInMail.getEditText().getText().toString();
                final String password = SignInPass.getEditText().getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your mail address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!validateEmail() | !validatePassword()) {
                    //authenticate user
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 8) {
                                            Toast.makeText(getApplicationContext(), "Password must be more than 8 digit", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
    private Boolean validateEmail(){
        String val = SignInMail.getEditText().getText().toString();
        Boolean boolVal = false;

        if(val.isEmpty()){
            SignInMail.setError("Field cannot be empty!");
        }else{
            SignInMail.setError(null);
            SignInMail.setErrorEnabled(false);
            boolVal = true;
        }

        return boolVal;
    }

        private Boolean validatePassword() {
            String val = SignInPass.getEditText().getText().toString();
            String passwordVal = "^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$";

            if (val.isEmpty()){
                SignInPass.setError("Field cannot be empty");
                return false;
            } else if(!val.matches(passwordVal)) {
                SignInPass.setError("Password too weak");
                return false;
            }
            else {
                SignInPass.setError(null);
                return true;
            }
        }

    public void ToSignUp(View v) {
        Intent inent = new Intent(this, SignUpActivity.class);
        startActivity(inent);
    }
    public void ToForgetPass(View v) {
        Intent inent = new Intent(this, ResetPasswordActivity.class);
        startActivity(inent);
    }
}