package com.example.georgesproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Optional;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static final DatabaseReference usersRef = db.getReference("users");
   // تعاريف
    EditText name, email, password;
    Button signup;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(this);
    }

    @Override
    //ازا تم الكبس على انشاء حساب
    public void onClick(View view) {
        if (signup == view){
            createAccount(email.getText().toString(), password.getText().toString());
        }
    }

    // لانشاء حساب جديد
    public void createAccount(String email, String password) {
        if (email != null && password != null) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(name.getText().toString(), email, password);

                                String currentUserUid = null;

                                if (mAuth.getCurrentUser() != null) {
                                    currentUserUid = mAuth.getCurrentUser().getUid();
                                } else {
                                    // رساله اذا غلط
                                    Toast.makeText(SignUpActivity.this, "Couldn't create account", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                usersRef.child(currentUserUid).setValue(user);
// نقل بين صفحه الانشاء لصفحه الرئيسيه
                                Intent intent = new Intent(SignUpActivity.this, ContentActivity.class);
                                startActivity(intent);
                            } else {
                                // رساله ازا غلط
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}