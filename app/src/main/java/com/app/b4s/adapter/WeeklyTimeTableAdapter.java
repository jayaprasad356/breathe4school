package com.app.b4s.adapter;

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
import com.app.b4s.model.days.Friday;
import com.app.b4s.model.days.Monday;
import com.app.b4s.model.days.Saturday;
import com.app.b4s.model.days.Sunday;
import com.app.b4s.model.days.Thursday;
import com.app.b4s.model.days.Tuesday;
import com.app.b4s.model.days.Wednesday;

import java.util.ArrayList;


public class WeeklyTimeTableAdapter extends RecyclerView.Adapter<com.app.b4s.adapter.WeeklyTimeTableAdapter.ViewHolder> {
    Activity activity;
    CommonMethods commonMethods = new CommonMethods();
    ArrayList<Monday> mondays;
    ArrayList<Tuesday> tuesdays;
    ArrayList<Wednesday> wednesdays;
    ArrayList<Thursday> thursdays;
    ArrayList<Friday> fridays;
    ArrayList<Saturday> saturdays;
    ArrayList<Sunday> sundays;


    public WeeklyTimeTableAdapter(ArrayList<Monday> mondays, ArrayList<Tuesday> tuesdays, ArrayList<Wednesday> wednesdays, ArrayList<Thursday> thursdays, ArrayList<Friday> fridays, ArrayList<Saturday> saturdays, ArrayList<Sunday> sundays, Activity activity) {
        this.mondays = mondays;
        this.activity = activity;
        this.tuesdays = tuesdays;
        this.wednesdays = wednesdays;
        this.thursdays = thursdays;
        this.fridays = fridays;
        this.saturdays = saturdays;
        this.sundays = sundays;
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
        if (position == 0) {
            if (mondays.get(position).getStart_time() >= 700 && mondays.get(position).getEnd_time() <= 740)
                holder.subjectM.setText(mondays.get(position).getName());
            if (tuesdays.get(position).getStart_time() >= 700 && tuesdays.get(position).getEnd_time() <= 740)
                holder.subjectT.setText(tuesdays.get(position).getName());
            if (wednesdays.get(position).getStart_time() >= 700 && wednesdays.get(position).getEnd_time() <= 740)
                holder.subjectTh.setText(wednesdays.get(position).getName());
            if (thursdays.get(position).getStart_time() >= 700 && thursdays.get(position).getEnd_time() <= 740)
                holder.subjectW.setText(thursdays.get(position).getName());
            if (fridays.get(position).getStart_time() >= 700 && fridays.get(position).getEnd_time() <= 740)
                holder.subjectF.setText(fridays.get(position).getName());
            if (saturdays.get(position).getStart_time() >= 700 && saturdays.get(position).getEnd_time() <= 740)
                holder.subjectS.setText(saturdays.get(position).getName());
            if (sundays.get(position).getStart_time() >= 700 && sundays.get(position).getEnd_time() <= 740)
                holder.subjectSu.setText(sundays.get(position).getName());
            else
                holder.sunday.setVisibility(View.INVISIBLE);
        }
        if (position == 1) {
            if (mondays.get(position).getStart_time() >= 740 && mondays.get(position).getEnd_time() <= 820)
                holder.subjectM.setText(mondays.get(position).getName());
            if (tuesdays.get(position).getStart_time() >= 740 && tuesdays.get(position).getEnd_time() <= 820)
                holder.subjectT.setText(tuesdays.get(position).getName());
            if (wednesdays.get(position).getStart_time() >= 740 && wednesdays.get(position).getEnd_time() <= 820)
                holder.subjectTh.setText(wednesdays.get(position).getName());
            if (thursdays.get(position).getStart_time() >= 740 && thursdays.get(position).getEnd_time() <= 820)
                holder.subjectW.setText(thursdays.get(position).getName());
            if (fridays.get(position).getStart_time() >= 740 && fridays.get(position).getEnd_time() <= 820)
                holder.subjectF.setText(fridays.get(position).getName());
            if (saturdays.get(position).getStart_time() >= 740 && saturdays.get(position).getEnd_time() <= 820)
                holder.subjectS.setText(saturdays.get(position).getName());
            if (sundays.get(position).getStart_time() >= 740 && sundays.get(position).getEnd_time() <= 820)
                holder.subjectSu.setText(sundays.get(position).getName());
            else
                holder.sunday.setVisibility(View.INVISIBLE);
        }
        if (position == 2) {
            if (mondays.size() > position && mondays.get(position).getStart_time() >= 820 && mondays.get(position).getEnd_time() <= 900)
                holder.subjectM.setText(mondays.get(position).getName());
            if (tuesdays.size() > position && tuesdays.get(position).getStart_time() >= 820 && tuesdays.get(position).getEnd_time() <= 900)
                holder.subjectT.setText(tuesdays.get(position).getName());
            if (wednesdays.size() > position && wednesdays.get(position).getStart_time() >= 820 && wednesdays.get(position).getEnd_time() <= 900)
                holder.subjectTh.setText(wednesdays.get(position).getName());
            if (thursdays.size() > position && thursdays.get(position).getStart_time() >= 820 && thursdays.get(position).getEnd_time() <= 900)
                holder.subjectW.setText(thursdays.get(position).getName());
            if (fridays.size() > position && fridays.get(position).getStart_time() >= 820 && fridays.get(position).getEnd_time() <= 900)
                holder.subjectF.setText(fridays.get(position).getName());
            if (saturdays.size() > position && saturdays.get(position).getStart_time() >= 820 && saturdays.get(position).getEnd_time() <= 900)
                holder.subjectS.setText(saturdays.get(position).getName());
            if (sundays.size() > position && sundays.get(position).getStart_time() >= 820 && sundays.get(position).getEnd_time() <= 900)
                holder.subjectSu.setText(sundays.get(position).getName());
            else
                holder.sunday.setVisibility(View.INVISIBLE);
        }
        if (position == 3) {
            if (mondays.size() > position && mondays.get(position).getStart_time() >= 1130 && mondays.get(position).getEnd_time() <= 1230)
                holder.subjectM.setText(mondays.get(position).getName());
            else
                holder.monday.setVisibility(View.INVISIBLE);
            if (tuesdays.size() > position && tuesdays.get(position).getStart_time() >= 1130 && tuesdays.get(position).getEnd_time() <= 1230)
                holder.subjectT.setText(tuesdays.get(position).getName());
            else
                holder.tuesday.setVisibility(View.INVISIBLE);
            if (wednesdays.size() > position && wednesdays.get(position).getStart_time() >= 1130 && wednesdays.get(position).getEnd_time() <= 1230)
                holder.subjectTh.setText(wednesdays.get(position).getName());
            else
                holder.wednesday.setVisibility(View.INVISIBLE);
            if (thursdays.size() > position && thursdays.get(position).getStart_time() >= 1130 && thursdays.get(position).getEnd_time() <= 1230)
                holder.subjectW.setText(thursdays.get(position).getName());
            else
                holder.thursday.setVisibility(View.INVISIBLE);
            if (fridays.size() > position && fridays.get(position).getStart_time() >= 1130 && fridays.get(position).getEnd_time() <= 1230)
                holder.subjectF.setText(fridays.get(position).getName());
            else
                holder.friday.setVisibility(View.INVISIBLE);
            if (saturdays.size() > position && saturdays.get(position).getStart_time() >= 1130 && saturdays.get(position).getEnd_time() <= 1230)
                holder.subjectS.setText(saturdays.get(position).getName());
            else
                holder.saturday.setVisibility(View.INVISIBLE);
            if (sundays.size() > position && sundays.get(position).getStart_time() >= 1130 && sundays.get(position).getEnd_time() <= 1230)
                holder.subjectSu.setText(sundays.get(position).getName());
            else
                holder.sunday.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return 5;
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

