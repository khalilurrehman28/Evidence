package com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails;

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
import com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter.CustomSpinnerSubFrameAdapter;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.GetFrameworksubtitleModel;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.SubTitleData;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.adapter.getSubMarksDetailAdapter;
import com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.model.GetSubMarksDetailResponse;
import com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.model.MarksListComparator;
import com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.model.subMarksData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.GetFrameworksModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.GetparentFrameworkResponse;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.parentFrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.CustomObjectComparator;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.CustomObjectSorting1;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubFramworkMarksDetails extends AppCompatActivity {
    ProgressDialog pDialog;
    CustomParentFrameSpinnerAdapter frameworkCategorySpinnerAdapter;
    ArrayList<parentFrameworkData> frameworkCategoryList;
    @BindView(R.id.spinnerFrameworkCategory) Spinner spinnerFrameworkCategory;
    ArrayList<FrameworkData> FrameworksList;
    @BindView(R.id.spinnerFramework) Spinner spinnerFramework;
    CustomSpinnerAdapter customSpinnerFrameworkAdapter;
    @BindView(R.id.tvNoFrameworkAvailable)TextView tvNoFrameworkAvailable;

    ArrayList<SubTitleData> subFrameworksList;
    @BindView(R.id.spinnerSubFramework) Spinner spinnerSubFramework;
    CustomSpinnerSubFrameAdapter customSpinnerSubFrameAdapter;
    @BindView(R.id.tvNoSubFrameworkAvailable)TextView tvNoSubFrameworkAvailable;

    String frameworkCategoryId, frameworkCategoryName,FrameworkId, FrameWorkTitle,subFrameworkId, subFrameWorkTitle;
    @BindView(R.id.layoutSubFramework) LinearLayout layoutSubFramework;
    @BindView(R.id.btnAddMarksDetails) TextView btnAddMarksDetails;
    Map<String,String> hashMap;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private List<subMarksData> subMarksDetailList;
    getSubMarksDetailAdapter subMarksDetailAdapter;
    @BindView(R.id.linearMarksPreview) LinearLayout linearMarksPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_framwork_marks_details);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Framework Marks details");
        tvNoFrameworkAvailable.setVisibility(View.GONE);
        tvNoSubFrameworkAvailable.setVisibility(View.GONE);
        linearMarksPreview.setVisibility(View.GONE);
        getDropDownOfParentFrameWork();
        hashMap = new HashMap<String,String>();

        btnAddMarksDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubFramworkMarksDetails.this,addSubFrameMarksDetails.class);
                intent.putExtra("frameworkCategoryId",frameworkCategoryId);
                intent.putExtra("frameworkCategoryName",frameworkCategoryName);
                intent.putExtra("FrameworkId",FrameworkId);
                intent.putExtra("FrameWorkTitle",FrameWorkTitle);
                intent.putExtra("subFrameworkId",subFrameworkId);
                intent.putExtra("subFrameWorkTitle",subFrameWorkTitle);
                intent.putExtra("hash", (Serializable) hashMap);
                startActivity(intent);
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

                frameworkCategoryId = ParentframeData.getCATEGORYID();
                frameworkCategoryName = ParentframeData.getCATEGORYNAME();

                getDropDownOfFrameWork(frameworkCategoryId);

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
            Toasty.warning(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG, true).show();
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

                            Toast.makeText(SubFramworkMarksDetails.this, "there have no data ", Toast.LENGTH_SHORT).show();
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

    private void getDropDownOfFrameWork(String frameworkCategoryId) {
        FrameworksList = new ArrayList<>();
        FrameworksList.clear();

        customSpinnerFrameworkAdapter = new CustomSpinnerAdapter(this, FrameworksList);
        spinnerFramework.setAdapter(customSpinnerFrameworkAdapter);
        createFrameWorkList(frameworkCategoryId);
        spinnerFramework.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final FrameworkData frameData = FrameworksList.get(position);

                FrameworkId = frameData.getFRAMEWORKID();
                FrameWorkTitle = frameData.getFRAMEWORKTITLE();

                getDropDownOfSubFrameWork(FrameworkId);


                // Toast.makeText(parent.getContext(), "class id" + classId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void createFrameWorkList(String frameworkCategoryId) {

        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            Toasty.warning(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG, true).show();

        } else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworksModel> userCall = service.getFrameworkTitles(Integer.parseInt(frameworkCategoryId), Integer.parseInt(teacherClassId()));
            userCall.enqueue(new Callback<GetFrameworksModel>() {
                @Override
                public void onResponse(Call<GetFrameworksModel> call, Response<GetFrameworksModel> response) {

                    Log.d("getListStatus", " " + response.body().getStatus());
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {

                            spinnerFramework.setVisibility(View.VISIBLE);
                            tvNoFrameworkAvailable.setVisibility(View.GONE);
                            layoutSubFramework.setVisibility(View.VISIBLE);
                           // linearMarksPreview.setVisibility(View.VISIBLE);

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

                                customSpinnerFrameworkAdapter.notifyDataSetChanged();
                            }

                        } else {

                            spinnerFramework.setVisibility(View.GONE);
                            tvNoFrameworkAvailable.setVisibility(View.VISIBLE);
                            layoutSubFramework.setVisibility(View.GONE);
                            linearMarksPreview.setVisibility(View.GONE);

                            Toasty.error(SubFramworkMarksDetails.this, "There have no data in this category", Toast.LENGTH_LONG, true).show();

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


        customSpinnerFrameworkAdapter.notifyDataSetChanged();
    }

    private void getDropDownOfSubFrameWork(String frameworkId) {
        subFrameworksList= new ArrayList<>();
        subFrameworksList.clear();

        customSpinnerSubFrameAdapter = new CustomSpinnerSubFrameAdapter(this, subFrameworksList);
        spinnerSubFramework.setAdapter(customSpinnerSubFrameAdapter);
        createSubFrameWorkList(frameworkId);
        spinnerSubFramework.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final SubTitleData frameData = subFrameworksList.get(position);

                subFrameworkId = frameData.getSubId();
                subFrameWorkTitle = frameData.getFRAMEWORKSUB();

                getMarksDetail(subFrameworkId);

                // Toast.makeText(parent.getContext(), "class id" + classId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createSubFrameWorkList(String frameworkId) {
        hashMap.clear();

        if (!checkInternetState.getInstance(this).isOnline()) {
            Toasty.warning(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG, true).show();
        } else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworksubtitleModel> userCall = service.get_framework_subtitle(Integer.parseInt(FrameworkId));
            userCall.enqueue(new Callback<GetFrameworksubtitleModel>() {
                @Override
                public void onResponse(Call<GetFrameworksubtitleModel> call, Response<GetFrameworksubtitleModel> response) {

                    Log.d("getListStatus", " " + response.body().getStatus());
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {

                            spinnerFramework.setVisibility(View.VISIBLE);
                            tvNoSubFrameworkAvailable.setVisibility(View.GONE);
                            btnAddMarksDetails.setVisibility(View.VISIBLE);
                           // linearMarksPreview.setVisibility(View.VISIBLE);


                            /*to get notice of principal to all*/
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                SubTitleData SubTitleData = new SubTitleData();
                                SubTitleData.setFRAMEWORKSTATUS(response.body().getData().get(i).getFRAMEWORKSTATUS());
                                SubTitleData.setFRAMEWORKSUB(response.body().getData().get(i).getFRAMEWORKSUB());
                                SubTitleData.setSCORE(response.body().getData().get(i).getSCORE());
                                SubTitleData.setSubId(response.body().getData().get(i).getSubId());
                                SubTitleData.setrEMARK(response.body().getData().get(i).getrEMARK());

                                SubTitleData.setEtGetScore("0");

                                subFrameworksList.add(SubTitleData);
                                //adapter.notifyDataSetChanged();
                                customSpinnerSubFrameAdapter.notifyDataSetChanged();
                            }

                        } else {

                            spinnerSubFramework.setVisibility(View.GONE);
                            tvNoSubFrameworkAvailable.setVisibility(View.VISIBLE);
                            btnAddMarksDetails.setVisibility(View.GONE);
                            linearMarksPreview.setVisibility(View.GONE);

                            Toasty.error(SubFramworkMarksDetails.this, "There have no data in framework", Toast.LENGTH_LONG, true).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<GetFrameworksubtitleModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }


        customSpinnerSubFrameAdapter.notifyDataSetChanged();
    }

    private void getMarksDetail(String subFrameworkId) {

        subMarksDetailList = new ArrayList<>();
        subMarksDetailAdapter = new getSubMarksDetailAdapter(this, subMarksDetailList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subMarksDetailAdapter);
        subMarksDetailList.clear();
        prepareMarksDetail(subFrameworkId);

    }

    private void prepareMarksDetail(String subFrameworkId) {
        hashMap.clear();

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetSubMarksDetailResponse> userCall = service.get_marks_detail(Integer.parseInt(subFrameworkId));
        userCall.enqueue(new Callback<GetSubMarksDetailResponse>() {
            @Override
            public void onResponse(Call<GetSubMarksDetailResponse> call, Response<GetSubMarksDetailResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        linearMarksPreview.setVisibility(View.VISIBLE);
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            subMarksData subMarksData = new subMarksData();
                            subMarksData.setDATETIME(response.body().getData().get(i).getDATETIME());
                            subMarksData.setDESCRIPTION(response.body().getData().get(i).getDESCRIPTION());
                            subMarksData.setMARKS(response.body().getData().get(i).getMARKS());
                            subMarksData.setSTATUS(response.body().getData().get(i).getSTATUS());
                            subMarksData.setMARKSDETAILID(response.body().getData().get(i).getMARKSDETAILID());

                            hashMap.put(response.body().getData().get(i).getMARKS(),response.body().getData().get(i).getDESCRIPTION());
                            //Collections.sort(studentList, new CustomObjectComparator(ascendingDes));
                            subMarksDetailList.add(subMarksData);
                            Collections.sort(subMarksDetailList, new CustomObjectSorting1(false));
                            //adapter.notifyDataSetChanged();
                            subMarksDetailAdapter.notifyDataSetChanged();
                        }

                    } else {
                        linearMarksPreview.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(SubFramworkMarksDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSubMarksDetailResponse> call, Throwable t) {
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
        return new PreferenceManager(SubFramworkMarksDetails.this).getTeacherClassId();
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
