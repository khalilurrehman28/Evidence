package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.dupleit.kotlin.primaryschoolassessment.Evidence.AddEvidence;
import com.dupleit.kotlin.primaryschoolassessment.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class filterActivity extends AppCompatActivity {
    @BindView(R.id.etStartDate) EditText etStartDate;
    @BindView(R.id.etEndDate) EditText etEndDate;
    @BindView(R.id.btnApply) Button btnApply;
    @BindView(R.id.btnCancel) Button btnCancel;
        int  DDay,DMonth,DYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//to hide keyboad

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Filter With Date");
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {

                    Intent intent = new Intent();
                    intent.putExtra("startDate", etStartDate.getText().toString());
                    intent.putExtra("endDate", etEndDate.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        etStartDate.setInputType(InputType.TYPE_NULL);
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        monthOfYear = monthOfYear + 1;
                        String mon = (monthOfYear < 10) ? "0" + monthOfYear : "" + monthOfYear;
                        String day = (dayOfMonth < 10) ? "0" + dayOfMonth : "" + dayOfMonth;
                        //String getDate = year+"/"+mon+"/"+day;
                        String getDate = day + "/" + mon + "/" + year;
                        DDay = dayOfMonth;
                        DMonth =monthOfYear;
                        DYear =year;
                        etStartDate.setText(getDate);
                        etEndDate.setText("");

                    }
                };
                Calendar calendar = Calendar.getInstance();
                int Date = calendar.get(Calendar.DAY_OF_MONTH);
                int Month = calendar.get(Calendar.MONTH);
                int Year = calendar.get(Calendar.YEAR);
                //Time date = new Time();



                DatePickerDialog d = new DatePickerDialog(filterActivity.this, dpd, Year,Month,Date);
                d.getDatePicker().setMaxDate(System.currentTimeMillis());
                d.show();

            }
        });
        etEndDate.setInputType(InputType.TYPE_NULL);
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        monthOfYear = monthOfYear + 1;
                        String mon = (monthOfYear < 10) ? "0" + monthOfYear : "" + monthOfYear;
                        String day = (dayOfMonth < 10) ? "0" + dayOfMonth : "" + dayOfMonth;
                        //String getDate = year+"/"+mon+"/"+day;
                        String getDate = day + "/" + mon + "/" + year;

                        etEndDate.setText(getDate);
                    }
                };
                Calendar calendar = Calendar.getInstance();
                int Date = calendar.get(Calendar.DAY_OF_MONTH);
                int Month = calendar.get(Calendar.MONTH);
                int Year = calendar.get(Calendar.YEAR);
                Log.e("date",""+Date+" "+Month+" "+Year);


                Calendar previous = Calendar.getInstance();
                previous.set(DYear,DMonth-1,DDay,
                        00, 0, 0);
                long startTime = previous.getTimeInMillis();

                //Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(filterActivity.this, dpd, Year,Month,Date);
                d.getDatePicker().setMaxDate(System.currentTimeMillis());
                d.getDatePicker().setMinDate(startTime);
                d.show();

            }
        });

    }

    private boolean validate() {
        if (etStartDate.getText().toString().equals("")) {
            etStartDate.setError("Please select start date");
            return false;
        }else {
            etStartDate.setError(null);

        }
        if (etEndDate.getText().toString().equals("")) {
            etEndDate.setError("Please select start date");
            return false;
        }else {
            etEndDate.setError(null);

        }
        return true;
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
