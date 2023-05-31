package com.example.georgesproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Contact> contacts;

    public RecyclerViewAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        return new ViewHolder(itemView);
    }

    public void updateData(List<Contact> newDataList) {
        contacts = newDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact sign = contacts.get(position);
        holder.bind(sign);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        public TextView textView5;
        public View v;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.retextView);
            textView1 = itemView.findViewById(R.id.retextView1);
            textView2 = itemView.findViewById(R.id.retextView2);
            textView3 = itemView.findViewById(R.id.retextView3);
            textView4 = itemView.findViewById(R.id.retextView4);
            textView5 = itemView.findViewById(R.id.retextView5);

            v = itemView;
            context = itemView.getContext();
        }

        public String getCurrentTimeFromPreferences() {
            SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            return preferences.getString("currentTime", "");
        }

        public void bind(Contact sign) {
            textView1.setText(sign.getName());
            textView3.setText(sign.getEmail());
            textView5.setText(getCurrentTimeFromPreferences());
        }
    }
}