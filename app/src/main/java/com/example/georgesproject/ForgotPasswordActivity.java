package com.example.georgesproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.UUID;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static final DatabaseReference usersRef = db.getReference("users");
    private static final DatabaseReference resetCodesRef = db.getReference("reset_codes");

    private static final Random mRand = new Random();

    private EditText mEmailEditText;
    private Button mSubmitButton;
// يمرق على الايميلل ويلاقي بال firebase
    private final View.OnClickListener mSubmitButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = mEmailEditText.getText().toString().trim();
            usersRef.orderByChild("userEmail").equalTo(email)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean emailExistsInDb = snapshot.exists();

                            if (!emailExistsInDb) return;
// بطلع من الاكتفيتي على الساين ان
                            OnCompleteListener<Void> onCompleteListener = (OnCompleteListener<Void>) task -> {
                                ForgotPasswordActivity.this.finish();
                            };
// ببعت رساله لانو مزبطش
                            OnFailureListener onFailureListener = (OnFailureListener) task -> {
                                Toast.makeText(ForgotPasswordActivity.this, "Couldn't send reset email", Toast.LENGTH_SHORT).show();
                            };

                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(onCompleteListener).addOnFailureListener(onFailureListener);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        mEmailEditText = findViewById(R.id.editTextTextEmailAddress2);
        mSubmitButton = findViewById(R.id.button4);
        mSubmitButton.setOnClickListener(mSubmitButtonClickListener);
    }

}