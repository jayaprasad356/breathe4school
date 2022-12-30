package com.app.b4s.view.HWM.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.b4s.R;
import com.app.b4s.adapter.QuestionsCountAdapter;
import com.app.b4s.databinding.ActivityQuestionsBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {
    private ActivityQuestionsBinding binding;
    RecyclerView rvQuestions;
    QuestionsCountAdapter questionsCountAdapter;
    int i, setBackground;
    private Session session;
    private JSONObject jsonObject;
    private JSONObject request;
    private RequestQueue requestQueue;


    private JSONObject homeWorkObject;
    private JSONArray jsonArray = null;
    private JSONArray options = null;
    private JSONArray correctAnswers = null;
    private JSONObject questions = null;
    private JSONObject questionsResponse = null;
    private JSONObject answer = null;
    private String title = "";
    private Boolean A, B, C, D;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String dat;
    private String type, date,titleSubject,descriptin, totalMark, optainedMark, subject, description;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions);
        rvQuestions = binding.rvQuestion;
        calendar = Calendar.getInstance();
        activity = this;
        session = new Session(activity);
        requestQueue=Volley.newRequestQueue(this);
        Intent intent = getIntent();
        type = intent.getStringExtra(Constant.TYPE);
        date = intent.getStringExtra(Constant.DATE);
        titleSubject=intent.getStringExtra(Constant.SUBJECT);
        description=intent.getStringExtra(Constant.DESCRIPTIO);
        if (type.equals(Constant.REVIEW) || type.equals(Constant.COMPLETED)) {
            setDisable();
        }
        binding.tvDate.setText(date);
        binding.tvTitle.setText(description);
        binding.tvSubject.setText(titleSubject + " | ");
        setQuestions(0);


        binding.tvQuestion.setText(title);


        binding.cbFirstOpt.setOnClickListener(view -> {
            if (type.equals(Constant.PENDING)) {
                A = true;
                B = false;
                C = false;
                D = false;
                binding.cbFirstOpt.setChecked(true);
                binding.cbSecondOpt.setChecked(false);
                binding.cbThirdOpt.setChecked(false);
                binding.cbFourthOpt.setChecked(false);
            }
            if (type.equals(Constant.COMPLETED) || type.equals(Constant.REVIEW)) {
                binding.cbFirstOpt.setChecked(true);
            }

        });
        binding.cbSecondOpt.setOnClickListener(view -> {
            if (type.equals(Constant.PENDING)) {
                A = false;
                B = true;
                C = false;
                D = false;
                binding.cbFirstOpt.setChecked(false);
                binding.cbSecondOpt.setChecked(true);
                binding.cbThirdOpt.setChecked(false);
                binding.cbFourthOpt.setChecked(false);
            }
            if (type.equals(Constant.COMPLETED) || type.equals(Constant.REVIEW)) {
                binding.cbSecondOpt.setChecked(true);
            }
        });
        binding.cbThirdOpt.setOnClickListener(view -> {
            if (type.equals(Constant.PENDING)) {
                A = false;
                B = false;
                C = true;
                D = false;
                binding.cbFirstOpt.setChecked(false);
                binding.cbSecondOpt.setChecked(false);
                binding.cbThirdOpt.setChecked(true);
                binding.cbFourthOpt.setChecked(false);
            }
            if (type.equals(Constant.COMPLETED) || type.equals(Constant.REVIEW)) {
                binding.cbThirdOpt.setChecked(true);
            }
        });
        binding.cbFourthOpt.setOnClickListener(view -> {
            if (type.equals(Constant.PENDING)) {
                A = false;
                B = false;
                C = false;
                D = true;
                binding.cbFirstOpt.setChecked(false);
                binding.cbSecondOpt.setChecked(false);
                binding.cbThirdOpt.setChecked(false);
                binding.cbFourthOpt.setChecked(true);
            }
            if (type.equals(Constant.COMPLETED) || type.equals(Constant.REVIEW)) {
                binding.cbFourthOpt.setChecked(true);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvQuestions.setLayoutManager(linearLayoutManager);
        setBackground = 0;
        questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, this);
        rvQuestions.setAdapter(questionsCountAdapter);
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals(Constant.PENDING))
                    pendingFlow();
                else if (type.equals(Constant.REVIEW)) {
                    setBackground = setBackground + 1;
                    if (i > setBackground) {
                        setQuestions(setBackground);
                        questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, activity);
                        rvQuestions.setAdapter(questionsCountAdapter);
                        questionsCountAdapter.notifyDataSetChanged();
                    } else {
                        //finishQuestion(jsonArrayList);
                    }
                } else if (type.equals(Constant.COMPLETED)) {
                    setBackground = setBackground + 1;
                    if (i > setBackground) {
                        setQuestions(setBackground);
                        questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, activity);
                        rvQuestions.setAdapter(questionsCountAdapter);
                        questionsCountAdapter.notifyDataSetChanged();
                    }
                }

            }
        });


    }

    private void pendingFlow() {
        if (!(A == null && B == null && C == null && D == null)) {
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
                test.put(Constant.ANSWERS, ans);
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
        } else
            Toast.makeText(activity, R.string.select_any_option, Toast.LENGTH_SHORT).show();
    }

    private void finishQuestion(ArrayList<JSONObject> answersList) {

        session = new Session(activity);
        String url;
        JSONObject requestBody = new JSONObject();
        JSONArray answers = new JSONArray();



        // url = Constant.FILTER_BY_STUDENT_ID + session.getData(Constant.STUDENT_ID)+"/"+"completed";
        url = Constant.HomeWork_Url + "63ab230d6356fe0dfe1f85ce" + "/" + "studentResponse/create";
        String  jsonString = "";
        try {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dat = dateFormat.format(calendar.getTime());
            requestBody.put(Constant.STUDENT_ID, session.getData(Constant.STUDENT_ID));
            requestBody.put(Constant.SUBMITTED_ON, dat);

            for (int i = 0; i < answersList.size(); i++) {
                // create a JSON object for each answer
                JSONObject answer = new JSONObject();
                answer.put("question_id", answersList.get(i).getString("question_id"));
                answer.put("answer", answersList.get(i).getString("answers"));

                // add the answer to the array
                answers.put(answer);
            }

            requestBody.put(Constant.ANSWERS, answers);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(requestBody);
//        String finalJsonString = jsonString;
//        StringRequest request = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(QuestionsActivity.this, response, Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // handle the error
//                        Toast.makeText(QuestionsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() {
//                String requestBody = finalJsonString;
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    // handle exception
//                    Toast.makeText(QuestionsActivity.this, "un", Toast.LENGTH_SHORT).show();
//                    return null;
//                }
//            }
//        };
//
//// add the request to the request queue
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(request);
        if (ApiConfig.isConnected(activity)) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error
                            if (error instanceof ClientError) {
                                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                                // This is a client error, such as a 4xx HTTP status code
                                // Handle the client error
                            } else {
                                Toast.makeText(activity, "serv", Toast.LENGTH_SHORT).show();
                                // This is a server error, such as a 5xx HTTP status code
                                // Handle the server error
                            }
                        }
                    });

