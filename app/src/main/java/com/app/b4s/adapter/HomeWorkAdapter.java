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
import com.app.b4s.model.OnHomeWordData;
import com.app.b4s.view.HWM.Activity.OnReviewActivity;

import java.util.ArrayList;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.ViewHolder> {
    Activity activity;
    ArrayList<OnHomeWordData> onHomeWordData;

    public HomeWorkAdapter(ArrayList<OnHomeWordData> onReviewHomeWorks, Activity activity) {
        this.onHomeWordData = onReviewHomeWorks;
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
        holder.tvSubName.setText(onHomeWordData.get(position).getName());
        holder.tvDate.setText(onHomeWordData.get(position).getDeadline());
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
        return onHomeWordData.size();
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
