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
import com.app.b4s.model.OnReviewHomeWork;
import com.app.b4s.model.PendingHomeWork;
import com.app.b4s.view.OnReviewActivity;
import com.app.b4s.view.SubmissionHomeworkFormbasedActivity;

import java.util.ArrayList;

public class OnReviewHomeWorkAdapter extends RecyclerView.Adapter<OnReviewHomeWorkAdapter.ViewHolder> {
    Activity activity;
    ArrayList<OnReviewHomeWork> onReviewHomeWorks;

    public OnReviewHomeWorkAdapter(ArrayList<OnReviewHomeWork> onReviewHomeWorks, Activity activity) {
        this.onReviewHomeWorks = onReviewHomeWorks;
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
        holder.tvSubName.setText(onReviewHomeWorks.get(position).getSub_name());
        holder.tvTopic.setText(onReviewHomeWorks.get(position).getTopic());
        holder.tvDate.setText(onReviewHomeWorks.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, OnReviewActivity.class);
                activity.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return onReviewHomeWorks.size();
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
