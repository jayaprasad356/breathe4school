package com.app.b4s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.model.StudentNotice;

import java.util.ArrayList;

public class HomeWorkSubjectAdapter extends RecyclerView.Adapter<HomeWorkSubjectAdapter.ViewHolder> {
    Activity activity;
    ArrayList<HomeWorkSubject> homeWorkSubjects;

    public HomeWorkSubjectAdapter(ArrayList<HomeWorkSubject> homeWorkSubjects, Activity activity) {
        this.homeWorkSubjects = homeWorkSubjects;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_subject, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSubName.setText(homeWorkSubjects.get(position).getSub_name());
    }


    @Override
    public int getItemCount() {
        return homeWorkSubjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvSubName = itemView.findViewById(R.id.tvSubName);
        }
    }
}
