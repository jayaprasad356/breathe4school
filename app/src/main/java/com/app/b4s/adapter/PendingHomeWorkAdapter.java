package com.app.b4s.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.PendingHomeWork;
import com.app.b4s.view.HWM.Activity.SubmissionHomeworkFormbasedActivity;

import java.util.ArrayList;

public class PendingHomeWorkAdapter extends RecyclerView.Adapter<PendingHomeWorkAdapter.ViewHolder> {
    Activity activity;
    ArrayList<PendingHomeWork> pendingHomeWorks;

    public PendingHomeWorkAdapter(ArrayList<PendingHomeWork> pendingHomeWorks, Activity activity) {
        this.pendingHomeWorks = pendingHomeWorks;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_pending, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSubName.setText(pendingHomeWorks.get(position).getSub_name());
        holder.tvTopic.setText(pendingHomeWorks.get(position).getTopic());
        holder.tvDate.setText(pendingHomeWorks.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SubmissionHomeworkFormbasedActivity.class);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pendingHomeWorks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubName,tvTopic,tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvSubName = itemView.findViewById(R.id.tvSubName);
            this.tvTopic = itemView.findViewById(R.id.tvSubName);
            this.tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
