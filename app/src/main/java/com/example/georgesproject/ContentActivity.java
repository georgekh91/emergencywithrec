package com.example.georgesproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContentActivity extends AppCompatActivity {

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();

    private static final DatabaseReference contactsRef = db.getReference("contacts");
    // الثواني المطلوبه
    //    private static final int SEND_AT_SECONDS = 1800;
    private static final int SEND_AT_SECONDS = 10;

    private LinearLayout contactsView;

    private final List<Contact> contactList = new ArrayList<>();

    private int seconds = 0;


    private boolean running;

    private boolean wasRunning;

    @Override // timer لانشاء ال
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        contactsView = findViewById(R.id.contactsview);
        if (savedInstanceState != null) {

            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();

        if (mAuth.getCurrentUser() == null) {
            return;
        }

        contactsRef.child(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Contact contact = snapshot.getValue(Contact.class);

                if (contact == null) {
                    return;
                }

                contactList.add(contact);
                contactsView.addView(new ContactView(getApplicationContext(), contact));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override // options xml للانشاء
    // وابضا مينو
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item3: { // logout الموجوده في الثلاث نقاط
                //انتقال صفحات
                Intent intent = new Intent(ContentActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
            case R.id.item2: //about us الموجوده في الثلاث نقاط
            {
                // انتقال صفحات
                Intent intent = new Intent(ContentActivity.this, AboutUsActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.item4: //history الموجودة في الثلاث نقاط
            {
                // انتقال صفحات
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout, new history());
                ft.commit();
                return true;
            }
            default:
                // Handle other menu items if needed
                return super.onOptionsItemSelected(item);
        }
    }

    // تشغيل التايمر
    private void runTimer() {


        final TextView timeView
                = (TextView) findViewById(
                R.id.time_view);


        final Handler handler
                = new Handler();


        handler.post(new Runnable() {
            @Override

            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                timeView.setText(time);

                if (running) {
                    seconds++;

                    if (seconds >= SEND_AT_SECONDS) {
                        sendEmailToContacts();
                        running = false;
                        seconds = 0;
                    }
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    //private Location getLastKnownLocation() {
    //    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    //    // Check if location permissions are granted
    //    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
    //        // Get the last known location from the provider
    //        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    //    }

    //    return null;
    //}

    // يتم اخذ اخر لوكيشن عبره
    private Location getLastKnownLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = null;

            try {
                l = locationManager.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    // يتم ارسال الموقع ورساله هنا
    private void sendEmailToContacts() {
        if (mAuth.getCurrentUser() == null) {
            return;
        }
// اذا لم يعطي البريمشن يدخل هنا
        Location location = getLastKnownLocation();
        String locationString = "Unknown", googleMapsLink = "Unavailable";


        if (location != null) {
            locationString = String.format(Locale.US, "(%f, %f)", location.getLatitude(), location.getLongitude());
            googleMapsLink = String.format(Locale.US, "https://maps.google.com/?q=%f,%f", location.getLatitude(), location.getLongitude());
        }

        String emailContent = "Hello %s, your friend %s is in danger. Last known location: %s\nGoogle maps: %s";

        for (Contact contact : contactList) {
            String emailBody = String.format(
                    emailContent,
                    contact.getName(),
                    mAuth.getCurrentUser().getEmail(),
                    locationString,
                    googleMapsLink
            );
            EmailUtils.sendEmail(contact.getEmail(), emailBody);

            updateLastSentEmailTime(contact);
        }
    }

    private void updateLastSentEmailTime(Contact contact) {
        if (mAuth.getCurrentUser() == null) {
            return;
        }

        final DatabaseReference contactRef = contactsRef.child(mAuth.getCurrentUser().getUid()).child(contact.getKey());
        contact.setLastSentEmailTime(new Date().getTime());

        contactRef.setValue(contact);
    }

    public void onSaveinstancestate(Bundle savedInstanceState) {
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }

    // لتوقيف التايمر
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // لاعاده تشغيله
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    public void onClickAddEmergencyPerson(View view) {
        // تبديل صفحات للدخول الى اضافه شخص للحمايه
        Intent intent = new Intent(ContentActivity.this, AddEmergencyPerson.class);
        startActivity(intent);
    }

}