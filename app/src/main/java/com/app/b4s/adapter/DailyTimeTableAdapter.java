package com.app.b4s.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.commons.CommonMethods;
import com.app.b4s.model.DailyTimeTables;
import com.app.b4s.utilities.Constant;

import java.util.ArrayList;
import java.util.Random;


public class DailyTimeTableAdapter extends RecyclerView.Adapter<com.app.b4s.adapter.DailyTimeTableAdapter.ViewHolder> {
    Activity activity;
    CommonMethods commonMethods = new CommonMethods();
    ArrayList<DailyTimeTables> dailyTimeTables;

    public DailyTimeTableAdapter(ArrayList<DailyTimeTables> dailyTimeTables, Activity activity) {
        this.dailyTimeTables = dailyTimeTables;
        this.activity = activity;
    }


    @NonNull
    @Override
    public com.app.b4s.adapter.DailyTimeTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.daily_layout, parent, false);
        return new com.app.b4s.adapter.DailyTimeTableAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull com.app.b4s.adapter.DailyTimeTableAdapter.ViewHolder holder, int position) {
        int startTime = Integer.parseInt(dailyTimeTables.get(position).start_time);
        int endTime = Integer.parseInt(dailyTimeTables.get(position).end_time);
        if (position % 2 == 0) {
            holder.layout.setBackgroundColor(Color.parseColor("#28cd9c"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#ff848e"));
        }

        holder.startTime.setText(commonMethods.militaryToOrdinaryTime(startTime));
        holder.endTime.setText(commonMethods.militaryToOrdinaryTime(endTime));
        int duriation = endTime - startTime;
        holder.duriation.setText(duriation + "mins");
        holder.subject.setText(dailyTimeTables.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return dailyTimeTables.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView startTime, duriation, subject, endTime;
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.startTime = itemView.findViewById(R.id.tvstartTime);
            this.endTime = itemView.findViewById(R.id.tvEndTime);
            this.duriation = itemView.findViewById(R.id.tvDuriation);
            this.subject = itemView.findViewById(R.id.tvSubject);
            this.layout = itemView.findViewById(R.id.timeTableLayout);
        }
    }
}

