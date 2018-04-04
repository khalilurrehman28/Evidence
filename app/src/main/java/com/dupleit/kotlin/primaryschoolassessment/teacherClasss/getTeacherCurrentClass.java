package com.dupleit.kotlin.primaryschoolassessment.teacherClasss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.selectStudentClass;

import butterknife.BindView;
import butterknife.ButterKnife;

public class getTeacherCurrentClass extends AppCompatActivity {
    @BindView(R.id.btnChange)
    Button btnChange;
    @BindView(R.id.btnCancel) Button btnCancel;
    @BindView(R.id.teacherCurrentClass)
    TextView teacherCurrentClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_teacher_current_class);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//to hide keyboad
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Current Class");
        if (!teacherClass().equals("")){
            teacherCurrentClass.setText("Class: "+teacherClass());
        }else {
            teacherCurrentClass.setText("No class found");
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getTeacherCurrentClass.this, selectStudentClass.class);
                i.putExtra("activityType","currentClass");
                startActivity(i);
            }
        });
    }

    private String teacherClass() {
        return new PreferenceManager(getTeacherCurrentClass.this).getTeacherClass();
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
