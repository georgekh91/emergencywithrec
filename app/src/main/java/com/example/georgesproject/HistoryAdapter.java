package com.example.georgesproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
   import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

    public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
        private int px;
        Context context;
        ArrayList<Contact> callLogModelArrayList;

        public Contact(Context context, ArrayList<Contact> ContactArrayList) {
            this.context = context;
            this.ContactArrayList = ContactArrayList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Resources r = parent.getResources();
            px = Math.round(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 8,r.getDisplayMetrics()));
            View v = LayoutInflater.from(context).inflate(R.layout.layout_call_log, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            int i = position;
            if(i == 0){
                ViewGroup.MarginLayoutParams layoutParams =
                        (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
                layoutParams.topMargin = px;
                holder.cardView.requestLayout();
            }

            Contact currentLog = Contact.get(position);
            holder.tv_ph_num.setText(currentLog.get());
            holder.tv_contact_name.setText(currentLog.getContactName());
            holder.tv_call_email.setText(currentLog.getEmail());
           holder.tv_call_time.setText(currentLog.getsendTime());
            tv_call_date = itemView.findViewById(R.id.layout_call_log_date);
        }

        @Override
        public int getItemCount() {
            return callLogModelArrayList==null ? 0 : callLogModelArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            CardView cardView;
            TextView tv_ph_num, tv_contact_name, tv_call_type, tv_call_date, tv_call_time, tv_call_duration;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_contact_name = itemView.findViewById(R.id.layout_call_log_contact_name);
                tv_call_date = itemView.findViewById(R.id.layout_call_log_date);
                tv_call_time = itemView.findViewById(R.id.layout_call_log_time);
                cardView = itemView.findViewById(R.id.layout_call_log_email);
            }
        }
    }