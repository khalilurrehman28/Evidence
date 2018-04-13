package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.FullImageView.FullImage;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.adapter.getFrameworkPreviewAdapter;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.adapter.getMediaAdapter;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.model.GetFrameworkScoresModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.model.getMediaData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.model.getScoreData;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.RecyclerTouchListener;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Utils;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import com.dupleit.kotlin.primaryschoolassessment.videoPlayer.videoPlayer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EvidencePreview extends AppCompatActivity {
    @BindView(R.id.recyclerResidence) RecyclerView recyclerView;
    @BindView(R.id.noMediaFound) TextView noMediaFound;
    @BindView(R.id.evidenceDate) TextView evidenceDateTv;
    @BindView(R.id.studentName) TextView studentNameTv;
    @BindView(R.id.evidenceComment) TextView evidenceCommentTv;
    String studentId,studentName,evidenceId,evidenceDate,evidenceComment;
    ArrayList<getMediaData> mediaDataList;
    getMediaAdapter adapter;
    ArrayList<String> imageExt;
    ArrayList<String> VideoExt;
    @BindView(R.id.noFrameworkFound) TextView noFrameworkAvailable;
    @BindView(R.id.recyclerframeworkPreview) RecyclerView recyclerframeworkPreview;
    getFrameworkPreviewAdapter adapterFramework;
    ArrayList<getScoreData> frameworksList;
    @BindView(R.id.frameworkTitle) TextView frameworkTitle;
    @BindView(R.id.gradePreview) TextView gradePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence_preview);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Evidence Preview");
        imageExt = new ArrayList<>();
        VideoExt = new ArrayList<>();
        mediaDataList = new ArrayList<>();
        frameworksList = new ArrayList<>();

        imageExt.add("png");
        imageExt.add("jpeg");
        imageExt.add("jpg");
        imageExt.add("gif");
        imageExt.add("JPG");
        imageExt.add("JPEG");

        VideoExt.add("mp4");
        VideoExt.add("avi");
        VideoExt.add("MP4");
        VideoExt.add("AVI");
        VideoExt.add("wave");
        VideoExt.add("WAVE");


        studentId= getIntent().getStringExtra("studentId");
        studentName= getIntent().getStringExtra("studentName");
        evidenceId= getIntent().getStringExtra("evidenceId");
        evidenceDate= getIntent().getStringExtra("evidenceDate");
        evidenceComment= getIntent().getStringExtra("evidenceComment");

        evidenceDateTv.setText(evidenceDate);
        studentNameTv.setText(studentName);
        evidenceCommentTv.setText(evidenceComment);
        initilizeMedia();
        getEvidenceMedia();

        createGradeLayout();
    }

    private void createGradeLayout() {
        int totalFrameScore = Integer.parseInt(getIntent().getStringExtra("evidenceScore"));
        int framecount = Integer.parseInt(getIntent().getStringExtra("frameCount"));

        float gradeAverage = totalFrameScore/framecount;
        int gradeWithProcess =Math.round(gradeAverage);

        switch (gradeWithProcess){
            case 10:
                gradePreview.setText("A1");
                gradePreview.setTextColor(Color.parseColor("#2E7D32"));
                gradePreview.setBackgroundResource(R.drawable.green_line);
                break;
            case 9:
                gradePreview.setText("A2");
                gradePreview.setTextColor(Color.parseColor("#2E7D32"));
                gradePreview.setBackgroundResource(R.drawable.green_line);
                break;
            case 8:
                gradePreview.setText("B1");
                gradePreview.setTextColor(Color.parseColor("#2E7D32"));
                gradePreview.setBackgroundResource(R.drawable.green_line);
                break;
            case 7:
                gradePreview.setText("B2");
                gradePreview.setTextColor(Color.parseColor("#2E7D32"));
                gradePreview.setBackgroundResource(R.drawable.green_line);
                break;
            case 6:
                gradePreview.setText("C1");
                gradePreview.setTextColor(Color.parseColor("#efcd37"));
                gradePreview.setBackgroundResource(R.drawable.yellow_line);
                break;
            case 5:
                gradePreview.setText("C2");
                gradePreview.setTextColor(Color.parseColor("#efcd37"));
                gradePreview.setBackgroundResource(R.drawable.yellow_line);
                break;
            case 4:
                gradePreview.setText("D");
                gradePreview.setTextColor(Color.parseColor("#e42f2f"));
                gradePreview.setBackgroundResource(R.drawable.red_line);
                break;
            case 3:
                gradePreview.setText("D");
                gradePreview.setTextColor(Color.parseColor("#e42f2f"));
                gradePreview.setBackgroundResource(R.drawable.red_line);
                break;
            case 2:
                gradePreview.setText("E1");
                gradePreview.setTextColor(Color.parseColor("#e42f2f"));
                gradePreview.setBackgroundResource(R.drawable.red_line);
                break;
            case 1:
                gradePreview.setText("E2");
                gradePreview.setTextColor(Color.parseColor("#e42f2f"));
                gradePreview.setBackgroundResource(R.drawable.red_line);
                break;
            case 0:
                gradePreview.setText("F");
                gradePreview.setTextColor(Color.parseColor("#e42f2f"));
                gradePreview.setBackgroundResource(R.drawable.red_line);
                break;


            default:
                break;
        }
        Log.e("grade",""+gradeWithProcess);
    }


    private void initilizeMedia() {
        adapter = new getMediaAdapter(this,mediaDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent;
                switch (mediaDataList.get(position).getType()){
                    case 0:
                        intent =new Intent(EvidencePreview.this,FullImage.class);
                        intent.putExtra("ImagePath",Utils.webUrlHome+mediaDataList.get(position).getIMAGEPATH());
                        startActivity(intent);
                        break;
                    case 1:
                        intent =new Intent(EvidencePreview.this,videoPlayer.class);
                        intent.putExtra("VideoPath",Utils.webUrlHome+mediaDataList.get(position).getIMAGEPATH());
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        adapterFramework = new getFrameworkPreviewAdapter(this,frameworksList);
        recyclerframeworkPreview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerframeworkPreview.setHasFixedSize(true);

        recyclerframeworkPreview.setAdapter(adapterFramework);

        recyclerframeworkPreview.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerframeworkPreview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }



    private void getEvidenceMedia() {
        if (!checkInternetState.getInstance(EvidencePreview.this).isOnline()) {
            Toasty.warning(EvidencePreview.this, "No Internet connection.", Toast.LENGTH_LONG, true).show();
        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworkScoresModel> userCall = service.get_evidence_media_request(Integer.parseInt(evidenceId));
            userCall.enqueue(new Callback<GetFrameworkScoresModel>() {
                @Override
                public void onResponse(Call<GetFrameworkScoresModel> call, Response<GetFrameworkScoresModel> response) {

                    Log.d("evidences"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                getMediaData mediaData = new getMediaData();
                                mediaData.setIMAGEID(response.body().getData().get(i).getIMAGEID());
                                mediaData.setIMAGEPATH(response.body().getData().get(i).getIMAGEPATH());
                                mediaData.setIMAGETIMESTAMP(response.body().getData().get(i).getIMAGETIMESTAMP());
                                Log.e("getMediaPath",""+response.body().getData().get(i).getIMAGEPATH());

                                String[] extension = response.body().getData().get(i).getIMAGEPATH().split("\\.");

                                if (imageExt.contains(extension[1])){
                                    mediaData.setType(0);
                                }
                                if (VideoExt.contains(extension[1])){
                                    mediaData.setType(1);
                                }

                               Log.d("extension",extension[1]);

                                mediaDataList.add(mediaData);
                                //adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                            }

                            for (int i = 0; i < response.body().getData1().size(); i++) {
                                getScoreData scoreData = new getScoreData();
                                scoreData.setFRAMEWORKID(response.body().getData1().get(i).getFRAMEWORKID());
                                scoreData.setFRAMEWORKTITLE(response.body().getData1().get(i).getFRAMEWORKTITLE());
                                scoreData.setFRAMEWORKSUB(response.body().getData1().get(i).getFRAMEWORKSUB());
                                scoreData.setfRAMEWORKREMARK(response.body().getData1().get(i).getfRAMEWORKREMARK());
                                scoreData.setSCORE(response.body().getData1().get(i).getSCORE());
                                scoreData.setSCOREID(response.body().getData1().get(i).getSCOREID());
                                scoreData.setMAXSCORE(response.body().getData1().get(i).getMAXSCORE());

                                frameworkTitle.setText(response.body().getData1().get(i).getFRAMEWORKTITLE());
                                Log.e("getMediaPath",""+response.body().getData1().get(i).getFRAMEWORKSUB());

                                frameworksList.add(scoreData);
                                //adapter.notifyDataSetChanged();
                                adapterFramework.notifyDataSetChanged();
                            }

                        }else{
                            frameworkTitle.setVisibility(View.GONE);
                            noMediaFound.setVisibility(View.VISIBLE);
                            noFrameworkAvailable.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(EvidencePreview.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<GetFrameworkScoresModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
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
