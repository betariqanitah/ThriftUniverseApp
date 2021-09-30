package com.example.thriftuniversecrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thriftuniversecrud.Model.Contact;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    Button contactBtn;
    TextInputLayout contName, contEmail, contSubject, contMessage;
    FirebaseDatabase rootNode;

    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("messages");

        contName = findViewById(R.id.contact_name);
        contEmail = findViewById(R.id.contact_email);
        contSubject = findViewById(R.id.contact_subject);
        contMessage = findViewById(R.id.contact_message);
        contactBtn = findViewById(R.id.contact_btn);

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = contName.getEditText().getText().toString();
                String email = contEmail.getEditText().getText().toString();
                String subject = contSubject.getEditText().getText().toString();
                String message = contMessage.getEditText().getText().toString();


                if(!validateName() | !validateEmail() | !validateSubject() | !validateMessage()){
                    Toast.makeText(ContactUs.this, "Error: message not inserted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ContactUs.this, "Success: message inserted",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ContactUs.this, HomeActivity.class));
                }
                reference = FirebaseDatabase.getInstance().getReference("messages");

                Contact helper = new Contact(name,email,subject,message);
//                FirebaseAuth user = FirebaseAuth.getInstance();
                reference.child(email).setValue(helper);
            }
        });
    }

    private Boolean validateName() {
        String val = contName.getEditText().getText().toString();

        if (val.isEmpty()){
            contName.setError("Field cannot be empty");
            return false;
        }
        else {
            contName.setError(null);
            contName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = contEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (val.isEmpty()){
            contEmail.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)) {
            contEmail.setError("Invalid email address");
            return false;
        }
        else {
            contEmail.setError(null);
            return true;
        }
    }
    private Boolean validateSubject() {
        String val = contSubject.getEditText().getText().toString();

        if (val.isEmpty()){
            contSubject.setError("Field cannot be empty");
            return false;
        }
        else {
            contSubject.setError(null);
            contSubject.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateMessage() {
        String val = contMessage.getEditText().getText().toString();

        if (val.isEmpty()){
            contMessage.setError("Field cannot be empty");
            return false;
        } else if (val.length() <= 15) {
            contMessage.setError("Message too short");
            return false;
        }
        else {
            contMessage.setError(null);
            contMessage.setErrorEnabled(false);
            return true;
        }
    }
}