package com.app.b4s.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.utilities.Constant;

import java.util.ArrayList;

public class HomeWorkSubjectAdapter extends RecyclerView.Adapter<HomeWorkSubjectAdapter.ViewHolder> {
    Activity activity;
    ArrayList<HomeWorkSubject> homeWorkSubjects;
    String name;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        name = String.valueOf(homeWorkSubjects.get(position).getSubject().getName());

        holder.tvSubName.setText(name);
        holder.tvOnReview.setText(activity.getString(R.string.onreview_with_dot) + homeWorkSubjects.get(position).getOn_review());
        holder.tvCompleted.setText(activity.getString(R.string.completed_with_dot) + homeWorkSubjects.get(position).getCompleted());
        holder.tvNotStarted.setText(activity.getString(R.string.not_started_with_dot) + homeWorkSubjects.get(position).getNot_started());
        switch (name) {
            case Constant.MATHS:
                holder.subImage.setImageResource(R.drawable.maths_logo);
                break;
            case Constant.SOCIAL:
                holder.subImage.setImageResource(R.drawable.social_logo);
                break;
            case Constant.KANNADA:
                holder.subImage.setImageResource(R.drawable.kannada_logo);
                break;
            case Constant.PHYSICS:
                holder.subImage.setImageResource(R.drawable.physics_logo);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return homeWorkSubjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubName, tvCompleted, tvNotStarted, tvOnReview;
        public ImageView subImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvSubName = itemView.findViewById(R.id.tvSubName);
            this.tvCompleted = itemView.findViewById(R.id.tvCompleted);
            this.tvNotStarted = itemView.findViewById(R.id.tvStarted);
            this.tvOnReview = itemView.findViewById(R.id.tvReview);
            this.subImage = itemView.findViewById(R.id.ivSubImage);
        }
    }
}
