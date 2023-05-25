package com.example.georgesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEmergencyPerson extends AppCompatActivity {

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    // بطلب الconnection عشان يروح على الdatabase عن طريق google-services.json
    private static final DatabaseReference contactsRef = db.getReference("contacts");
    // الي جوا الdatabase لقرائته او لاعطاء اوامر وين يروح
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // يمحي او يضيف user
    // تعاريف للاستخدام
    private EditText mName, mEmail;

    private Button mSubmit;


    private final View.OnClickListener mSubmitOnClick = new View.OnClickListener() {
        @Override // check email
        public void onClick(View v) {
            // بتفحص ازا فاضي وازا فاضي بتعطي مسج ازا لا بتحطو بالfirebase
            if (mName.getText().toString().trim().length() == 0 ||
                    mEmail.getText().toString().trim().length() == 0
            ) {
                //المسج الي بتطلع عشاشه ازا فاضي
                Toast.makeText(AddEmergencyPerson.this, "Missing fields.", Toast.LENGTH_SHORT).show();
                return;
            }
// بتودي على الfirebase
            if (mAuth.getCurrentUser() == null) {
                return;
            }

            // push means we have a list, and we want to add to the list.
            DatabaseReference newItemRef = contactsRef.child(mAuth.getCurrentUser().getUid()).push();
            newItemRef.setValue(new EmergencyPerson(mName.getText().toString(), mEmail.getText().toString()));
        }
    };

    @Override // add person for emer بتعبي الاكتفيتي
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemergencyperson);

        mName = findViewById(R.id.editTextTextPersonName);
        mEmail = findViewById(R.id.editTextTextEmailAddress);

        mSubmit = findViewById(R.id.button2);
        mSubmit.setOnClickListener(mSubmitOnClick);
    }

    @Override //options logout - about us
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    //menu التلت نقاط الي عملتهن
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item3: { // logout
                Intent intent = new Intent(AddEmergencyPerson.this, MainActivity.class);
                // عشان ازا عمل logout ميرجعش يفوت عن طريق back
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
            case R.id.item2: //about us
            {
                //تنقل من صفحه ال  addeme لصفحه aboutus
                Intent intent = new Intent(AddEmergencyPerson.this, AboutUsActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        //للكبسات التانيه
    }
}