package com.app.b4s.adapter.HomepageAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.commons.CommonMethods;
import com.app.b4s.model.WeeklyTimeTable;

import java.util.ArrayList;


public class WeeklyTimeTableAdapter extends RecyclerView.Adapter<WeeklyTimeTableAdapter.ViewHolder> {
    Activity activity;
    CommonMethods commonMethods = new CommonMethods();
    ArrayList<WeeklyTimeTable> weeklyTimeTables;


    public WeeklyTimeTableAdapter(ArrayList<WeeklyTimeTable> weeklyTimeTables,Activity activity) {
        this.weeklyTimeTables = weeklyTimeTables;
        this.activity = activity;
    }


    @NonNull
    @Override
    public WeeklyTimeTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.weekly_layout, parent, false);
        return new WeeklyTimeTableAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyTimeTableAdapter.ViewHolder holder, int position) {

        holder.subjectM.setText(weeklyTimeTables.get(position).getMon_name());
        holder.subjectT.setText(weeklyTimeTables.get(position).getTue_name());
        holder.subjectW.setText(weeklyTimeTables.get(position).getWed_name());
        holder.subjectTh.setText(weeklyTimeTables.get(position).getThurs_name());
        holder.subjectF.setText(weeklyTimeTables.get(position).getFri_name());
        holder.subjectS.setText(weeklyTimeTables.get(position).getSatur_name());
        holder.subjectSu.setText(weeklyTimeTables.get(position).getSun_name());
    }


    @Override
    public int getItemCount() {
        return weeklyTimeTables.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectM, subjectT, subjectW, subjectTh, subjectF, subjectS, subjectSu;
        public LinearLayout layout;
        public RelativeLayout monday, tuesday, wednesday, thursday, friday, saturday, sunday;

        public ViewHolder(View itemView) {
            super(itemView);
            this.subjectM = itemView.findViewById(R.id.tvSubjectnm);
            this.subjectT = itemView.findViewById(R.id.tvSubjectnt);
            this.subjectW = itemView.findViewById(R.id.tvSubjectw);
            this.subjectTh = itemView.findViewById(R.id.tvSubjectnth);
            this.subjectF = itemView.findViewById(R.id.tvSubjectf);
            this.subjectS = itemView.findViewById(R.id.tvSubjects);
            this.subjectSu = itemView.findViewById(R.id.tvSubjectsu);
            this.monday = itemView.findViewById(R.id.mondayView);
            this.tuesday = itemView.findViewById(R.id.tuesdayView);
            this.wednesday = itemView.findViewById(R.id.wednesdayView);
            this.thursday = itemView.findViewById(R.id.thursDayView);
            this.friday = itemView.findViewById(R.id.fridayView);
            this.saturday = itemView.findViewById(R.id.saturView);
            this.sunday = itemView.findViewById(R.id.sundayView);
        }
    }
}

