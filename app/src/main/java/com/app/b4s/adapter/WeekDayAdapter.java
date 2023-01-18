package com.app.b4s.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.commons.CommonMethods;
import com.app.b4s.model.OnHomeWorkData;
import com.app.b4s.model.WeekDay;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.HWM.Activity.QuestionsActivity;
import com.app.b4s.view.Home.Fragment.CalendarFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.ViewHolder> {
    Activity activity;
    ArrayList<WeekDay> onHomeWordData;
    CalendarFragment calendarFragment;
    int selectedPosition=-1;
    public WeekDayAdapter(ArrayList<WeekDay> onReviewHomeWorks, Activity activity,CalendarFragment calendarFragment) {
        this.onHomeWordData = onReviewHomeWorks;
        this.activity = activity;
        this.calendarFragment = calendarFragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.week_day_layout, parent, false);
        return new ViewHolder(listItem);
    }

    private int selectedPos = RecyclerView.NO_POSITION;

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvWeekDAY.setText(onHomeWordData.get(position).getDay());
        if(selectedPosition==position)
            holder.imgText.setImageResource(R.drawable.bg_circle_week);
        else
            holder.imgText.setImageResource(0);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=position;
                notifyDataSetChanged();
                calendarFragment.loadDailyTimeTables(1,onHomeWordData.get(position).getDate());

            }
        });

    }

    @Override
    public int getItemCount() {
        return onHomeWordData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWeekDAY;
        public ImageView imgText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvWeekDAY = itemView.findViewById(R.id.tvWeekDAY);
            this.imgText = itemView.findViewById(R.id.imgText);

        }
    }
}
