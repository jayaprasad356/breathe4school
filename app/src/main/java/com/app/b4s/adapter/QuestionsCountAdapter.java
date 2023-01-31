
package com.app.b4s.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.Answered;
import com.app.b4s.utilities.DatabaseHelper;
import com.app.b4s.view.HWM.Activity.PositionPicker;

import java.util.ArrayList;

public class QuestionsCountAdapter extends RecyclerView.Adapter<QuestionsCountAdapter.ViewHolder> {
    Activity activity;
    int count;
    int setBackground;
    boolean answred;
    PositionPicker positionPicker;
    ArrayList<Answered> answereds;
    ArrayList<Answered> specificanswereds;

    ArrayList<String> allQuestionIds;
    DatabaseHelper databaseHelper;

    public QuestionsCountAdapter(ArrayList<String> allQuestionIds, ArrayList<Answered> answereds,
                                 int count, int setBackground, Activity activity, PositionPicker positionPicker) {
        this.answereds = answereds;
        this.activity = activity;
        this.count = count;
        this.setBackground = setBackground;
        this.positionPicker = positionPicker;
        this.allQuestionIds = allQuestionIds;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.questions_layout, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int quesNumber = 1 + position;
        databaseHelper=new DatabaseHelper(activity);
        String text = "Q" + quesNumber;
        holder.question.setText(text);

        for (int l = 0; l < answereds.size(); l++) {
            if (allQuestionIds.get(position).equals(answereds.get(l).getQid())) {
                specificanswereds = new ArrayList<>();
                specificanswereds = databaseHelper.getSpecificAnsweredTables(allQuestionIds.get(position));
                if (specificanswereds.get(0).getIsAnswered().equals("1")){
                    holder.question.setBackground(activity.getDrawable(R.drawable.transparent_answred_bg));
                    holder.question.setTextColor(activity.getColor(R.color.primary));
                }
            }
        }
        if (position == setBackground) {
            holder.question.setBackground(activity.getDrawable(R.drawable.question_tv_checked));
            holder.question.setTextColor(activity.getColor(R.color.primary));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionPicker.selectedPosition(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView question;

        public ViewHolder(View itemView) {
            super(itemView);
            this.question = itemView.findViewById(R.id.tvQuestionNo);

        }
    }
}
