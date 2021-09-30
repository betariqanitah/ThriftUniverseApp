package com.example.thriftuniversecrud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout SignUpMail, SignUpPass, SignUpName;
    Button SignUpButton;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    DatabaseReference ref;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUpName = findViewById(R.id.SignUpName);
        SignUpMail = findViewById(R.id.SignUpMail);
        SignUpPass = findViewById(R.id.SignUpPass);
        auth= FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance();
        ref = db.getReference();
        SignUpButton = (Button) findViewById(R.id.SignUpButton);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = SignUpName.getEditText().getText().toString();
                String email = SignUpMail.getEditText().getText().toString();
                String pass = SignUpPass.getEditText().getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter your E-mail address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
                }
                if (pass.length() == 0){
                    Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
                }
                if (pass.length()<8){
                    Toast.makeText(getApplicationContext(),"Password must be more than 8 digit",Toast.LENGTH_LONG).show();
                }
                else{
                    auth.createUserWithEmailAndPassword(email,pass) .addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            UserInformation userInformation = new UserInformation();
                            userInformation.setUserFullName(name);
//                            userInformation.setUserPhone("Phone");
//                            userInformation.setUserAddress("address");
//                            userInformation.setUserPostalCode("postal code");
                            ref.child(FirebaseAuth.getInstance().getUid())
                                    .setValue(userInformation)
                                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(SignUpActivity.this, "ERROR",Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                startActivity(new Intent(SignUpActivity.this, EditProfileActivity.class));
                                                finish();
                                            }
                                        }
                                    });
                        }
                    });
//                    auth.createUserWithEmailAndPassword(email,pass) .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (!task.isSuccessful()) {
//                                        Toast.makeText(SignUpActivity.this, "ERROR",Toast.LENGTH_LONG).show();
//                                    }
//                                    else {
//                                        startActivity(new Intent(SignUpActivity.this, EditProfileActivity.class));
//                                        finish();
//                                    }
//                                }
//                            });
                }
            }
        });
    }
    public void ToSignIn(View v){
        Intent  intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}