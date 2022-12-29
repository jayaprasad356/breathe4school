package com.app.b4s.adapter.HomepageAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.commons.CommonMethods;
import com.app.b4s.model.DailyTimeTables;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.google.gson.internal.LinkedTreeMap;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DailyTimeTableAdapter extends RecyclerView.Adapter<DailyTimeTableAdapter.ViewHolder> {
    Activity activity;
    CommonMethods commonMethods = new CommonMethods();
    ArrayList<DailyTimeTables> dailyTimeTables;
    private String type;
    private Session session;
    boolean isJoinNow, setReminder, viewSummary;

    public DailyTimeTableAdapter(ArrayList<DailyTimeTables> dailyTimeTables, Activity activity, String type) {
        this.dailyTimeTables = dailyTimeTables;
        this.activity = activity;
        this.type = type;
    }


    @NonNull
    @Override
    public DailyTimeTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.daily_layout, parent, false);
        return new DailyTimeTableAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyTimeTableAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        session = new Session(activity);
        int startTime = Integer.parseInt(dailyTimeTables.get(position).start_time);
        int endTime = Integer.parseInt(dailyTimeTables.get(position).end_time);
        int duriation = endTime - startTime;
        LocalTime startTim;
        LocalTime endTim;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

        if (startTime < 1000)
            startTim = LocalTime.parse("0" + dailyTimeTables.get(position).getStart_time(),formatter);
        else
            startTim = LocalTime.parse(dailyTimeTables.get(position).getStart_time(),formatter);
        if (endTime < 1000)
            endTim = LocalTime.parse("0" + dailyTimeTables.get(position).getEnd_time(),formatter);
        else
            endTim = LocalTime.parse(dailyTimeTables.get(position).getEnd_time(),formatter);

        Duration duration = Duration.between(startTim, endTim);
        long minutes = duration.toMinutes();


        if (type.equals(Constant.STUDY_PLANER)) {
            holder.studyView.setVisibility(View.VISIBLE);
            holder.iconView.setVisibility(View.GONE);

        } else if (type.equals(Constant.LIVE_SESSION)) {
            if (!(dailyTimeTables.get(position).getName().equals(activity.getString(R.string.lunch)))) {
                holder.iconView.setVisibility(View.VISIBLE);
                holder.studyView.setVisibility(View.GONE);
                holder.iconView.setOnClickListener(view -> {
                    showClassDetailPopup(startTime, endTime, position);
                });
            }else {
                holder.timeTableLayout.setVisibility(View.GONE);
                holder.lunchLayout.setVisibility(View.VISIBLE);

            }

        }


        if (position % 2 == 0) {
            holder.timeTableLayout.setBackgroundColor(Color.parseColor("#28cd9c"));
        } else {
            holder.timeTableLayout.setBackgroundColor(Color.parseColor("#ff848e"));
        }

        holder.startTime.setText(commonMethods.militaryToOrdinaryTime(startTime));
        holder.endTime.setText(commonMethods.militaryToOrdinaryTime(endTime));

        if (duriation > 40) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, activity.getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._30sdp));
            holder.rcLayout.setLayoutParams(params);
        }
        holder.duriation.setText(minutes + "mins");
        holder.subject.setText(dailyTimeTables.get(position).getName());
        iconVisibility(holder, position);
        statusVisibility(holder, position, startTime, endTime);
        holder.editPlan.setOnClickListener(view -> showPopup(position));

    }

    private void showClassDetailPopup(int startTime, int endTime, int position) {
        Dialog dialog = new Dialog(activity);
        int duriat = endTime - startTime;
        String tim = commonMethods.militaryToOrdinaryTime(startTime) + " - " + commonMethods.militaryToOrdinaryTime(endTime);
        dialog.setContentView(R.layout.time_table_detail_popup);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        View view = dialog.findViewById(R.id.emptyView);
        TextView name, subject, date, time, duriation;
        name = dialog.findViewById(R.id.tvName);
        subject = dialog.findViewById(R.id.tvSubject);
        date = dialog.findViewById(R.id.tvDate);
        time = dialog.findViewById(R.id.tvTime);
        time.setText(tim);
        duriation = dialog.findViewById(R.id.tvDuriation);
        name.setText(session.getData(Constant.NAME));
        subject.setText(dailyTimeTables.get(position).getName());

        duriation.setText(duriat + "mins");

        if (dailyTimeTables.get(0).getJoinNow()) {
            dialog.findViewById(R.id.ivViewSummary).setVisibility(View.GONE);
        } else if (dailyTimeTables.get(0).getViewSummery()) {
            dialog.findViewById(R.id.btnJoinNow).setVisibility(View.GONE);
            view.setBackgroundColor(Color.parseColor("#C9C9C9"));
        } else if (dailyTimeTables.get(0).getSetReminder()) {
            dialog.findViewById(R.id.btnJoinNow).setVisibility(View.GONE);
            dialog.findViewById(R.id.ivViewSummary).setVisibility(View.GONE);
            dialog.findViewById(R.id.lastEmptyView).setVisibility(View.GONE);
            view.setBackgroundColor(Color.parseColor("#28CD9C"));
        }

        iconVisibilityInDetailPopup(dialog, position);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(activity.getDrawable(R.drawable.popup_background)); // Set the corner radius to 20dp
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    private void iconVisibilityInDetailPopup(Dialog dialog, int position) {
        if (dailyTimeTables.get(position).getActivity_id() != null) {
            dialog.findViewById(R.id.llActivityView).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.activityBtmView).setVisibility(View.VISIBLE);
        }
        if (dailyTimeTables.get(position).getAssessment_id() != null) {
            dialog.findViewById(R.id.llAssessmentView).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.assessmentView).setVisibility(View.VISIBLE);
        }
        if (dailyTimeTables.get(position).getPre_read_id() != null) {
            dialog.findViewById(R.id.llPreReadView).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.preReadView).setVisibility(View.VISIBLE);
        }
        if (dailyTimeTables.get(position).getBbb_lecture_id() != null) {
            dialog.findViewById(R.id.llPresentationView).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.lastEmptyView).setVisibility(View.VISIBLE);
        }

    }

    private void showPopup(int position) {
        EditText planerName, description;
        Spinner statTime, endTime;
        CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
        TextView title;

        List<String> subjects = session.getArrayList(Constant.SUBJECTS_KEY);
        List<String> subjectIds = session.getArrayList(Constant.SUBJECTS_ID_KEY);
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.add_study_planer);
        Spinner spinner = dialog.findViewById(R.id.spinnerSubjects);
        String sub = String.valueOf(((LinkedTreeMap) dailyTimeTables.get(position).getSubject()).get("name"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, subjects);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubject = parent.getItemAtPosition(position).toString();
                String selectedSubjectId = subjectIds.get(position);
                session.setData(Constant.SELECTED_SUBJECT, selectedSubject);
                session.setData(Constant.SELECTED_SUBJECT_ID, selectedSubjectId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        for (int i = 0; i < subjects.size(); i++) {
            if (sub.equals(subjects.get(i)))
                spinner.setSelection(i);
        }


        dialog.findViewById(R.id.btnCreateStudy).setVisibility(View.GONE);
        dialog.findViewById(R.id.btnDescardChanges).setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.btnSaveChanges).setVisibility(View.VISIBLE);
        planerName = dialog.findViewById(R.id.tvPlanerName);
        description = dialog.findViewById(R.id.etDescription);
        statTime = dialog.findViewById(R.id.spinnerST);
        endTime = dialog.findViewById(R.id.spinnerET);
        title = dialog.findViewById(R.id.title);

        title.setText(activity.getString(R.string.edit_study_planer));
        int start = Integer.parseInt(dailyTimeTables.get(position).getStart_time());
        int end = Integer.parseInt(dailyTimeTables.get(position).getEnd_time());


        description.setText(dailyTimeTables.get(position).getDescription());
        planerName.setText(dailyTimeTables.get(position).getName());


        monday = dialog.findViewById(R.id.cbMonday);
        tuesday = dialog.findViewById(R.id.cbTuesday);
        wednesday = dialog.findViewById(R.id.cbWednes);
        thursday = dialog.findViewById(R.id.cbThurs);
        friday = dialog.findViewById(R.id.cbFriday);
        saturday = dialog.findViewById(R.id.cbSatur);
        sunday = dialog.findViewById(R.id.cbSun);

        switch (dailyTimeTables.get(position).getDay()) {
            case Constant.MONDAY:
                monday.setChecked(true);
                monday.setBackgroundResource(R.drawable.check_box_bg);
                break;
            case Constant.TUESDAY:
                tuesday.setChecked(true);
                tuesday.setBackgroundResource(R.drawable.check_box_bg);
                break;
            case Constant.WEDNESDAY:
                wednesday.setChecked(true);
                wednesday.setBackgroundResource(R.drawable.check_box_bg);
                break;
            case Constant.THURSDAY:
                thursday.setChecked(true);
                thursday.setBackgroundResource(R.drawable.check_box_bg);
                break;
            case Constant.FRIDAY:
                friday.setChecked(true);
                friday.setBackgroundResource(R.drawable.check_box_bg);
                break;
            case Constant.SATURDAY:
                saturday.setChecked(true);
                saturday.setBackgroundResource(R.drawable.check_box_bg);
                break;
            case Constant.SUNDAY:
                sunday.setChecked(true);
                sunday.setBackgroundResource(R.drawable.check_box_bg);
                break;
        }


        if (start <= 700 && start < 800)
            statTime.setSelection(1);
        else if (start <= 800 && start < 900)
            statTime.setSelection(2);
        else if (start <= 900 && start < 1000)
            statTime.setSelection(3);
        else if (start <= 1000 && start < 1100)
            statTime.setSelection(4);
        else if (start <= 1100 && start < 1200)
            statTime.setSelection(5);
        else if (start <= 1200 && start < 1300)
            statTime.setSelection(6);
        else if (start <= 1300 && start < 1400)
            statTime.setSelection(7);
        else if (start <= 1400 && start < 1500)
            statTime.setSelection(8);
        else if (start <= 1500 && start < 1600)
            statTime.setSelection(9);
        else if (start <= 1600 && start < 1700)
            statTime.setSelection(10);
        else if (start <= 1700 && start < 1800)
            statTime.setSelection(11);
        else if (start <= 1800 && start < 1900)
            statTime.setSelection(11);

        if (end <= 700 && end < 800)
            endTime.setSelection(1);
        else if (end <= 800 && end < 900)
            endTime.setSelection(2);
        else if (end <= 900 && end < 1000)
            endTime.setSelection(3);
        else if (end <= 1000 && end < 1100)
            endTime.setSelection(4);
        else if (end <= 1100 && end < 1200)
            endTime.setSelection(5);
        else if (end <= 1200 && end < 1300)
            endTime.setSelection(6);
        else if (end <= 1300 && end < 1400)
            endTime.setSelection(7);
        else if (end <= 1400 && end < 1500)
            endTime.setSelection(8);
        else if (end <= 1500 && end < 1600)
            endTime.setSelection(9);
        else if (end <= 1600 && end < 1700)
            endTime.setSelection(10);
        else if (end <= 1700 && end < 1800)
            endTime.setSelection(11);
        else if (end <= 1800 && end < 1900)
            endTime.setSelection(11);
        else if (end <= 1900 && end < 2000)
            endTime.setSelection(11);


//        monday=dialog.findViewById(R.id.cbMonday).setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbMonday)));
//        dialog.findViewById(R.id.cbTuesday)         .setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbTuesday)));
//        dialog.findViewById(R.id.cbWednes)      .setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbWednes)));
//        dialog.findViewById(R.id.cbThurs)           .setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbThurs)));
//        dialog.findViewById(R.id.cbFriday)          .setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbFriday)));
//        dialog.findViewById(R.id.cbSatur)           .setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbSatur)));
//        dialog.findViewById(R.id.cbSun)                 .setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbSun)));
//        dialog.findViewById(R.id.btnCreateStudy)            .setOnClickListener(view -> apiCall(dialog));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    private void statusVisibility(@NonNull ViewHolder holder, int position, int startTime, int endTime) {
        if (type.equals(Constant.STUDY_PLANER)) {
            holder.title.setText("Study Planer 01");
            holder.editPlan.setVisibility(View.VISIBLE);
        } else {
            int currentTime = Integer.parseInt(commonMethods.getCurrentMilitaryTime());
            if (currentTime > startTime && currentTime < endTime) {
                holder.joinNow.setVisibility(View.VISIBLE);
                dailyTimeTables.get(0).setJoinNow(true);
            } else {
                dailyTimeTables.get(0).setJoinNow(false);
            }
            if (currentTime > startTime && currentTime > endTime) {
                holder.viewSummbery.setVisibility(View.VISIBLE);
                dailyTimeTables.get(0).setViewSummery(true);
            } else {
                dailyTimeTables.get(0).setViewSummery(false);
            }
            if (!(currentTime > startTime && currentTime > endTime)) {
                holder.setReminer.setVisibility(View.VISIBLE);
                dailyTimeTables.get(0).setSetReminder(true);
            } else {
                dailyTimeTables.get(0).setSetReminder(false);
            }
        }
    }

    private void iconVisibility(@NonNull ViewHolder holder, int position) {
        if (dailyTimeTables.get(position).getActivity_id() == null)
            holder.activityView.setVisibility(View.INVISIBLE);
        if (dailyTimeTables.get(position).getAssessment_id() == null)
            holder.assessmentView.setVisibility(View.INVISIBLE);
        if (dailyTimeTables.get(position).getPre_read_id() == null)
            holder.preReadView.setVisibility(View.INVISIBLE);
        if (dailyTimeTables.get(position).getBbb_lecture_id() == null)
            holder.presentationView.setVisibility(View.INVISIBLE);
    }


    @Override
    public int getItemCount() {
        return dailyTimeTables.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView startTime, duriation, subject, endTime, setReminer, joinNow, onReminder, viewSummbery, studyView, editPlan, title;
        public LinearLayout timeTableLayout, iconView,lunchLayout;
        public RelativeLayout rcLayout;
        public ImageView assessmentView, preReadView, activityView, presentationView;
        public View emptyview;

        public ViewHolder(View itemView) {
            super(itemView);
            this.startTime = itemView.findViewById(R.id.tvstartTime);
            this.endTime = itemView.findViewById(R.id.tvEndTime);
            this.duriation = itemView.findViewById(R.id.tvDuriation);
            this.subject = itemView.findViewById(R.id.tvSubject);
            this.timeTableLayout = itemView.findViewById(R.id.timeTableLayout);
            this.lunchLayout=itemView.findViewById(R.id.LunchLayout);
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
            this.title = itemView.findViewById(R.id.tvTitle);
            this.rcLayout = itemView.findViewById(R.id.customHeightView);
            this.emptyview = itemView.findViewById(R.id.tvview7);
        }
    }
}

