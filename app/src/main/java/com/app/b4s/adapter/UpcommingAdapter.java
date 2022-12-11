package com.app.b4s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.StudentNotice;
import com.app.b4s.model.UpCommingData;

import java.util.ArrayList;

public class UpcommingAdapter extends RecyclerView.Adapter<UpcommingAdapter.ViewHolder> {
    Activity activity;
    ArrayList<UpCommingData> upCommingData;

    public UpcommingAdapter(ArrayList<UpCommingData> upCommingData, Activity activity) {
        this.upCommingData = upCommingData;
        this.activity = activity;
    }


    @NonNull
    @Override
    public UpcommingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_classes, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcommingAdapter.ViewHolder holder, int position) {
        holder.teacherName.setText(upCommingData.get(position).name);
        String duriation = String.valueOf(upCommingData.get(position).start_time) + "-" + String.valueOf(upCommingData.get(position).end_time);
        holder.duriation.setText(duriation);
    }


    @Override
    public int getItemCount() {
        return upCommingData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teacherName, duriation, subject;
        public ImageView subjectImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.teacherName = itemView.findViewById(R.id.tvTeacher);
            this.duriation = itemView.findViewById(R.id.tvDuriation);
            this.subjectImage = itemView.findViewById(R.id.ivSubImage);
            this.subject = itemView.findViewById(R.id.tvSubject);
        }
    }
}
