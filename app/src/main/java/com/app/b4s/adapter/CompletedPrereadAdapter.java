package com.app.b4s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.CompletedPreread;

import java.util.ArrayList;

public class CompletedPrereadAdapter extends RecyclerView.Adapter<CompletedPrereadAdapter.ViewHolder> {
    Activity activity;
    ArrayList<CompletedPreread> completedPrereads;

    public CompletedPrereadAdapter(ArrayList<CompletedPreread> completedPrereads, Activity activity) {
        this.completedPrereads = completedPrereads;
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
        holder.tvSubject.setText(completedPrereads.get(position).getSub());
        holder.tvcontent.setText(completedPrereads.get(position).getContent());
        holder.tvTime.setText(completedPrereads.get(position).getTime());

    }


    @Override
    public int getItemCount() {
        return completedPrereads.size();
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
