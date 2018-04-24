package com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.activities.Login.UI.LoginActivity;
import com.dupleit.kotlin.primaryschoolassessment.activities.MainActivity;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.RecyclerTouchListener;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.adapter.getTeacherClassesAdapter;
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.model.GetTeacherClassesResponse;
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.model.getClassesData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class selectStudentClass extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noClassesFound)
    TextView noClassesFound;
    private List<getClassesData> frameworksList;
    getTeacherClassesAdapter adapter;
    String activityType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student_class);
        ButterKnife.bind(this);

        initilize();
    }

    private void initilize() {
        activityType = getIntent().getStringExtra("activityType");
        if (activityType.equals("login")){
            setTitle("Select your class");
        }
        if (activityType.equals("currentClass")){
            setTitle("Change class");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


        frameworksList = new ArrayList<>();
        adapter = new getTeacherClassesAdapter(selectStudentClass.this, frameworksList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(selectStudentClass.this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!getTeacherEmail().equals("")){
            progressBar.setVisibility(View.VISIBLE);
            prepareClassList();

        }else {
            Intent i = new Intent(selectStudentClass.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toasty.warning(selectStudentClass.this, "Please login first", Toast.LENGTH_SHORT, true).show();

        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(selectStudentClass.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final getClassesData classes = frameworksList.get(position);

               /* Intent i = new Intent(selectStudentClass.this, gettingFrameworkSubtitles.class);
                i.putExtra("frameworkId", frames.getFRAMEWORKID());
                i.putExtra("frameworkTitle", frames.getFRAMEWORKTITLE());
                startActivity(i);*/
               if (classes.getCLASSSTATUS().equals("1")){
                   Toasty.warning(selectStudentClass.this, "This class is not active", Toast.LENGTH_LONG, true).show();

               }else {

                   if (classes.getCLASSID().equals(teacherClassId())){
                       Toasty.warning(getApplicationContext(), "Already selected", Toast.LENGTH_SHORT, true).show();
                   }else {

                       new PreferenceManager(getApplicationContext()).saveTeacherClassName(classes.getCLASSNAME());
                       new PreferenceManager(getApplicationContext()).saveTeacherClassId(classes.getCLASSID());
                       if (activityType.equals("login")) {
                           final ProgressDialog progress = new ProgressDialog(selectStudentClass.this);
                           progress.setMessage("Please wait...");
                           progress.setCancelable(false);
                           progress.show();
                           Runnable progressRunnable = new Runnable() {

                               @Override
                               public void run() {
                                   progress.cancel();
                                   Intent intent = new Intent(selectStudentClass.this, MainActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);
                               }
                           };

                           Handler pdCanceller = new Handler();
                           pdCanceller.postDelayed(progressRunnable, 1000);

                       }
                       if (activityType.equals("currentClass")) {
                           new PreferenceManager(getApplicationContext()).saveTeacherClassName(classes.getCLASSNAME());
                           new PreferenceManager(getApplicationContext()).saveTeacherClassId(classes.getCLASSID());

                           final ProgressDialog progress = new ProgressDialog(selectStudentClass.this);
                           progress.setMessage("Please wait...");
                           progress.setCancelable(false);
                           progress.show();
                           Runnable progressRunnable = new Runnable() {

                               @Override
                               public void run() {
                                   progress.cancel();
                                   Intent intent = new Intent(selectStudentClass.this, MainActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);


                                   Toasty.success(getApplicationContext(), "Class updated", Toast.LENGTH_LONG, true).show();

                               }
                           };

                           Handler pdCanceller = new Handler();
                           pdCanceller.postDelayed(progressRunnable, 1000);
                       }
                   }

               }


            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));

    }

    private void prepareClassList() {
        noClassesFound.setVisibility(View.GONE);
        if (!checkInternetState.getInstance(selectStudentClass.this).isOnline()) {
            progressBar.setVisibility(View.GONE);
            noClassesFound.setText("No Internet Connection.");
            noClassesFound.setVisibility(View.VISIBLE);
        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetTeacherClassesResponse> userCall = service.get_teacher_class(Integer.parseInt(sharedId()));
            userCall.enqueue(new Callback<GetTeacherClassesResponse>() {
                @Override
                public void onResponse(Call<GetTeacherClassesResponse> call, Response<GetTeacherClassesResponse> response) {
                    progressBar.setVisibility(View.GONE);

                    Log.d("classes"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            noClassesFound.setVisibility(View.GONE);

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                getClassesData classes = new getClassesData();
                                classes.setCLASSID(response.body().getData().get(i).getCLASSID());
                                classes.setCLASSDATETIME(response.body().getData().get(i).getCLASSDATETIME());
                                classes.setCLASSNAME(response.body().getData().get(i).getCLASSNAME());
                                classes.setCLASSMODIFYDATETIME(response.body().getData().get(i).getCLASSMODIFYDATETIME());
                                classes.setCLASSSTATUS(response.body().getData().get(i).getCLASSSTATUS());

                                frameworksList.add(classes);
                                //adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                            }

                        }else{
                            noClassesFound.setText("No classes found");
                            noClassesFound.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(selectStudentClass.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<GetTeacherClassesResponse> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    progressBar.setVisibility(View.GONE);

                }
            });
        }
    }
    private String teacherClassId() {
        return new PreferenceManager(selectStudentClass.this).getTeacherClassId();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private String sharedId() {
        return new PreferenceManager(selectStudentClass.this).getUserID();
    }
    private String getTeacherEmail() {
        return new PreferenceManager(selectStudentClass.this).getUserEmail();
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
