package com.app.b4s.view.HWM.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.app.b4s.TestActivity;
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
    private JSONArray attachment = null;
    private boolean isAttached = false;
    private JSONArray correctAnswers = null;
    private JSONObject questions = null;
    private JSONObject questionsResponse = null;
    private JSONObject answer = null;
    private String title = "";
    private Boolean A, B, C, D;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String dat;
    ArrayList<JSONObject> jsonArrayList = new ArrayList<>();
    ;
    JSONObject test;

    private String type, date, titleSubject, descriptin, totalMark, optainedMark, subject, description;

    Activity activity;

    private ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions);
        rvQuestions = binding.rvQuestion;
        calendar = Calendar.getInstance();
        activity = this;
        session = new Session(activity);
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        type = intent.getStringExtra(Constant.TYPE);
        date = intent.getStringExtra(Constant.DATE);
        titleSubject = intent.getStringExtra(Constant.SUBJECT);
        description = intent.getStringExtra(Constant.DESCRIPTIO);
        if (type.equals(Constant.REVIEW) || type.equals(Constant.COMPLETED)) {
            setDisable();
        }
        activiytResult();
        binding.tvDate.setText(date);
        binding.tvTitle.setText(description);
        binding.tvSubject.setText(titleSubject + " | ");
        setQuestions(0);
        binding.ivOpenFiles.setOnClickListener(view -> doSelectionProcess());



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

    private void doSelectionProcess() {
        if (ActivityCompat.checkSelfPermission(
                QuestionsActivity.this,
                Manifest.permission
                        .READ_EXTERNAL_STORAGE)
                != PackageManager
                .PERMISSION_GRANTED) {
            // When permission is not granted
            // Result permission
            ActivityCompat.requestPermissions(
                    QuestionsActivity.this,
                    new String[]{
                            Manifest.permission
                                    .READ_EXTERNAL_STORAGE},
                    1);
        } else {
            // When permission is granted
            // Create method
            selectPDF();
        }
    }

    private void selectPDF() {
        // Initialize intent
        Intent intent
                = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);
    }

    private void activiytResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                result -> {
                    // Initialize result data
                    Intent data = result.getData();
                    // check condition
                    if (data != null) {
                        // When data is not equal to empty
                        // Get PDf uri
                        Uri sUri = data.getData();
                        // set Uri on text view
                        String tvUri = Html.fromHtml(
                                "<big><b>PDF Uri</b></big><br>"
                                        + sUri).toString();
                        binding.ivOpenFiles.setVisibility(View.GONE);
                        binding.ivFileSuccess.setVisibility(View.VISIBLE);

                        // Get PDF path
                        String sPath = sUri.getPath();
                        // Set path on text view
                        String tvPath;
                        tvPath = Html.fromHtml(
                                "<big><b>PDF Path</b></big><br>"
                                        + sPath).toString();
                    }
                });
    }

    private void pendingFlow() {
        if (isAttached) {
            setBackground = setBackground + 1;
            isAttached = false;
            binding.llUploadPdf.setVisibility(View.GONE);
            binding.llCheckBoxes.setVisibility(View.VISIBLE);
            if (i > setBackground) {
                setQuestions(setBackground);
                questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, activity);
                rvQuestions.setAdapter(questionsCountAdapter);
                questionsCountAdapter.notifyDataSetChanged();
            } else {
                finishQuestion(jsonArrayList);
            }
        } else {
            if (!(A == null && B == null && C == null && D == null)) {
                setBackground = setBackground + 1;

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
                    test = new JSONObject();
                    test.put(Constant.QUESTION_ID, session.getData(Constant.QUESTIONS_ID));
                    test.put(Constant.ANSWERS, ans);
                    String explanation = "";
                    if (ans.equals("A")) {
                        explanation = session.getData(Constant.A_VALUE);
                    } else if (ans.equals("B")) {
                        explanation = session.getData(Constant.B_VALUE);
                    } else if (ans.equals("C")) {
                        explanation = session.getData(Constant.C_VALUE);
                    } else if (ans.equals("D")) {
                        explanation = session.getData(Constant.D_VALUE);
                    }
                    test.put("explanation", explanation);
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
    }

    private void finishQuestion(ArrayList<JSONObject> answersList) {

        JSONArray allAnswers = new JSONArray();
        JSONObject requestBody = new JSONObject();


        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dat = dateFormat.format(calendar.getTime());

        try {
            requestBody.put(Constant.STUDENT_ID, session.getData(Constant.STUDENT_ID));
            requestBody.put(Constant.SUBMITTED_ON, dat);
            for (int i = 0; i < answersList.size(); i++) {
                // create a JSON object for each answer
                JSONObject answers = new JSONObject();
                JSONObject answer = new JSONObject();
                answers.put("question_id", answersList.get(i).getString("question_id"));
                answer.put(Constant.KEY, answersList.get(i).getString(Constant.ANSWERS));
                answer.put(Constant.EXPLANATION, answersList.get(i).getString(Constant.EXPLANATION));
                answers.put(Constant.ANSWERS, answer);

                // add the answer to the array
                allAnswers.put(answers);
            }
            requestBody.put("answers", allAnswers);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //jsonObject2 is the payload to server here you can use JsonObjectRequest

        String url = "http://143.244.132.170:3001/api/v1/homework/" + session.getData(Constant.HOMEWORD_ID) + "/studentResponse/create";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("STUDENT_RESPONSE", response.toString());
                        //   Toast.makeText(QuestionsActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        Toast.makeText(QuestionsActivity.this, "Success", Toast.LENGTH_SHORT).show();


                        try {
                            //TODO: Handle your response here
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.print(response);

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        String body = "";
                        //get status code here
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
                        if (error.networkResponse.data != null) {
                            try {
                                body = new String(error.networkResponse.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(QuestionsActivity.this, "failed", Toast.LENGTH_SHORT).show();

                    }


                });

        ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);
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
                attachment = new JSONArray();
                String questionCount = String.valueOf(i + 1);
                binding.tvQuestionNumber.setText(count);
                binding.tvResult.setText("Questions" + ":");
                String totQuestion = jsonObject.getString(Constant.TOTAL_QUESTIONS);
                binding.tvMarkDetails.setText(questionCount + "/" + totQuestion);
                session.setData(Constant.QUESTIONS_ID, jsonArray.getJSONObject(i).getString(Constant.ID));
                title = jsonArray.getJSONObject(i).getString(Constant.title);
                binding.tvQuestion.setText(title);
                attachment = jsonArray.getJSONObject(i).getJSONArray("attachments");
                if (attachment.length() >= 1) {
                    attachment = jsonArray.getJSONObject(i).getJSONArray("attachments");
                    isAttached = true;
                } else
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
        if (isAttached) {
            binding.llUploadPdf.setVisibility(View.VISIBLE);

            binding.llCheckBoxes.setVisibility(View.GONE);

        } else {
            for (int j = 0; j < options.length(); j++) {
                JSONObject option = options.getJSONObject(j);
                String key = option.getString(Constant.KEY);
                String value = option.getString(Constant.VALUE);
                if (j == 0) {
                    binding.cbFirstOpt.setVisibility(View.VISIBLE);
                    binding.cbFirstOpt.setText(value);
                    session.setData(Constant.A_VALUE, value);
                }
                if (j == 1) {
                    binding.cbSecondOpt.setVisibility(View.VISIBLE);
                    binding.cbSecondOpt.setText(value);
                    session.setData(Constant.B_VALUE, value);
                }
                if (j == 2) {
                    binding.cbThirdOpt.setVisibility(View.VISIBLE);
                    binding.cbThirdOpt.setText(value);
                    session.setData(Constant.C_VALUE, value);
                }
                if (j == 3) {
                    binding.cbFourthOpt.setVisibility(View.VISIBLE);
                    binding.cbFourthOpt.setText(value);
                    session.setData(Constant.D_VALUE, value);
                }
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