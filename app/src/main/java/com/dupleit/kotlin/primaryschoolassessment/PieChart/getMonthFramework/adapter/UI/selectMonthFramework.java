package com.dupleit.kotlin.primaryschoolassessment.PieChart.getMonthFramework.adapter.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.PieChart.PreviewPieChart.PieChartActivity;
import com.dupleit.kotlin.primaryschoolassessment.PieChart.getMonthFramework.adapter.CustomSpinnerFrameworkAdapter;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.GetFrameworksModel;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class selectMonthFramework extends AppCompatActivity {
    CustomSpinnerFrameworkAdapter customSpinnerAdapter;
    ArrayList<FrameworkData> FrameworksList;
    @BindView(R.id.spinnerCustom) Spinner spinnerFrameworks;
    @BindView(R.id.spinnerMonth) Spinner spinnerMonth;
    @BindView(R.id.btnCancel) Button btnCancel;
    @BindView(R.id.btnCreate) Button btnCreate;

    String FrameworkId, FrameWorkTitle;
    ProgressDialog pDialog;
    String startDate,endDate,studentId,studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_month_framework);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startDate= "";
        endDate = "";
        setTitle("Select Month/Framework");
        studentId= getIntent().getStringExtra("studentId");
        studentName= getIntent().getStringExtra("studentName");
        getDropDownOfMonth();
        getDropDownOfFrameWork();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(selectMonthFramework.this, PieChartActivity.class);
                i.putExtra("studentId", studentId);
                i.putExtra("studentName", studentName);
                i.putExtra("startDate", startDate);
                i.putExtra("endDate", endDate);
                i.putExtra("FrameworkId", FrameworkId);
                i.putExtra("FrameworkTitle", FrameWorkTitle);
                startActivity(i);
            }
        });
    }

    private void getDropDownOfMonth() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.object_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // Toast.makeText(getApplicationContext(), "Item number: " + position, Toast.LENGTH_LONG).show();
                switch (position) {

                    case 0:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "31/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 1:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "29/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 2:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "31/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 3:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "30/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 4:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "31/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 5:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "30/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 6:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "31/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 7:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "31/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 8:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "30/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 9:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "31/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 10:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "30/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;
                    case 11:
                        startDate = "01/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        endDate = "31/01/"+Calendar.getInstance().get(Calendar.YEAR);
                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getDropDownOfFrameWork() {
        FrameworksList = new ArrayList<>();
        FrameworksList.clear();

        customSpinnerAdapter = new CustomSpinnerFrameworkAdapter(this, FrameworksList);
        spinnerFrameworks.setAdapter(customSpinnerAdapter);
        createFrameWorkList();
        spinnerFrameworks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final FrameworkData frameData = FrameworksList.get(position);

                FrameworkId = frameData.getFRAMEWORKID();
                FrameWorkTitle = frameData.getFRAMEWORKTITLE();

                // Toast.makeText(parent.getContext(), "class id" + classId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void createFrameWorkList() {
        pDialog = new ProgressDialog(this);
        //pDialog.setIndeterminate(true);
        pDialog.setMessage("Please wait getting data...");
        pDialog.setCancelable(false);

        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();

            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        } else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworksModel> userCall = service.getFrameworkTitles(1,1);
            userCall.enqueue(new Callback<GetFrameworksModel>() {
                @Override
                public void onResponse(Call<GetFrameworksModel> call, Response<GetFrameworksModel> response) {
                    hidepDialog();
                    Log.d("getListStatus", " " + response.body().getStatus());
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            /*to get notice of principal to all*/
                            Log.d("getMessage", "" + response.body().getMsg());
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                FrameworkData Frames = new FrameworkData();

                                Log.d("data", "" + response.body().getData().get(i).getFRAMEWORKDATETIME());
                                Frames.setFRAMEWORKTITLE(response.body().getData().get(i).getFRAMEWORKTITLE());
                                Frames.setFRAMEWORKID(response.body().getData().get(i).getFRAMEWORKID());
                                Frames.setFRAMEWORKDATETIME(response.body().getData().get(i).getFRAMEWORKDATETIME());
                                FrameworksList.add(Frames);
                                //adapter.notifyDataSetChanged();

                                customSpinnerAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(selectMonthFramework.this, "there have no data ", Toast.LENGTH_SHORT).show();
                            //noFramesFound.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetFrameworksModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }

    }
    //to show progress dialog
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    //to hide progress
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
