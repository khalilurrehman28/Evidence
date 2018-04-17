package com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.createFramework.CreateFramework.create_framework;
import com.dupleit.kotlin.primaryschoolassessment.createFramework.model.addframeworkResponse;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.selectStudentClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addSubFrameMarksDetails extends AppCompatActivity {

    String frameworkCategoryId, frameworkCategoryName,FrameworkId, FrameWorkTitle,subFrameworkId, subFrameWorkTitle;

    @BindView(R.id.marksAddedIn) TextView marksAddedIn;
    @BindView(R.id.et1) TextView et1;
    @BindView(R.id.et2) TextView et2;
    @BindView(R.id.et3) TextView et3;
    @BindView(R.id.et4) TextView et4;
    @BindView(R.id.et5) TextView et5;
    @BindView(R.id.btnAddMarks) TextView btnAddMarksDetails;
    ArrayList<String> edittextData;
    ArrayList<String> filledMarksDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_frame_marks_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Marks details");
        edittextData =new ArrayList<>();
        filledMarksDetailList= new ArrayList<>();
        frameworkCategoryId =getIntent().getStringExtra("frameworkCategoryId");
        frameworkCategoryName =getIntent().getStringExtra("frameworkCategoryName");
        FrameworkId =getIntent().getStringExtra("FrameworkId");
        FrameWorkTitle =getIntent().getStringExtra("FrameWorkTitle");
        subFrameworkId =getIntent().getStringExtra("subFrameworkId");
        subFrameWorkTitle =getIntent().getStringExtra("subFrameWorkTitle");
        filledMarksDetailList = getIntent().getStringArrayListExtra("marksDetailList");
        marksAddedIn.setText(frameworkCategoryName+" >> "+FrameWorkTitle+" >> "+subFrameWorkTitle);

        if (filledMarksDetailList.size()>0){
            et1.setText(filledMarksDetailList.get(0));
            et2.setText(filledMarksDetailList.get(1));
            et4.setText(filledMarksDetailList.get(2));
            et3.setText(filledMarksDetailList.get(3));
            et5.setText(filledMarksDetailList.get(4));
        }


        btnAddMarksDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    hitApi();
                }
            }
        });


    }

    private void hitApi() {
        edittextData.clear();
        edittextData.add(et1.getText().toString());
        edittextData.add(et2.getText().toString());
        edittextData.add(et3.getText().toString());
        edittextData.add(et4.getText().toString());
        edittextData.add(et5.getText().toString());
        Log.e("listData",""+edittextData+"  --  ");


        JSONObject item1 = new JSONObject();
        //JSONObject json = new JSONObject();
        int count = 1;
        for (int i=0;i<edittextData.size();i++){
            try {
                item1.put(String.valueOf(count),edittextData.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            count++;
        }

        Log.d("json", "hitApi: "+item1.toString());

        final ProgressDialog pd = new ProgressDialog(addSubFrameMarksDetails.this);
        pd.setTitle("Adding Data");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        if (!checkInternetState.getInstance(addSubFrameMarksDetails.this).isOnline()) {
            Toasty.warning(addSubFrameMarksDetails.this, "Please check your internet connection.", Toast.LENGTH_LONG, true).show();
        }else {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<addframeworkResponse> userCall = service.add_marks_detail(Integer.parseInt(subFrameworkId),item1.toString());
            userCall.enqueue(new Callback<addframeworkResponse>() {
                @Override
                public void onResponse(Call<addframeworkResponse> call, Response<addframeworkResponse> response) {
                    pd.hide();
                    if (response.isSuccessful()){
                        if (response.body().getStatus()){

                            Toasty.success(addSubFrameMarksDetails.this,"Marks detail added successfully", Toast.LENGTH_LONG, true).show();
                            Intent i = new Intent(addSubFrameMarksDetails.this, SubFramworkMarksDetails.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();

                        }
                    }else {
                        Toasty.error(addSubFrameMarksDetails.this, "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }

                }
                @Override
                public void onFailure(Call<addframeworkResponse> call, Throwable t) {
                    pd.hide();
                    Log.d("onFailure", t.toString());
                }
            });
        }
    }

    private boolean validate() {
        if (et1.getText().toString().trim().equals("")||et1.getText().toString().trim().length()<4) {
            et1.setError("Please fill some detail");
            return false;
        }else {
            et1.setError(null);
        }
        if (et2.getText().toString().trim().equals("")||et2.getText().toString().trim().length()<4) {
            et2.setError("Please fill some detail");
            return false;
        }else {
            et2.setError(null);
        }
        if (et2.getText().toString().trim().equals("")||et3.getText().toString().trim().length()<4) {
            et3.setError("Please fill some detail");
            return false;
        }else {
            et3.setError(null);
        }

        if (et4.getText().toString().trim().equals("")||et4.getText().toString().trim().length()<4) {
            et4.setError("Please fill some detail");
            return false;
        }else {
            et4.setError(null);
        }
        if (et5.getText().toString().trim().equals("")||et5.getText().toString().trim().length()<4) {
            et5.setError("Please fill some detail");
            return false;
        }else {
            et5.setError(null);
        }



    return true;
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
