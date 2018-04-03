package com.dupleit.kotlin.primaryschoolassessment.fragments.subTitleframework;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.GetFrameworksubtitleModel;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.SubTitleData;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.subTitleframework.adapter.frameworksubTitlesAdapter;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

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
        frameworksubTList = new ArrayList<>();
        subTitleAdapter = new frameworksubTitlesAdapter(this, frameworksubTList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subTitleAdapter);
        progressBar.setVisibility(View.VISIBLE);
        prepareSubtitles();
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
