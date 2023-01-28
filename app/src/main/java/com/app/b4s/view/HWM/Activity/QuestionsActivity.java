package com.app.b4s.view.HWM.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.b4s.R;
import com.app.b4s.VolleyMultipartRequest;
import com.app.b4s.VolleySingleton;
import com.app.b4s.adapter.QuestionsCountAdapter;
import com.app.b4s.databinding.ActivityQuestionsBinding;
import com.app.b4s.model.Attach;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.utilities.DatabaseHelper;
import com.app.b4s.utilities.ProgressDisplay;
import com.bumptech.glide.Glide;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
    private JSONObject request;
    private RequestQueue requestQueue;
    PositionPicker positionPicker;
    byte[] fileBytes = null;
    DatabaseHelper databaseHelper;

    private JSONObject homeWorkObject;
    private JSONArray jsonArray = null;
    private JSONArray options = null;
    private JSONArray attachment = null;
    private JSONArray ansAttachment = null;
    private JSONArray attach = null;
    private boolean isAttached = false;
    private JSONArray correctAnswers = null;
    private JSONObject questions = null;
    private JSONObject questionsResponse = null;
    private JSONObject answer = null;
    private String title = "";
    private Boolean A = false, B = false, C = false, D = false;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String dat;
    ArrayList<JSONObject> jsonArrayList = new ArrayList<>();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static int PICK_FILE_REQUEST = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    ArrayList<Attach> answeredAttaches;

    JSONObject test;
    MediaController mediaController;
    private String type, date, titleSubject, descriptin, totalMark, optainedMark, subject, description;

    Activity activity;
    ProgressDisplay progressDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions);
        rvQuestions = binding.rvQuestion;
        calendar = Calendar.getInstance();
        activity = this;
        databaseHelper = new DatabaseHelper(this);
        progressDisplay = new ProgressDisplay(activity);

        session = new Session(activity);
        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());
        positionPicker = position -> {
            setQuestions(position);
            questionsCountAdapter = new QuestionsCountAdapter(i, position, activity, positionPicker);
            rvQuestions.setAdapter(questionsCountAdapter);
            questionsCountAdapter.notifyDataSetChanged();
        };
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        type = intent.getStringExtra(Constant.TYPE);
        date = intent.getStringExtra(Constant.DATE);
        titleSubject = intent.getStringExtra(Constant.SUBJECT);
        description = intent.getStringExtra(Constant.DESCRIPTIO);
        if (type.equals(Constant.REVIEW) || type.equals(Constant.COMPLETED)) {
            setDisable();
        }

        binding.tvDate.setText(date);
        binding.tvTitle.setText(description);
        binding.tvSubject.setText(titleSubject + " | ");
        setQuestions(0);
        binding.ivOpenFiles.setOnClickListener(view -> doSelectionProcess());
        binding.ivPdf.setOnClickListener(view -> {
            String string = session.getData(Constant.LINK);
            showPdf(string);
        });
        binding.LLOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    // Permission has already been granted
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }


            }
        });
        binding.ivAnsPdf.setOnClickListener(view -> {
            String string = session.getData(Constant.ANSWER_PDF_LINK);
            showPdf(string);
        });

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
        questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, this, positionPicker);
        rvQuestions.setAdapter(questionsCountAdapter);
        binding.btnNext.setOnClickListener(view -> {
            binding.videoView.stopPlayback();
            binding.LLOpenCamera.setVisibility(View.VISIBLE);
            binding.ivCameraSuccess.setVisibility(View.GONE);
            if (mediaController != null)
                mediaController.hide();
            if (type.equals(Constant.PENDING)) {
                pendingFlow();
            } else if (type.equals(Constant.REVIEW)) {
                setBackground = setBackground + 1;
                if (i > setBackground) {
                    setQuestions(setBackground);
                    questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, activity, positionPicker);
                    rvQuestions.setAdapter(questionsCountAdapter);
                    questionsCountAdapter.notifyDataSetChanged();
                } else {
                    //finishQuestion(jsonArrayList);
                }
            } else if (type.equals(Constant.COMPLETED)) {
                setBackground = setBackground + 1;
                if (i > setBackground) {
                    setQuestions(setBackground);
                    questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, activity, positionPicker);
                    rvQuestions.setAdapter(questionsCountAdapter);
                    questionsCountAdapter.notifyDataSetChanged();
                }
            }

        });


    }

    private void showPdf(String string) {
        String link = string.substring(1);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("http://143.244.132.170:3001" + link));
        startActivity(browserIntent);
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
            selectFile();
        }
    }

    private void selectFile() {
        // Create an Intent to open the file picker
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

// Start the file picker activity
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }


    @SuppressLint("Range")
    private String getFilePath(Uri fileUri) {
        String filePath = null;
        if (fileUri != null) {
            //String[] projection = {MediaStore.Files.FileColumns.DATA};
            Cursor cursor = activity.getContentResolver().query(fileUri, null, null, null, null);
            try {

                if (cursor != null && cursor.moveToFirst()) {
                    filePath = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
//                    filePath = cursor.getString(columnIndex);
//                    cursor.close();
                }
            } finally {
                cursor.close();
            }
            if (filePath == null) {
                filePath = fileUri.getPath();
                int cutt = filePath.lastIndexOf('/');
                if (cutt != -1) {
                    filePath = fileUri.getLastPathSegment();

                    // filePath=filePath.substring(cutt+1);
                }
            }
        }
        return filePath;
    }

    private String getPath(final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            // MediaStore (and general)
            return getForApi19(uri);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @TargetApi(19)
    private String getForApi19(Uri uri) {
        //  Log.e(, "+++ API 19 URI :: " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // Log.e(tag, "+++ Document URI");
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                //  Log.e(tag, "+++ External Document URI");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    // Log.e(tag, "+++ Primary External Document URI");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                //  Log.e(tag, "+++ Downloads External Document URI");
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                // Log.e(tag, "+++ Media Document URI");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    // Log.e(tag, "+++ Image Media Document URI");
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    //  Log.e(tag, "+++ Video Media Document URI");
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    //Log.e(tag, "+++ Audio Media Document URI");
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else if ("document".equals(type))
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Log.e(tag, "+++ No DOCUMENT URI :: CONTENT ");

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //  Log.e(tag, "+++ No DOCUMENT URI :: FILE ");
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public String getPDFPath(Uri uri) {

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private void pendingFlow() {
        String qid = "";
        String attach_Link = "";
        String explanation = "";

        if (isAttached) {
            attach = new JSONArray();
            setBackground = setBackground + 1;
            isAttached = false;
            binding.llUploadPdf.setVisibility(View.GONE);
            binding.llCheckBoxes.setVisibility(View.VISIBLE);
            test = new JSONObject();
            attach.put(session.getData(Constant.PDF_UPLOAD_LINK));

            try {
                qid = session.getData(Constant.QUESTIONS_ID);
                attach_Link = session.getData(Constant.PDF_UPLOAD_LINK);
                test.put(Constant.QUESTION_ID, session.getData(Constant.QUESTIONS_ID));
                test.put(Constant.ATTACHMENTS, attach);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            databaseHelper.AddAttachQuestion(qid, attach_Link, "", "");
            jsonArrayList.add(test);
            session.setData(Constant.PDF_UPLOAD_LINK, "");

            if (i > setBackground) {
                setSubmitText(i, setBackground);
                setQuestions(setBackground);
                questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, activity, positionPicker);
                rvQuestions.setAdapter(questionsCountAdapter);
                questionsCountAdapter.notifyDataSetChanged();
            } else {
                finishQuestion(jsonArrayList);
            }
        } else {
//            if (!(A == null && B == null && C == null && D == null)) {
            setBackground = setBackground + 1;

            String ans = "";
            if (binding.cbFirstOpt.isChecked()) {
                ans = "A";
            } else if (binding.cbSecondOpt.isChecked()) {
                ans = "B";
            } else if (binding.cbThirdOpt.isChecked()) {
                ans = "C";
            } else if (binding.cbFourthOpt.isChecked()) {
                ans = "D";
            }
//            if (A) {
//                ans = "A";
//            } else if (B) {
//                ans = "B";
//            } else if (C) {
//                ans = "C";
//            } else if (D) {
//                ans = "D";
//            }
            try {
                test = new JSONObject();
                qid = session.getData(Constant.QUESTIONS_ID);
                test.put(Constant.QUESTION_ID, session.getData(Constant.QUESTIONS_ID));
                test.put(Constant.ANSWERS, ans);
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

            databaseHelper.AddAttachQuestion(qid, "", ans, explanation);
            jsonArrayList.add(test);

            if (i > setBackground) {
                setSubmitText(i, setBackground);
                setQuestions(setBackground);

                questionsCountAdapter = new QuestionsCountAdapter(i, setBackground, activity, positionPicker);
                rvQuestions.setAdapter(questionsCountAdapter);
                questionsCountAdapter.notifyDataSetChanged();
            } else {
                finishQuestion(jsonArrayList);
            }
//            } else
//                Toast.makeText(activity, R.string.select_any_option, Toast.LENGTH_SHORT).show();
        }
    }

    private void setSubmitText(int totalCount, int setBackground) {
        int temp = setBackground + 1;
        if (temp == totalCount) {
            binding.btnNext.setText("Submit");
        }
    }

    private void finishQuestion(ArrayList<JSONObject> answersList) {

        JSONArray allAnswers = new JSONArray();
        JSONObject requestBody = new JSONObject();


        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dat = dateFormat.format(calendar.getTime());

        try {
            requestBody.put(Constant.STUDENT_ID, session.getData(Constant.STUDENT_ID));
            requestBody.put(Constant.SUBMITTED_ON, dat);
            for (int i = 0; i < answersList.size(); i++) {
                // create a JSON object for each answer
                JSONObject answers = new JSONObject();
                JSONObject answer = new JSONObject();

                if (answersList.get(i).has(Constant.ANSWERS)) {
                    answers.put("question_id", answersList.get(i).getString("question_id"));
                    answer.put(Constant.KEY, answersList.get(i).getString(Constant.ANSWERS));
                    answer.put(Constant.EXPLANATION, answersList.get(i).getString(Constant.EXPLANATION));
                    answers.put(Constant.ANSWERS, answer);
                    // add the answer to the array
                    allAnswers.put(answers);
                }
                if (answersList.get(i).has(Constant.ATTACHMENTS))
                    allAnswers.put(answersList.get(i));

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout_activity, null);
                        builder.setView(customLayout);

                        Button btnOk = customLayout.findViewById(R.id.btnOk);
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(activity, HomeWorkManagementActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog dialog
                                = builder.create();
                        dialog.show();


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
                        //String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
                        if (error.networkResponse.data != null) {
                            try {
                                body = new String(error.networkResponse.data, "UTF-8");

                                String message = null;
                                try {
                                    JSONObject response = new JSONObject(body);
                                    message = response.getString(Constant.MESSAGE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(message);

                                Toast.makeText(QuestionsActivity.this, message, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

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
                binding.tvAnswered.setVisibility(View.GONE);
                binding.tvDateStatus.setText(R.string.dead_line);
                jsonObject = new JSONObject(session.getData(Constant.QUESTION_DATA));
                jsonArray = jsonObject.getJSONArray(Constant.QUESTIONS);
                session.setData(Constant.QUESTIONS_ID, jsonArray.getJSONObject(i).getString(Constant.ID));
                answeredAttaches = databaseHelper.getAnsweredQuestion(session.getData(Constant.QUESTIONS_ID));
                System.out.println(answeredAttaches);

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
                title = jsonArray.getJSONObject(i).getString(Constant.title);
                binding.tvQuestion.setText(title);
                binding.imageLayout.setVisibility(View.GONE);
                binding.vidoLayout.setVisibility(View.GONE);
                binding.rlPdfView.setVisibility(View.GONE);
                attachment = jsonArray.getJSONObject(i).getJSONArray(Constant.ATTACHMENTS);
                if (attachment.length() >= 1) {
                    binding.ivOpenFiles.setVisibility(View.VISIBLE);
                    binding.ivFileSuccess.setVisibility(View.GONE);
                    if (answeredAttaches.size() > 0)
                        if (!(answeredAttaches.get(0).getAttach_link().isEmpty()))
                            binding.tvAnswered.setVisibility(View.VISIBLE);
                    attachment = jsonArray.getJSONObject(i).getJSONArray(Constant.ATTACHMENTS);
                    isAttached = true;
                    String picUrl = Constant.SERVER + attachment.get(0);
                    String url = picUrl.replace("./", "/");
                    if (!(url.contains(".pdf"))) {

                        if (url.contains(".png") || url.contains(".jpeg")) {
                            binding.imageLayout.setVisibility(View.VISIBLE);
                            Glide.with(this)
                                    .load(url) // image url
                                    .into(binding.imageView);
                            binding.imageLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showImagePopup(url);

                                }
                            });
                        } else {
                            binding.vidoLayout.setVisibility(View.VISIBLE);
                            VideoView videoView = binding.videoView;
                            String videoUrl = url;
                            Uri uri = Uri.parse(videoUrl);
                            videoView.setVideoURI(uri);
                            mediaController = new MediaController(this);

                            // sets the anchor view
                            // anchor view for the videoView
                            mediaController.setAnchorView(videoView);

                            // sets the media player to the videoView
                            mediaController.setMediaPlayer(videoView);

                            // sets the media controller to the videoView
                            videoView.setMediaController(mediaController);

                            // starts the video
                            //videoView.start();
                            binding.vidoLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showVideoPopup(videoUrl);
                                }
                            });
                        }
                    } else if (url.contains(".pdf")) {
                        binding.rlPdfView.setVisibility(View.VISIBLE);
                        session.setData(Constant.LINK, attachment.getString(0));


                    }
                } else {
                    options = jsonArray.getJSONObject(i).getJSONArray(Constant.OPTIONS);
                    isAttached = false;
                }
                System.out.println(options);
                setOptionsForPending();
                this.i = jsonObject.getInt(Constant.TOTAL_QUESTIONS);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equals(Constant.REVIEW)) {
            try {
                attachment = new JSONArray();
                ansAttachment = new JSONArray();
                jsonObject = new JSONObject(session.getData(Constant.QUESTION_DATA));
                jsonArray = jsonObject.getJSONArray(Constant.ANSWERS);
                questions = jsonArray.getJSONObject(i).getJSONObject(Constant.QUESTION);
                title = questions.getString(Constant.title);
                binding.imageLayout.setVisibility(View.GONE);
                binding.answerImageLayout.setVisibility(View.GONE);
                binding.vidoLayout.setVisibility(View.GONE);
                binding.rlPdfView.setVisibility(View.GONE);
                binding.rlAnsPdfView.setVisibility(View.GONE);
                if (questions.has(Constant.OPTIONS)) {
                    binding.rlPdfView.setVisibility(View.GONE);
                    binding.llCheckBoxes.setVisibility(View.VISIBLE);
                    options = questions.getJSONArray(Constant.OPTIONS);
                }
                if (jsonArray.getJSONObject(i).has(Constant.ANSWER))
                    answer = jsonArray.getJSONObject(i).getJSONObject(Constant.ANSWER);
                //Answer Attachment
                if (jsonArray.getJSONObject(i).has("attachments")) {
                    binding.llCheckBoxes.setVisibility(View.GONE);
                    ansAttachment = jsonArray.getJSONObject(i).getJSONArray("attachments");
                    if (ansAttachment.length() != 0) {
                        String responseUrl = Constant.SERVER + ansAttachment.get(0);
                        String url = responseUrl.replace("./", "/");
                        if (!(url.contains(".pdf"))) {
                            if (url.contains(".png") || url.contains(".jpeg") || url.contains("image")) {
                                binding.answerImageLayout.setVisibility(View.VISIBLE);
                                Glide.with(this)
                                        .load(url) // image url
                                        .into(binding.answerImageView);
                                binding.answerImageLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showImagePopup(url);
                                    }
                                });
                            } else {
                                binding.vidoLayout.setVisibility(View.VISIBLE);
                                VideoView videoView = binding.videoView;
                                String videoUrl = url;
                                Uri uri = Uri.parse(videoUrl);
                                videoView.setVideoURI(uri);
                                mediaController = new MediaController(this);

                                // sets the anchor view
                                // anchor view for the videoView
                                mediaController.setAnchorView(videoView);

                                // sets the media player to the videoView
                                mediaController.setMediaPlayer(videoView);

                                // sets the media controller to the videoView
                                videoView.setMediaController(mediaController);

                                // starts the video
                                //  videoView.start();
                                binding.vidoLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showVideoPopup(videoUrl);
                                    }
                                });
                            }
                        } else {
                            binding.rlAnsPdfView.setVisibility(View.VISIBLE);
                            session.setData(Constant.ANSWER_PDF_LINK, ansAttachment.getString(0));
                        }
                    }
                }
                //Questions Attachment
                if (questions.has(Constant.ATTACHMENTS)) {
                    attachment = (JSONArray) questions.get(Constant.ATTACHMENTS);
                    if (attachment.length() != 0) {
                        Toast.makeText(this, "found", Toast.LENGTH_SHORT).show();
                        String responseUrl = Constant.SERVER + attachment.get(0);
                        String url = responseUrl.replace("./", "/");
                        if (!(url.contains(".pdf"))) {

                            if (url.contains(".png") || url.contains(".jpeg")) {
                                binding.imageLayout.setVisibility(View.VISIBLE);
                                Glide.with(this)
                                        .load(url) // image url
                                        .into(binding.imageView);
                            } else {
                                binding.vidoLayout.setVisibility(View.VISIBLE);
                                VideoView videoView = binding.videoView;
                                String videoUrl = url;
                                Uri uri = Uri.parse(videoUrl);
                                videoView.setVideoURI(uri);
                                mediaController = new MediaController(this);

                                // sets the anchor view
                                // anchor view for the videoView
                                mediaController.setAnchorView(videoView);

                                // sets the media player to the videoView
                                mediaController.setMediaPlayer(videoView);

                                // sets the media controller to the videoView
                                videoView.setMediaController(mediaController);

                                // starts the video
                                videoView.start();
                            }
                        } else if (url.contains(".pdf")) {
                            binding.rlPdfView.setVisibility(View.VISIBLE);
                            session.setData(Constant.LINK, attachment.getString(0));
                        }
                    }
                }
                binding.tvOnReview.setVisibility(View.VISIBLE);
                if (answer != null)
                    setOptionsForReview(answer);
                this.i = jsonArray.length();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equals(Constant.COMPLETED)) {
            try {
                attachment = new JSONArray();
                jsonObject = new JSONObject(session.getData(Constant.QUESTION_DATA));
                jsonArray = jsonObject.getJSONArray(Constant.RESULTS);
                homeWorkObject = jsonObject.getJSONObject(Constant.HOMEWORK);
                questionsResponse = jsonArray.getJSONObject(i).getJSONObject(Constant.QUESTION_RESPONSE);
                if (questionsResponse.has(Constant.ANSWER))
                    answer = questionsResponse.getJSONObject(Constant.ANSWER);
                questions = questionsResponse.getJSONObject(Constant.QUESTION);
                if (questions.has(Constant.OPTIONS)) {
                    binding.llCheckBoxes.setVisibility(View.VISIBLE);
                    binding.rlPdfView.setVisibility(View.GONE);
                    options = questions.getJSONArray(Constant.OPTIONS);
                } else {
                    binding.llCheckBoxes.setVisibility(View.GONE);
                }
                if (jsonArray.getJSONObject(i).has(Constant.ATTACHMENTS)) {
                    attachment = jsonArray.getJSONObject(i).getJSONArray(Constant.ATTACHMENTS);
                    if (attachment.length() >= 1) {
                        binding.rlPdfView.setVisibility(View.VISIBLE);
                        session.setData(Constant.LINK, attachment.getString(0));
                    }
                }
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

    private void showVideoPopup(String videoUrl) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_video);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(150, 128, 128, 128)));
        dialog.getWindow().setDimAmount(0);

        VideoView videoView = dialog.findViewById(R.id.video_view);
        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);

        mediaController = new MediaController(this);

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoView);

        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoView);

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController);
        // starts the video
        videoView.start();
        dialog.show();


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
            setVisibility();
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
            if (answeredAttaches.size() > 0)
                if (!(answeredAttaches.get(0).getAnswer().isEmpty())) {
                    String ans = answeredAttaches.get(0).getAnswer();
                    if (ans.equals("A")) {
                        binding.cbFirstOpt.setChecked(true);
                    } else if (ans.equals("B")) {
                        binding.cbSecondOpt.setChecked(true);
                    } else if (ans.equals("C")) {
                        binding.cbThirdOpt.setChecked(true);
                    } else if (ans.equals("B")) {
                        binding.cbFourthOpt.setChecked(true);
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

    private void setVisibility() {
        binding.llCheckBoxes.setVisibility(View.VISIBLE);
        binding.llUploadPdf.setVisibility(View.GONE);
        binding.cbFirstOpt.setVisibility(View.GONE);
        binding.cbSecondOpt.setVisibility(View.GONE);
        binding.cbThirdOpt.setVisibility(View.GONE);
        binding.cbFourthOpt.setVisibility(View.GONE);
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

    private void saveProfileAccount(Uri fileUri) {

        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            fileBytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentResolver cR = getContentResolver();
        String mimeType = cR.getType(fileUri);
        File file = new File(fileUri.getPath());
        String fileName = file.getName();
        //String url = "http://www.angga-ari.com/api/something/awesome";
        String url = Constant.UPLOADATTACHMENT + session.getData(Constant.STUDENT_ID);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.d("RESPONSE", resultResponse);

                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");

                    session.setData(Constant.PDF_UPLOAD_LINK, String.valueOf(result.get("data")));
                    progressDisplay.hideProgress();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                    if (status.equals(Constant.REQUEST_SUCCESS)) {
//                        // tell everybody you have succed upload image and post strings
//                        Log.i("Messsage", message);
//                    } else {
//                        Log.i("Unexpected", message);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                progressDisplay.hideProgress();
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put(Constant.UploadresponseAttachemnt, new DataPart(fileName, fileBytes, mimeType));
                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            binding.LLOpenCamera.setVisibility(View.GONE);
            binding.ivCameraSuccess.setVisibility(View.VISIBLE);

            Bitmap imageBitmap = (Bitmap) data.getExtras().get(Constant.DATE);
            Uri imageUri = getImageUri(this, imageBitmap);

            progressDisplay.showProgress();
            saveProfileAccount(imageUri);
        }

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {

            if (data != null) {

                Uri sUri = data.getData();
                binding.ivOpenFiles.setVisibility(View.GONE);
                binding.ivFileSuccess.setVisibility(View.VISIBLE);
                progressDisplay.showProgress();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                saveProfileAccount(sUri);

            }
        }

    }

    public Uri getImageUri(Activity context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void showImagePopup(String url) {


        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_image);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(150, 128, 128, 128)));
        dialog.getWindow().setDimAmount(0);
        ImageView dialogImageView = dialog.findViewById(R.id.image_view);
        dialog.show();
        Glide.with(this)
                .load(url) // image url
                .into(dialogImageView);


    }

}