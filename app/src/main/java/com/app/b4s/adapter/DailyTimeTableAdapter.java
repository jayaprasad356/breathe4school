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
import com.app.b4s.view.CalendarFragment;

import java.util.ArrayList;


public class DailyTimeTableAdapter extends RecyclerView.Adapter<com.app.b4s.adapter.DailyTimeTableAdapter.ViewHolder> {
    Activity activity;
    CommonMethods commonMethods = new CommonMethods();
    ArrayList<DailyTimeTables> dailyTimeTables;
    private String type;

    public DailyTimeTableAdapter(ArrayList<DailyTimeTables> dailyTimeTables, Activity activity, String type) {
        this.dailyTimeTables = dailyTimeTables;
        this.activity = activity;
        this.type = type;
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

        if (type.equals(Constant.STUDY_PLANER)) {
            holder.studyView.setVisibility(View.VISIBLE);
            holder.iconView.setVisibility(View.GONE);

        } else if (type.equals(Constant.LIVE_SESSION)) {
            holder.iconView.setVisibility(View.VISIBLE);
            holder.studyView.setVisibility(View.GONE);
        }

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
        iconVisibility(holder, position);

        statusVisibility(holder, position, startTime, endTime);

    }

    private void statusVisibility(@NonNull ViewHolder holder, int position, int startTime, int endTime) {
        if (type.equals(Constant.STUDY_PLANER)) {

            holder.title.setText("Study Planer 01");
            holder.editPlan.setVisibility(View.VISIBLE);
        } else {
            int currentTime = Integer.parseInt(commonMethods.getCurrentMilitaryTime());
            if (currentTime > startTime && currentTime < endTime)
                holder.joinNow.setVisibility(View.VISIBLE);
            if (currentTime > startTime && currentTime > endTime)
                holder.viewSummbery.setVisibility(View.VISIBLE);
            if (!(currentTime > startTime && currentTime > endTime))
                holder.setReminer.setVisibility(View.VISIBLE);
        }
    }

    private void iconVisibility(@NonNull ViewHolder holder, int position) {
        if (dailyTimeTables.get(position).getActivity_id() == null)
            holder.activityView.setVisibility(View.INVISIBLE);
        if (dailyTimeTables.get(position).getAssessment_id() == null)
            holder.assessmentView.setVisibility(View.INVISIBLE);
        if (dailyTimeTables.get(position).getPre_read_id() == null)
            holder.preReadView.setVisibility(View.INVISIBLE);
        if (dailyTimeTables.get(position).getBbb_lecture_id() != null)
            holder.presentationView.setVisibility(View.INVISIBLE);
    }


    @Override
    public int getItemCount() {
        return dailyTimeTables.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView startTime, duriation, subject, endTime, setReminer, joinNow, onReminder, viewSummbery, studyView, editPlan, title;
        public LinearLayout layout, iconView;
        public ImageView assessmentView, preReadView, activityView, presentationView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.startTime = itemView.findViewById(R.id.tvstartTime);
            this.endTime = itemView.findViewById(R.id.tvEndTime);
            this.duriation = itemView.findViewById(R.id.tvDuriation);
            this.subject = itemView.findViewById(R.id.tvSubject);
            this.layout = itemView.findViewById(R.id.timeTableLayout);
            this.activityView = itemView.findViewById(R.id.ivActivity);
            this.assessmentView = itemView.findViewById(R.id.ivAssessment);
            this.preReadView = itemView.findViewById(R.id.ivPreRead);
            this.presentationView = itemView.findViewById(R.id.ivPresentation);
            this.setReminer = itemView.findViewById(R.id.tvSetReminderView);
            this.joinNow = itemView.findViewById(R.id.tvJoinNowView);
            this.onReminder = itemView.findViewById(R.id.tvOnReminderView);
            this.viewSummbery = itemView.findViewById(R.id.tvViewSummeryView);
            this.iconView = itemView.findViewById(R.id.iconView);
            this.studyView = itemView.findViewById(R.id.studyView);
            this.editPlan = itemView.findViewById(R.id.tvEditPlaner);
            this.title=itemView.findViewById(R.id.tvTitle);
        }
    }
}

