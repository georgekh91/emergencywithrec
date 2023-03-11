package com.example.georgesproject;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactView extends LinearLayout {

    public ContactView(Context context, Contact contact) {
        super(context);

        View mainView = inflate(getContext(), R.layout.view_contact, this);
        TextView nameTextView = mainView.findViewById(R.id.contactNameTV);
        TextView emailTextView = mainView.findViewById(R.id.contactEmailTV);

        nameTextView.setText(contact.getName());
        emailTextView.setText(contact.getEmail());
    }
}
