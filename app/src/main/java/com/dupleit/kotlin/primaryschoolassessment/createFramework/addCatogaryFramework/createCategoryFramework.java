package com.dupleit.kotlin.primaryschoolassessment.createFramework.addCatogaryFramework;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter.CustomParentFrameSpinnerAdapter;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.createFramework.CreateFramework.create_framework;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.GetparentFrameworkResponse;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.parentFrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class createCategoryFramework extends AppCompatActivity {
    String activityType;
    @BindView(R.id.cardFramework) CardView cardFramework;
    @BindView(R.id.cardCategory) CardView cardCategory;
    CustomParentFrameSpinnerAdapter frameworkCategorySpinnerAdapter;
    ArrayList<parentFrameworkData> frameworkCategoryList;
    @BindView(R.id.spinnerFrameworkCategory)
    Spinner spinnerFrameworkCategory;
    String parentFrameworkId, parentFrameWorkTitle;
    ProgressDialog pDialog;
    @BindView(R.id.btnAdd)Button btnAdd;
    @BindView(R.id.btnCancel)Button btnCancel;
    @BindView(R.id.etFrameworkName) EditText etFrameworkName;
    @BindView(R.id.etCategory) EditText etCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category_framework);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityType = getIntent().getStringExtra("activityType");
        if (activityType.equals("AddCategory")){
            setTitle("Add Category");
            cardCategory.setVisibility(View.VISIBLE);
            cardFramework.setVisibility(View.GONE);
        }else if (activityType.equals("AddFramework")){
            setTitle("Add Framework Name");
            cardCategory.setVisibility(View.GONE);
            cardFramework.setVisibility(View.VISIBLE);
            getDropDownOfParentFrameWork();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityType.equals("AddCategory")){

                    if (etCategory.getText().toString().trim().equals("")){
                        etCategory.setError("Please fill category");
                    }else {
                        Toast.makeText(createCategoryFramework.this, "All right", Toast.LENGTH_SHORT).show();
                    }

                }else if (activityType.equals("AddFramework")){
                    if (etFrameworkName.getText().toString().trim().equals("")){
                        etFrameworkName.setError("Please fill framework name");
                    }else {
                        Toast.makeText(createCategoryFramework.this, "All right", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDropDownOfParentFrameWork() {
        frameworkCategoryList = new ArrayList<>();
        frameworkCategoryList.clear();

        frameworkCategorySpinnerAdapter = new CustomParentFrameSpinnerAdapter(this, frameworkCategoryList);
        spinnerFrameworkCategory.setAdapter(frameworkCategorySpinnerAdapter);
        createParentFrameWorkList();
        spinnerFrameworkCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final parentFrameworkData ParentframeData = frameworkCategoryList.get(position);

                parentFrameworkId = ParentframeData.getCATEGORYID();
                parentFrameWorkTitle = ParentframeData.getCATEGORYNAME();


                // Toast.makeText(parent.getContext(), "class id" + classId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createParentFrameWorkList() {
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
            Call<GetparentFrameworkResponse> userCall = service.getParentFrameworkTitles();
            userCall.enqueue(new Callback<GetparentFrameworkResponse>() {
                @Override
                public void onResponse(Call<GetparentFrameworkResponse> call, Response<GetparentFrameworkResponse> response) {
                    hidepDialog();
                    Log.d("getListStatus", " " + response.body().getStatus());
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {

                            /*to get notice of principal to all*/
                            Log.d("getMessage", "" + response.body().getMsg());
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                parentFrameworkData parentFrames = new parentFrameworkData();

                                Log.d("data", "" + response.body().getData().get(i).getCATEGORYID());
                                parentFrames.setCATEGORYID(response.body().getData().get(i).getCATEGORYID());
                                parentFrames.setCATEGORYNAME(response.body().getData().get(i).getCATEGORYNAME());
                                parentFrames.setDATETIME(response.body().getData().get(i).getDATETIME());
                                parentFrames.setSTATUS(response.body().getData().get(i).getSTATUS());
                                frameworkCategoryList.add(parentFrames);
                                //adapter.notifyDataSetChanged();

                                frameworkCategorySpinnerAdapter.notifyDataSetChanged();
                            }

                        } else {

                            Toast.makeText(createCategoryFramework.this, "there have no data ", Toast.LENGTH_SHORT).show();
                            //noFramesFound.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetparentFrameworkResponse> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                }
            });
        }


        frameworkCategorySpinnerAdapter.notifyDataSetChanged();
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
