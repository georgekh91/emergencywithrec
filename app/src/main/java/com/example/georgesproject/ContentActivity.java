package com.example.georgesproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ContentActivity extends AppCompatActivity {
    private int seconds = 0;


    private boolean running;

    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.optins,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item3:
                Intent intent1 = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(intent1);
                return true;
            case R.id.item2:
                appdetails app = new appdetails();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.framelayout, app, app.getTag())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
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
                }


                handler.postDelayed(this, 1000);
            }
        });
    }


    public void onSaveinstancestate(Bundle savedInstanceState) {
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }


    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }


   /* public void onClickStart (View view)
    {
        running = true;
    }


    public void onClickStop (View view)
    {
        running = false;
    }


    public void onClickReset (View view)
    {
        running = false;
        seconds = 0;
    }*/


    public void onClickStart(android.view.View view) {
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
        Intent intent = new Intent(ContentActivity.this, AddEmergencyPerson.class);
        startActivity(intent);
    }
}