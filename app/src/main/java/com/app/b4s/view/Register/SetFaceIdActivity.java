package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.b4s.R;

import org.w3c.dom.Text;

public class SetFaceIdActivity extends AppCompatActivity {

    ImageButton ibBackBtn;
    TextView tvSkipFaceid;

    Boolean Skip_FaceID_tv = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_face_id);


        ibBackBtn = findViewById(R.id.ibBackBtn);
        tvSkipFaceid = findViewById(R.id.tvSkipFaceid);


        Skip_FaceID_tv = getIntent().getExtras().getBoolean("SkipFaceId");

        ibBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (Skip_FaceID_tv == true){

            tvSkipFaceid.setVisibility(View.VISIBLE);

        }

        else
        {
            tvSkipFaceid.setVisibility(View.GONE);

        }


    }
}