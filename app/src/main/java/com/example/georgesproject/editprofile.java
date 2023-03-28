package com.example.georgesproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editprofile extends AppCompatActivity {
    EditText editName, editEmail ;
    Button saveButton;
    String nameUser, emailUser, usernameUser ;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        reference = FirebaseDatabase.getInstance().getReference("users");
        editName = findViewById(R.id.old_name_edittext);
        editEmail = findViewById(R.id.old_email_edittext);
        saveButton = findViewById(R.id.saveButton);

        showData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isEmailChanged()) {
                    Toast.makeText(editprofile.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(editprofile.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isNameChanged(){
        if (!nameUser.equals(editName.getText().toString())){
            reference.child(usernameUser).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isEmailChanged(){
        if (!emailUser.equals(editName.getText().toString())){
            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else{
            return false;
        }
    }


    public void showData(){
        Intent intent = getIntent();
        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        editName.setText(nameUser);
        editEmail.setText(emailUser);
    }
}
