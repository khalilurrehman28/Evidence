package com.dupleit.kotlin.primaryschoolassessment.createFramework.CreateFramework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter.CustomParentFrameSpinnerAdapter;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter.CustomSpinnerAdapter;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.GetFrameworksubtitleModel;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.SubTitleData;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.createFramework.CreateFramework.adapter.getFrameworksubTitleAdapter;
import com.dupleit.kotlin.primaryschoolassessment.createFramework.addCatogaryFramework.createCategoryFramework;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.GetFrameworksModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.GetparentFrameworkResponse;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.parentFrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_framework extends AppCompatActivity {

    ProgressDialog pDialog;
    CustomParentFrameSpinnerAdapter frameworkCategorySpinnerAdapter;
    ArrayList<parentFrameworkData> frameworkCategoryList;
    @BindView(R.id.spinnerFrameworkCategory) Spinner spinnerFrameworkCategory;
    ArrayList<FrameworkData> FrameworksList;
    @BindView(R.id.spinnerFramework)
    Spinner spinnerFramework;
    @BindView(R.id.tvNoFrameworkAvailable)TextView tvNoFrameworkAvailable;
    @BindView(R.id.layoutSubFramework)LinearLayout layoutSubFramework;
    CustomSpinnerAdapter customSpinnerAdapter;
    String parentFrameworkId, parentFrameWorkTitle,FrameworkId, FrameWorkTitle;
    @BindView(R.id.recyclerSubTitle) RecyclerView recyclerSubTitle;
    private List<SubTitleData> frameworksubTList;
    getFrameworksubTitleAdapter subTitleAdapter;
    @BindView(R.id.linearSubTitle) LinearLayout linearSubTitle;
    @BindView(R.id.className)TextView className;
    @BindView(R.id.btnAddCategory)TextView btnAddCategory;
    @BindView(R.id.btnAddFramework)TextView btnAddFramework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_framework);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Create Framework");
        tvNoFrameworkAvailable.setVisibility(View.GONE);
        linearSubTitle.setVisibility(View.GONE);
        className.setText(teacherClass()+" Class");
        getDropDownOfParentFrameWork();

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(create_framework.this, createCategoryFramework.class);
                i.putExtra("activityType","AddCategory");
                startActivity(i);
            }
        });
        btnAddFramework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(create_framework.this, createCategoryFramework.class);
                i.putExtra("activityType","AddFramework");
                startActivity(i);
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

                getDropDownOfFrameWork(parentFrameworkId);

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

                            spinnerFramework.setVisibility(View.VISIBLE);
                            tvNoFrameworkAvailable.setVisibility(View.GONE);
                            layoutSubFramework.setVisibility(View.VISIBLE);
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

                            Toast.makeText(create_framework.this, "there have no data ", Toast.LENGTH_SHORT).show();
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

    private void getDropDownOfFrameWork(String parentFrameworkId) {
        FrameworksList = new ArrayList<>();
        FrameworksList.clear();

        customSpinnerAdapter = new CustomSpinnerAdapter(this, FrameworksList);
        spinnerFramework.setAdapter(customSpinnerAdapter);
        createFrameWorkList(parentFrameworkId);
        spinnerFramework.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final FrameworkData frameData = FrameworksList.get(position);

                FrameworkId = frameData.getFRAMEWORKID();
                FrameWorkTitle = frameData.getFRAMEWORKTITLE();

                getSubTitles();

                // Toast.makeText(parent.getContext(), "class id" + classId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void createFrameWorkList(String parentFrameworkId) {

        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {


            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        } else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworksModel> userCall = service.getFrameworkTitles(Integer.parseInt(parentFrameworkId), Integer.parseInt(teacherClassId()));
            userCall.enqueue(new Callback<GetFrameworksModel>() {
                @Override
                public void onResponse(Call<GetFrameworksModel> call, Response<GetFrameworksModel> response) {

                    Log.d("getListStatus", " " + response.body().getStatus());
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {

                            spinnerFramework.setVisibility(View.VISIBLE);
                            tvNoFrameworkAvailable.setVisibility(View.GONE);
                            layoutSubFramework.setVisibility(View.VISIBLE);

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

                            spinnerFramework.setVisibility(View.GONE);
                            tvNoFrameworkAvailable.setVisibility(View.VISIBLE);
                            layoutSubFramework.setVisibility(View.GONE);


                            Toast.makeText(create_framework.this, "there have no data ", Toast.LENGTH_SHORT).show();
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


        customSpinnerAdapter.notifyDataSetChanged();
    }
    private void getSubTitles() {

        frameworksubTList = new ArrayList<>();
        subTitleAdapter = new getFrameworksubTitleAdapter(this, frameworksubTList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerSubTitle.setLayoutManager(mLayoutManager);
        recyclerSubTitle.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerSubTitle.setItemAnimator(new DefaultItemAnimator());
        recyclerSubTitle.setAdapter(subTitleAdapter);

        prepareSubtitles();

    }

    private void prepareSubtitles() {

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetFrameworksubtitleModel> userCall = service.get_framework_subtitle(Integer.parseInt(FrameworkId));
        userCall.enqueue(new Callback<GetFrameworksubtitleModel>() {
            @Override
            public void onResponse(Call<GetFrameworksubtitleModel> call, Response<GetFrameworksubtitleModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        linearSubTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            SubTitleData SubTitleData = new SubTitleData();
                            SubTitleData.setFRAMEWORKSTATUS(response.body().getData().get(i).getFRAMEWORKSTATUS());
                            SubTitleData.setFRAMEWORKSUB(response.body().getData().get(i).getFRAMEWORKSUB());
                            SubTitleData.setSCORE(response.body().getData().get(i).getSCORE());
                            SubTitleData.setSubId(response.body().getData().get(i).getSubId());
                            SubTitleData.setrEMARK(response.body().getData().get(i).getrEMARK());

                            SubTitleData.setEtGetScore("0");

                            frameworksubTList.add(SubTitleData);
                            //adapter.notifyDataSetChanged();
                            subTitleAdapter.notifyDataSetChanged();
                        }

                    } else {
                        linearSubTitle.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(create_framework.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFrameworksubtitleModel> call, Throwable t) {
                //hidepDialog();
                Log.d("onFailure", t.toString());

            }
        });
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
    private String teacherClassId() {
        return new PreferenceManager(create_framework.this).getTeacherClassId();
    }
    private String teacherClass() {
        return new PreferenceManager(create_framework.this).getTeacherClass();
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
