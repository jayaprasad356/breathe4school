package com.app.b4s.view.HWM.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.b4s.R;
import com.app.b4s.adapter.QuestionsCountAdapter;
import com.app.b4s.databinding.ActivityQuestionsBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {
    private ActivityQuestionsBinding binding;
    RecyclerView rvQuestions;
    QuestionsCountAdapter questionsCountAdapter;
    int i, setBackground;
    private Session session;
    private JSONObject jsonObject;
    private JSONArray jsonArray = null;
    private JSONArray options = null;
    private String title = "";
    private Boolean A, B, C, D;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions);
        rvQuestions = binding.rvQuestion;
        activity = this;
        session = new Session(activity);
        setQuestions(0);
        binding.tvQuestion.setText(title);
        binding.cbFirstOpt.setOnClickListener(view -> {
            A = true;
            B = false;
            C = false;
            D = false;
            binding.cbFirstOpt.setChecked(true);
            binding.cbSecondOpt.setChecked(false);
            binding.cbThirdOpt.setChecked(false);
            binding.cbFourthOpt.setChecked(false);
        });
        binding.cbSecondOpt.setOnClickListener(view -> {
            A = false;
            B = true;
            C = false;
            D = false;
            binding.cbFirstOpt.setChecked(false);
            binding.cbSecondOpt.setChecked(true);
            binding.cbThirdOpt.setChecked(false);
            binding.cbFourthOpt.setChecked(false);
        });

        binding.cbThirdOpt.setOnClickListener(view -> {
            A = false;
            B = false;
            C = true;
            D = false;
            binding.cbFirstOpt.setChecked(false);
            binding.cbSecondOpt.setChecked(false);
            binding.cbThirdOpt.setChecked(true);
            binding.cbFourthOpt.setChecked(false);
        });

        binding.cbFourthOpt.setOnClickListener(view -> {
            A = false;
            B = false;
            C = false;
            D = true;
            binding.cbFirstOpt.setChecked(false);
            binding.cbSecondOpt.setChecked(false);
            binding.cbThirdOpt.setChecked(false);
            binding.cbFourthOpt.setChecked(true);
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvQuestions.setLayoutManager(linearLayoutManager);
        setBackground = 0;
        questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, this);
        rvQuestions.setAdapter(questionsCountAdapter);
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground = setBackground + 1;

                ArrayList<JSONObject> jsonArrayList = new ArrayList<>();
                JSONObject test = new JSONObject();

                String ans = "";
                if (A) {
                    ans = "A";
                } else if (B) {
                    ans = "B";
                } else if (C) {
                    ans = "C";
                } else if (D) {
                    ans = "D";
                }
                try {
                    test.put("question_id", session.getData(Constant.QUESTIONS_ID));
                    test.put("answer", ans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArrayList.add(test);


                if (i > setBackground) {
                    setQuestions(setBackground);
                    questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, activity);
                    rvQuestions.setAdapter(questionsCountAdapter);
                    questionsCountAdapter.notifyDataSetChanged();
                } else {
                    finishQuestion(jsonArrayList);
                }

            }
        });


    }

    private void finishQuestion(ArrayList<JSONObject> answersList) {

        session = new Session(activity);
        String url;
        JSONObject jsonObject = new JSONObject();

        // url = Constant.FILTER_BY_STUDENT_ID + session.getData(Constant.STUDENT_ID)+"/"+"completed";
        url = Constant.HomeWork_Url + session.getData(Constant.HOMEWORD_ID) + "/" + "/studentResponse/create";
        try {
            jsonObject.put(Constant.STUDENT_ID, session.getData(Constant.STUDENT_ID));
            jsonObject.put(Constant.SUBMITTED_ON, "23/12/2022");
            jsonObject.put("answers",answersList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);

        if (ApiConfig.isConnected(activity)) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject, response -> {
                        Log.d("TEST_RES", response.toString());
                        try {
                            if (response.getBoolean(Constant.STATUS)) {
                                Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.print(response);

                    }, error -> {
                        // TODO: Handle error
                        error.printStackTrace();
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                    });
            ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);
        }
    }

    private void setQuestions(int i) {
        try {
            jsonObject = new JSONObject(session.getData(Constant.QUESTION_DATA));
            jsonArray = jsonObject.getJSONArray("questions");
            session.setData(Constant.QUESTIONS_ID, jsonArray.getJSONObject(i).getString(Constant.ID));
            title = jsonArray.getJSONObject(i).getString("title");
            options = jsonArray.getJSONObject(i).getJSONArray("options");
            System.out.println(options);
            for (int j = 0; j < options.length(); j++) {
                JSONObject option = options.getJSONObject(j);
                String key = option.getString("key");
                String value = option.getString("value");
                if (j == 0) {
                    binding.cbFirstOpt.setVisibility(View.VISIBLE);
                    binding.cbFirstOpt.setText(value);
                }
                if (j == 1) {
                    binding.cbSecondOpt.setVisibility(View.VISIBLE);
                    binding.cbSecondOpt.setText(value);
                }
                if (j == 2) {
                    binding.cbThirdOpt.setVisibility(View.VISIBLE);
                    binding.cbThirdOpt.setText(value);
                }
                if (j == 3) {
                    binding.cbFourthOpt.setVisibility(View.VISIBLE);
                    binding.cbFourthOpt.setText(value);
                }
            }
            this.i = jsonObject.getInt("total_questions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}