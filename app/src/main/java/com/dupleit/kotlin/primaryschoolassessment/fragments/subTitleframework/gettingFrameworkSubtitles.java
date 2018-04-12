package com.dupleit.kotlin.primaryschoolassessment.fragments.subTitleframework;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.GetFrameworksubtitleModel;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.SubTitleData;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.subTitleframework.adapter.frameworksubTitlesAdapter;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.RecyclerTouchListener;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import com.mancj.slideup.SlideUp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class gettingFrameworkSubtitles extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noFrameworksFound)
    TextView noFrameworksFound;
    private List<SubTitleData> frameworksubTList;
    frameworksubTitlesAdapter subTitleAdapter;
    View mView;
    private SlideUp slideUp;
    @BindView(R.id.dim) View dim;
    @BindView(R.id.slideView) View sliderView;
    @BindView(R.id.btnClose)ImageView btnClose;
    @BindView(R.id.tvTitle)TextView tvTitle;
    @BindView(R.id.tvDes)TextView tvDes;
    @BindView(R.id.tvMaxScore)TextView tvMaxScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_framework_subtitles);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initilize();
        setTitle("Preview of "+getIntent().getStringExtra("frameworkTitle"));
    }

    private void initilize() {

        slideUp = new SlideUp(sliderView);
        slideUp.hideImmediately();
        slideUp.setSlideListener(new SlideUp.SlideListener() {
            @Override
            public void onSlideDown(float v) {
                dim.setAlpha(1 - (v / 100));
            }

            @Override
            public void onVisibilityChanged(int i) {
                if (i == View.GONE) {
                    //mMap.mar
                }

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp.animateOut();
            }
        });
        frameworksubTList = new ArrayList<>();
        subTitleAdapter = new frameworksubTitlesAdapter(this, frameworksubTList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subTitleAdapter);
        progressBar.setVisibility(View.VISIBLE);
        prepareSubtitles();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(gettingFrameworkSubtitles.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final SubTitleData frames = frameworksubTList.get(position);
                if (!frames.getFRAMEWORKSUB().equals("")){
                    tvTitle.setText(frames.getFRAMEWORKSUB());

                }
                String s= "Description:  "+frames.getrEMARK();
                SpannableString ss1=  new SpannableString(s);
                ss1.setSpan(new RelativeSizeSpan(1.3f), 0,12, 0); // set size
                ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 12, 0);// set color

                tvDes.setText(ss1);

                String s1= "Max Score: "+frames.getSCORE();
                SpannableString marks=  new SpannableString(s1);
                marks.setSpan(new RelativeSizeSpan(1f), 0,10, 0); // set size
                marks.setSpan(new ForegroundColorSpan(Color.RED), 0, 10, 0);// set color

                tvMaxScore.setText(marks);

                slideUp.animateIn();
            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));
    }

    private void prepareSubtitles() {
        noFrameworksFound.setVisibility(View.GONE);
        if (!checkInternetState.getInstance(this).isOnline()) {
            progressBar.setVisibility(View.GONE);
            noFrameworksFound.setVisibility(View.VISIBLE);
            noFrameworksFound.setText("No Internet Connection");
        }else {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworksubtitleModel> userCall = service.get_framework_subtitle(Integer.parseInt(getIntent().getStringExtra("frameworkId")));
            userCall.enqueue(new Callback<GetFrameworksubtitleModel>() {
                @Override
                public void onResponse(Call<GetFrameworksubtitleModel> call, Response<GetFrameworksubtitleModel> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            noFrameworksFound.setVisibility(View.GONE);

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                SubTitleData SubTitleData= new SubTitleData();
                                SubTitleData.setFRAMEWORKSTATUS(response.body().getData().get(i).getFRAMEWORKSTATUS());
                                SubTitleData.setFRAMEWORKSUB(response.body().getData().get(i).getFRAMEWORKSUB());
                                SubTitleData.setSCORE(response.body().getData().get(i).getSCORE());
                                SubTitleData.setSubId(response.body().getData().get(i).getSubId());
                                SubTitleData.setrEMARK(response.body().getData().get(i).getrEMARK());

                                frameworksubTList.add(SubTitleData);
                                //adapter.notifyDataSetChanged();
                                subTitleAdapter.notifyDataSetChanged();
                            }

                        } else {
                            noFrameworksFound.setVisibility(View.VISIBLE);
                            noFrameworksFound.setText("No frameworks subtitles found.");
                        }
                    }else {
                        Toast.makeText(gettingFrameworkSubtitles.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetFrameworksubtitleModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
