package com.dupleit.kotlin.primaryschoolassessment.singleStudentDetail;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.activities.studentProfile.studentProfile;
import com.dupleit.kotlin.primaryschoolassessment.basicInfo;
import com.dupleit.kotlin.primaryschoolassessment.fragments.allStudents.Ui.StudentListFragment;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.GridStudentEvidence;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class profileSingleStudent extends AppCompatActivity {
    @BindView(R.id.studenImageBackground) ImageView studenImageBackground;
    @BindView(R.id.studentName) TextView studentName;
    @BindView(R.id.student_name) TextView student_name;
    @BindView(R.id.student_gender) TextView student_gender;
    @BindView(R.id.studentImage) CircleImageView studentImage;
    @BindView(R.id.studentClass) TextView studentClass;
    @BindView(R.id.student_class) TextView student_class;
    @BindView(R.id.studentDOB) TextView studentDOB;
    @BindView(R.id.student_dob) TextView student_dob;
    @BindView(R.id.studentGender) TextView studentGender;
    @BindView(R.id.class_session) TextView class_session;
    @BindView(R.id.studentFatherName) TextView studentFatherName;
    @BindView(R.id.studentMotherName) TextView studentMotherName;
    @BindView(R.id.contactNo) TextView contactNo;
    @BindView(R.id.studentFEmail) TextView studentFEmail;
    @BindView(R.id.studentAddress) TextView studentAddress;
    @BindView(R.id.btnShowEvidence) Button btnShowEvidence;
    @BindView(R.id.btnShowAssessment) Button btnShowAssessment;


    /*@BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_single_student);

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
            Glide.with(this).load(Utils.webUrlHome+sImage).into(studenImageBackground);

        }

        if (!sName.equals("")){
            studentName.setText(sName);
            student_name.setText(sName);
        }
        if (!sClass.equals("")){
            studentClass.setText(sClass);
            student_class.setText(sClass);
        }
        if (!sDOB.equals("")){
            studentDOB.setText(sDOB);
            student_dob.setText(sDOB);

        }
        if (!sGender.equals("")){
            if (sGender.equals("1")){
                studentGender.setText("Male");
                student_gender.setText("Male");
            }else if (sGender.equals("2")){
                studentGender.setText("Female");
                student_gender.setText("Female");

            }

        }
        if (!sclassSession.equals("")){
            class_session.setText(sclassSession);
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
                Intent i = new Intent(profileSingleStudent.this, GridStudentEvidence.class);
                i.putExtra("studentId", sId);
                i.putExtra("studentName", sName);
                i.putExtra("activityType","AllEvidence");

                startActivity(i);
            }
        });
        btnShowAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profileSingleStudent.this, GridStudentEvidence.class);
                i.putExtra("studentId", sId);
                i.putExtra("studentName", sName);
                i.putExtra("activityType","AssessmentRecord");
                startActivity(i);
            }
        });
       /* setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#F36961"));*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new basicInfo(), "Basic Info");
        adapter.addFragment(new basicInfo(), "Personal Info");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


