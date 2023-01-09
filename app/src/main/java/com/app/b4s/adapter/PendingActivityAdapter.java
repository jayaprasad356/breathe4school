package com.app.b4s.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.PendingActivity;
import com.app.b4s.view.HWM.Activity.ActivityPendingActivity;

import java.util.ArrayList;

public class PendingActivityAdapter extends RecyclerView.Adapter<PendingActivityAdapter.ViewHolder> {
    Activity activity;
    ArrayList<PendingActivity> pendingActivities;

    public PendingActivityAdapter(ArrayList<PendingActivity> pendingActivities, Activity activity) {
        this.pendingActivities = pendingActivities;
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
        holder.tvSubject.setText(pendingActivities.get(position).getSub());
        holder.imageView.setBackground(activity.getDrawable(R.drawable.grey_lap));

        holder.tvcontent.setText(pendingActivities.get(position).getContent());
        holder.tvTime.setText(pendingActivities.get(position).getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityPendingActivity.class);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pendingActivities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubject,tvcontent,tvTime;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvSubject = itemView.findViewById(R.id.tvSubject);
            this.tvcontent = itemView.findViewById(R.id.tvcontent);
            this.tvTime = itemView.findViewById(R.id.tvTime);
            this.imageView=itemView.findViewById(R.id.ivfolder);
        }
    }
}
