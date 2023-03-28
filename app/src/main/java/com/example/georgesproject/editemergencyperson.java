package com.example.georgesproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class editemergencyperson extends AppCompatActivity {

    EditText oldNameEditText = findViewById(R.id.old_name_edittext);
    EditText newNameEditText = findViewById(R.id.new_name_edittext);
    EditText oldEmailEditText = findViewById(R.id.old_email_edittext);
    EditText newEmailEditText = findViewById(R.id.new_email_edittext);

    // Get the user's input from the EditText views
    String oldName = oldNameEditText.getText().toString();
    String newName = newNameEditText.getText().toString();
    String oldEmail = oldEmailEditText.getText().toString();
    String newEmail = newEmailEditText.getText().toString();

// Check if the user wants to update the name
if(!oldName.isEmpty()&&!newName.isEmpty())

    {
        // Update the name
        // You can replace this with your own code for updating the name
        // For example, you could use a database or SharedPreferences to store the user's name
        // and update it accordingly
        updateName(oldName, newName);
    }

// Check if the user wants to update the email
if(!oldEmail.isEmpty()&&!newEmail.isEmpty())

    {
        // Update the email
        // You can replace this with your own code for updating the email
        // For example, you could use a database or SharedPreferences to store the user's email
        // and update it accordingly
        updateEmail(oldEmail, newEmail);
    }

    private void updateEmail(String oldEmail, String newEmail) {
        // Retrieve the user's data from a database or SharedPreferences
        User user = getUserData();

        // Check if the old email matches the user's current email
        if (user.getEmail().equals(oldEmail)) {
            // Update the user's email with the new value
            user.setEmail(newEmail);

            // Save the updated user data back to the database or SharedPreferences
            saveUserData(user);

            // Show a success message to the user
            Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Show an error message to the user if the old email is incorrect
            Toast.makeText(this, "Old email is incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}