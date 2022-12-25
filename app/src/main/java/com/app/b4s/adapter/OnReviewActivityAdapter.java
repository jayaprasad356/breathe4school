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
import com.app.b4s.model.OnReviewActivity;
import com.app.b4s.view.HWM.Activity.ActivityOnReviewActivity;

import java.util.ArrayList;

public class OnReviewActivityAdapter extends RecyclerView.Adapter<OnReviewActivityAdapter.ViewHolder> {
    Activity activity;
    ArrayList<OnReviewActivity> onReviewActivities;

    public OnReviewActivityAdapter(ArrayList<OnReviewActivity> onReviewActivities, Activity activity) {
        this.onReviewActivities = onReviewActivities;
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
        holder.tvSubject.setText(onReviewActivities.get(position).getSub());
        holder.tvcontent.setText(onReviewActivities.get(position).getContent());
        holder.tvTime.setText(onReviewActivities.get(position).getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityOnReviewActivity.class);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return onReviewActivities.size();
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
