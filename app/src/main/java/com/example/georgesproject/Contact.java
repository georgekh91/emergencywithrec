package com.example.georgesproject;

public class Contact {
    private final String name;
    private final String email;

    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getImageResourceId() {
        int[] imageIds = {R.drawable.contact_image_1, R.drawable.contact_image_2, R.drawable.contact_image_3};
        int index = Math.abs(name.hashCode()) % imageIds.length;
        return imageIds[index];
    }
}
