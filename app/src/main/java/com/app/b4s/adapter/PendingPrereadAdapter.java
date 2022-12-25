package com.app.b4s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.PendingPreread;

import java.util.ArrayList;

public class PendingPrereadAdapter extends RecyclerView.Adapter<PendingPrereadAdapter.ViewHolder> {
    Activity activity;
    ArrayList<PendingPreread> pendingPrereads;

    public PendingPrereadAdapter(ArrayList<PendingPreread> pendingPrereads, Activity activity) {
        this.pendingPrereads = pendingPrereads;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_preread, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSubject.setText(pendingPrereads.get(position).getSub());
        holder.tvcontent.setText(pendingPrereads.get(position).getContent());
        holder.tvTime.setText(pendingPrereads.get(position).getTime());

    }


    @Override
    public int getItemCount() {
        return pendingPrereads.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubject,tvcontent,tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvSubject = itemView.findViewById(R.id.tvSubject);
            this.tvcontent = itemView.findViewById(R.id.tvcontent);
            this.tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
