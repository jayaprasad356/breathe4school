package com.app.b4s.adapter.HomepageAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.StudentNotice;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    Activity activity;
    ArrayList<StudentNotice> studentNotices;

    public NoticeAdapter(ArrayList<StudentNotice> sakunaluDatas, Activity activity) {
        this.studentNotices = sakunaluDatas;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.notice_board_lyt, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updates.setText(studentNotices.get(position).getNotice());
    }


    @Override
    public int getItemCount() {
        return studentNotices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView updates;

        public ViewHolder(View itemView) {
            super(itemView);
            this.updates = itemView.findViewById(R.id.updates);
        }
    }
}
