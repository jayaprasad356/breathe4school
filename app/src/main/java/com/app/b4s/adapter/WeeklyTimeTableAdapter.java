package com.app.b4s.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.commons.CommonMethods;
import com.app.b4s.model.DailyTimeTables;
import com.app.b4s.model.WeeklyTimeTable;

import java.util.ArrayList;





public class WeeklyTimeTableAdapter extends RecyclerView.Adapter<com.app.b4s.adapter.WeeklyTimeTableAdapter.ViewHolder> {
    Activity activity;
    CommonMethods commonMethods = new CommonMethods();
    ArrayList<WeeklyTimeTable> weeklyTimeTables;

    public WeeklyTimeTableAdapter(ArrayList<WeeklyTimeTable> weeklyTimeTables, Activity activity) {
        this.weeklyTimeTables = weeklyTimeTables;
        this.activity = activity;
    }


    @NonNull
    @Override
    public com.app.b4s.adapter.WeeklyTimeTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.weekly_layout, parent, false);
        return new com.app.b4s.adapter.WeeklyTimeTableAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull com.app.b4s.adapter.WeeklyTimeTableAdapter.ViewHolder holder, int position) {
        int startTime = Integer.parseInt(weeklyTimeTables.get(position).start_time);
        int endTime = Integer.parseInt(weeklyTimeTables.get(position).end_time);

        holder.subjectM.setText(weeklyTimeTables.get(position).getName());
        holder.subjectT.setText(weeklyTimeTables.get(position).getName());
        holder.subjectTh.setText(weeklyTimeTables.get(position).getName());
        holder.subjectW.setText(weeklyTimeTables.get(position).getName());
        holder.subjectF.setText(weeklyTimeTables.get(position).getName());
        holder.subjectS.setText(weeklyTimeTables.get(position).getName());
        holder.subjectSu.setText(weeklyTimeTables.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return weeklyTimeTables.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectM,subjectT,subjectW,subjectTh,subjectF,subjectS,subjectSu;
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.subjectM = itemView.findViewById(R.id.tvSubjectnm);
            this.subjectT = itemView.findViewById(R.id.tvSubjectnt);
            this.subjectW = itemView.findViewById(R.id.tvSubjectw);
            this.subjectTh = itemView.findViewById(R.id.tvSubjectnth);
            this.subjectF = itemView.findViewById(R.id.tvSubjectf);
            this.subjectS = itemView.findViewById(R.id.tvSubjects);
            this.subjectSu = itemView.findViewById(R.id.tvSubjectsu);
        }
    }
}

