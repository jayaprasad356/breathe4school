package com.app.b4s.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.WeekDay;
import com.app.b4s.view.Home.Fragment.CalendarFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.ViewHolder> {
    Activity activity;
    ArrayList<WeekDay> onHomeWordData;
    CalendarFragment calendarFragment;
    int selectedPosition=-1;
    TextView tvDate;
    public WeekDayAdapter(ArrayList<WeekDay> onReviewHomeWorks, Activity activity, CalendarFragment calendarFragment, TextView tvDate) {
        this.onHomeWordData = onReviewHomeWorks;
        this.activity = activity;
        this.calendarFragment = calendarFragment;
        this.tvDate=tvDate;
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


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvWeekDAY.setText(onHomeWordData.get(position).getDay());
        if(selectedPosition==position){



           // holder.tvWeekDAY.setBackgroundDrawable(R.drawable.bg_circle_week);
            holder.tvWeekDAY.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_circle_week));
            holder.tvWeekDAY.setTextColor(ContextCompat.getColor(activity, R.color.white));

        }

        else{

            holder.tvWeekDAY.setBackground(ContextCompat.getDrawable(activity, R.color.white));
            holder.tvWeekDAY.setTextColor(ContextCompat.getColor(activity, R.color.text_gray));

        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=position;
                notifyDataSetChanged();
                String day= oneDayBefore(position);
                calendarFragment.loadDailyTimeTables(1,day);
                tvDate.setText(dateFormatted(day));

            }
        });

    }

    @Override
    public int getItemCount() {
        return onHomeWordData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWeekDAY;


        public ViewHolder(View itemView) {
            super(itemView);
            this.tvWeekDAY = itemView.findViewById(R.id.tvWeekDAY);


        }
    }
    String oneDayBefore(int position){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(onHomeWordData.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1); // subtract one day
        Date oneDayBefore = cal.getTime();
        return sdf.format(oneDayBefore);
    }
    String dateFormatted(String day){
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, EEEE");
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = sdf.format(date);
        return formattedDate;

    }
}