// Add the request to the request queue
            requestQueue.add(jsonObjectRequest);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setQuestions(int i) {


        if (type.equals(Constant.PENDING)) {
            try {
                setUnCheck();
                binding.layoutThisQuestion.setVisibility(View.VISIBLE);
                binding.layoutTotalMarks.setVisibility(View.VISIBLE);
                binding.tvDateStatus.setText(R.string.dead_line);
                jsonObject = new JSONObject(session.getData(Constant.QUESTION_DATA));
                jsonArray = jsonObject.getJSONArray(Constant.QUESTIONS);
                String totalMark = jsonObject.getString(Constant.TOTAL_MARK);
                binding.tvTotalMarknum.setText(totalMark);
                String count = "";
                if (i < 10) {
                    count = String.valueOf(i + 1);
                    count = "0" + count;
                } else {
                    count = String.valueOf(i);
                }

                String questionCount = String.valueOf(i + 1);
                binding.tvQuestionNumber.setText(count);
                binding.tvResult.setText("Questions" + ":");
                String totQuestion = jsonObject.getString(Constant.TOTAL_QUESTIONS);
                binding.tvMarkDetails.setText(questionCount + "/" + totQuestion);
                session.setData(Constant.QUESTIONS_ID, jsonArray.getJSONObject(i).getString(Constant.ID));
                title = jsonArray.getJSONObject(i).getString(Constant.title);
                options = jsonArray.getJSONObject(i).getJSONArray(Constant.OPTIONS);
                System.out.println(options);
                setOptionsForPending();
                this.i = jsonObject.getInt(Constant.TOTAL_QUESTIONS);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equals(Constant.REVIEW)) {
            try {
                jsonObject = new JSONObject(session.getData(Constant.QUESTION_DATA));
                jsonArray = jsonObject.getJSONArray(Constant.ANSWERS);
                questions = jsonArray.getJSONObject(i).getJSONObject(Constant.QUESTION);
                title = questions.getString(Constant.title);
                options = questions.getJSONArray(Constant.OPTIONS);
                answer = jsonArray.getJSONObject(i).getJSONObject(Constant.ANSWER);
                binding.tvOnReview.setVisibility(View.VISIBLE);
                setOptionsForReview(answer);
                this.i = questions.length();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (type.equals(Constant.COMPLETED)) {
            try {
                jsonObject = new JSONObject(session.getData(Constant.QUESTION_DATA));
                jsonArray = jsonObject.getJSONArray(Constant.RESULTS);
                homeWorkObject = jsonObject.getJSONObject(Constant.HOMEWORK);
                questionsResponse = jsonArray.getJSONObject(i).getJSONObject(Constant.QUESTION_RESPONSE);
                answer = questionsResponse.getJSONObject(Constant.ANSWER);
                questions = questionsResponse.getJSONObject(Constant.QUESTION);
                options = questions.getJSONArray(Constant.OPTIONS);
                title = questions.getString(Constant.title);
                totalMark = homeWorkObject.getString(Constant.TOTAL_MARK);
                optainedMark = jsonObject.getString(Constant.OPTAINED_MARK);
                binding.tvMarkDetails.setText(optainedMark + "/" + totalMark);
                correctAnswers = questions.getJSONArray(Constant.CORRECT_ANSWERS);
                setOptionsForCompleted();
                this.i = questionsResponse.length();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void setOptionsForCompleted() throws JSONException {
        setUnCheck();
        removeBackground();
        setDisable();
        int green = Color.parseColor("#45BF55");
        ColorStateList GreencolorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        green
                }
        );
        int red = Color.parseColor("#D40D12");
        ColorStateList RedcolorStateList = new ColorStateList(

                new int[][]{
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        red
                }
        );
        for (int j = 0; j < options.length(); j++) {
            JSONObject option = options.getJSONObject(j);
            String key = option.getString(Constant.KEY);
            String value = option.getString("value");
            //todo Your answer
            if (answer.get(Constant.KEY).equals(Constant.A)) {
                binding.cbFirstOpt.setEnabled(true);
                binding.cbFirstOpt.setChecked(true);
                if (answer.get(Constant.KEY).equals(correctAnswers.getJSONObject(0).get(Constant.OPTION_KEY))) {
                    binding.cbFirstOpt.setButtonTintList(GreencolorStateList);
                    binding.cbFirstOpt.setBackground(activity.getDrawable(R.color.check_box_green));
                } else {
                    binding.cbFirstOpt.setButtonTintList(RedcolorStateList);
                    binding.cbFirstOpt.setBackground(activity.getDrawable(R.color.check_box_red));
                }
            } else if (answer.get(Constant.KEY).equals(Constant.B)) {
                binding.cbSecondOpt.setEnabled(true);
                binding.cbSecondOpt.setChecked(true);
                if (answer.get(Constant.KEY).equals(correctAnswers.getJSONObject(0).get(Constant.OPTION_KEY))) {
                    binding.cbSecondOpt.setButtonTintList(GreencolorStateList);
                    binding.cbSecondOpt.setBackground(activity.getDrawable(R.color.check_box_green));
                } else {
                    binding.cbSecondOpt.setButtonTintList(RedcolorStateList);
                    binding.cbSecondOpt.setBackground(activity.getDrawable(R.color.check_box_red));
                }
            } else if (answer.get(Constant.KEY).equals(Constant.C)) {
                binding.cbThirdOpt.setEnabled(true);
                binding.cbThirdOpt.setChecked(true);
                if (answer.get(Constant.KEY).equals(correctAnswers.getJSONObject(0).get(Constant.OPTION_KEY))) {
                    binding.cbThirdOpt.setButtonTintList(GreencolorStateList);
                    binding.cbThirdOpt.setBackground(activity.getDrawable(R.color.check_box_green));
                } else {
                    binding.cbThirdOpt.setButtonTintList(RedcolorStateList);
                    binding.cbThirdOpt.setBackground(activity.getDrawable(R.color.check_box_red));
                }
            } else if (answer.get(Constant.KEY).equals(Constant.D)) {
                binding.cbFourthOpt.setEnabled(true);
                binding.cbFourthOpt.setChecked(true);
                if (answer.get(Constant.KEY).equals(correctAnswers.getJSONObject(0).get(Constant.OPTION_KEY))) {
                    binding.cbFourthOpt.setButtonTintList(GreencolorStateList);
                    binding.cbFourthOpt.setBackground(activity.getDrawable(R.color.check_box_green));
                } else {
                    binding.cbFourthOpt.setButtonTintList(RedcolorStateList);
                    binding.cbFourthOpt.setBackground(activity.getDrawable(R.color.check_box_red));
                }
            }

            //todo Correct Answer
            if (correctAnswers.getJSONObject(0).get(Constant.OPTION_KEY).equals(Constant.A)) {
                binding.cbFirstOpt.setEnabled(true);
                binding.cbFirstOpt.setChecked(true);
                binding.cbFirstOpt.setBackground(activity.getDrawable(R.color.check_box_green));
                binding.cbFirstOpt.setButtonTintList(GreencolorStateList);

            } else if (correctAnswers.getJSONObject(0).get(Constant.OPTION_KEY).equals(Constant.B)) {
                binding.cbSecondOpt.setEnabled(true);
                binding.cbSecondOpt.setChecked(true);
                binding.cbSecondOpt.setBackground(activity.getDrawable(R.color.check_box_green));
                binding.cbSecondOpt.setButtonTintList(GreencolorStateList);

            } else if (correctAnswers.getJSONObject(0).get(Constant.OPTION_KEY).equals(Constant.C)) {
                binding.cbThirdOpt.setEnabled(true);
                binding.cbThirdOpt.setChecked(true);
                binding.cbThirdOpt.setBackground(activity.getDrawable(R.color.check_box_green));
                binding.cbThirdOpt.setButtonTintList(GreencolorStateList);

            } else if (correctAnswers.getJSONObject(0).get(Constant.OPTION_KEY).equals(Constant.D)) {
                binding.cbFourthOpt.setEnabled(true);
                binding.cbFourthOpt.setChecked(true);
                binding.cbFourthOpt.setBackground(activity.getDrawable(R.color.check_box_green));
                binding.cbFourthOpt.setButtonTintList(GreencolorStateList);
            }

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
    }


    private void setOptionsForPending() throws JSONException {
        for (int j = 0; j < options.length(); j++) {
            JSONObject option = options.getJSONObject(j);
            String key = option.getString(Constant.KEY);
            String value = option.getString(Constant.VALUE);
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
    }

    private void setOptionsForReview(JSONObject answer) throws JSONException {
        setUnCheck();
        setDisable();
        for (int j = 0; j < options.length(); j++) {
            JSONObject option = options.getJSONObject(j);
            String key = option.getString(Constant.KEY);
            String value = option.getString(Constant.VALUE);
            if (answer.get(Constant.KEY).equals(Constant.A)) {
                binding.cbFirstOpt.setEnabled(true);
                binding.cbFirstOpt.setChecked(true);
            } else if (answer.get(Constant.KEY).equals(Constant.B)) {
                binding.cbSecondOpt.setEnabled(true);
                binding.cbSecondOpt.setChecked(true);
            } else if (answer.get(Constant.KEY).equals(Constant.C)) {
                binding.cbThirdOpt.setEnabled(true);
                binding.cbThirdOpt.setChecked(true);
            } else if (answer.get(Constant.KEY).equals(Constant.D)) {
                binding.cbFourthOpt.setEnabled(true);
                binding.cbFourthOpt.setChecked(true);
            }
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
    }

    private void setUnCheck() {
        binding.cbFirstOpt.setChecked(false);
        binding.cbSecondOpt.setChecked(false);
        binding.cbThirdOpt.setChecked(false);
        binding.cbFourthOpt.setChecked(false);
    }

    private void removeBackground() {
        binding.cbFirstOpt.setBackground(null);
        binding.cbSecondOpt.setBackground(null);
        binding.cbThirdOpt.setBackground(null);
        binding.cbFourthOpt.setBackground(null);
        binding.cbFirstOpt.setButtonTintList(null);
    }

    private void setDisable() {
        binding.cbFirstOpt.setEnabled(false);
        binding.cbSecondOpt.setEnabled(false);
        binding.cbThirdOpt.setEnabled(false);
        binding.cbFourthOpt.setEnabled(false);
    }

}