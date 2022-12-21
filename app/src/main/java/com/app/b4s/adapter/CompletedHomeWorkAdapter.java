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
import com.app.b4s.model.ComplededHomeWork;
import com.app.b4s.model.OnReviewHomeWork;
import com.app.b4s.view.CompletedHomeworkActivity;
import com.app.b4s.view.SubmissionHomeworkFormbasedActivity;

import java.util.ArrayList;

public class CompletedHomeWorkAdapter extends RecyclerView.Adapter<CompletedHomeWorkAdapter.ViewHolder> {
    Activity activity;
    ArrayList<ComplededHomeWork> complededHomeWorks;

    public CompletedHomeWorkAdapter(ArrayList<ComplededHomeWork> complededHomeWorks, Activity activity) {
        this.complededHomeWorks = complededHomeWorks;
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
        holder.tvSubName.setText(complededHomeWorks.get(position).getSub_name());
        holder.tvTopic.setText(complededHomeWorks.get(position).getTopic());
        holder.tvDate.setText(complededHomeWorks.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CompletedHomeworkActivity.class);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return complededHomeWorks.size();
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
