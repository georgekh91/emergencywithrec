package com.example.georgesproject;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easytest.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Sign> signList;

    public RecyclerViewAdapter(List<Sign> signList) {
        this.signList = signList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sign sign = signList.get(position);
        holder.bind(sign);
    }

    @Override
    public int getItemCount() {
        return signList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public View v;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.reimageView);
            textView = itemView.findViewById(R.id.retextView);
            v = itemView;
        }

        public void bind(Sign sign) {
            textView.setText(sign.getSignName());
            Picasso.get().load(sign.getImageUrl()).into(imageView);
        }
    }
}
