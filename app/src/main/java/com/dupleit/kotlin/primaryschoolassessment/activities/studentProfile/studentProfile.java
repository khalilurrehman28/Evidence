package com.dupleit.kotlin.primaryschoolassessment.activities.studentProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.GridStudentEvidence;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class studentProfile extends AppCompatActivity {
    @BindView(R.id.studentName) TextView studentName;
    @BindView(R.id.studentImage) CircleImageView studentImage;
    @BindView(R.id.studentClass) TextView studentClass;
    @BindView(R.id.studentDOB) TextView studentDOB;
    @BindView(R.id.studentGender) TextView studentGender;
    @BindView(R.id.classSession) TextView classSession;
    @BindView(R.id.studentFatherName) TextView studentFatherName;
    @BindView(R.id.studentMotherName) TextView studentMotherName;
    @BindView(R.id.contactNo) TextView contactNo;
    @BindView(R.id.studentFEmail) TextView studentFEmail;
    @BindView(R.id.studentAddress) TextView studentAddress;
    @BindView(R.id.btnShowEvidence) Button btnShowEvidence;
    @BindView(R.id.btnShowAssessment) Button btnShowAssessment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String sId=getIntent().getStringExtra("studentId");
        String sImage=getIntent().getStringExtra("studentImage");
        final String sName=getIntent().getStringExtra("studentName");
        String sGender=getIntent().getStringExtra("studentGender");
        String sClass=getIntent().getStringExtra("studentClass");
        String sDOB=getIntent().getStringExtra("studentDOB");
        String sclassSession=getIntent().getStringExtra("classSession");
        String sFName=getIntent().getStringExtra("studentFName");
        String sMName=getIntent().getStringExtra("studentMName");
        String sContact=getIntent().getStringExtra("studentContactPrimary");
        String sEmail=getIntent().getStringExtra("studentEmail");
        String sAddress=getIntent().getStringExtra("studentAddress");

        if (!sImage.equals("")){
            Log.e("imageurl",""+sImage);
            Glide.with(this).load(Utils.webUrlHome+sImage).into(studentImage);
        }

        if (!sName.equals("")){
            studentName.setText(sName);
        }
        if (!sClass.equals("")){
            studentClass.setText(sClass);
        }
        if (!sDOB.equals("")){
            studentDOB.setText(sDOB);
        }
        if (!sGender.equals("")){
            studentGender.setText(sGender);
        }
        if (!sclassSession.equals("")){
            classSession.setText(sclassSession);
        }
        if (!sFName.equals("")){
            studentFatherName.setText(sFName);
        }
        if (!sMName.equals("")){
            studentMotherName.setText(sMName);
        }
        if (!sContact.equals("")){
            contactNo.setText(sContact);
        }
        if (!sEmail.equals("")){
            studentFEmail.setText(sEmail);
        }
        if (!sAddress.equals("")){
            studentAddress.setText(sAddress);
        }

        btnShowEvidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(studentProfile.this, GridStudentEvidence.class);
                i.putExtra("studentId", sId);
                i.putExtra("studentName", sName);
                i.putExtra("activityType","AllEvidence");

                startActivity(i);
            }
        });
        btnShowAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(studentProfile.this, GridStudentEvidence.class);
                i.putExtra("studentId", sId);
                i.putExtra("studentName", sName);
                i.putExtra("activityType","AssessmentRecord");
                startActivity(i);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
