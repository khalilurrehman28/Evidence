package com.dupleit.kotlin.primaryschoolassessment.PieChart.PreviewPieChart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.PieChart.model.GetSessionPieData;
import com.dupleit.kotlin.primaryschoolassessment.PieChart.model.GetSessionPieRecordModel;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.PieChart.PreviewPieChart.PieChartActivity;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.ScreenshotType;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.ScreenshotUtils;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PieChartActivity extends AppCompatActivity {
    String startDate,endDate,studentId,studentName,FrameworkId,FrameWorkTitle;
    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    private LinearLayout rootContent;
    PieData pieData ;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Session progress in %");
        rootContent = findViewById(R.id.root_content);
        studentId= getIntent().getStringExtra("studentId");
        studentName= getIntent().getStringExtra("studentName");
        startDate= getIntent().getStringExtra("startDate");
        endDate= getIntent().getStringExtra("endDate");
        FrameworkId= getIntent().getStringExtra("FrameworkId");
        FrameWorkTitle= getIntent().getStringExtra("FrameWorkTitle");

        Log.e("data",""+studentId+" "+studentName+" "+startDate+" "+endDate+" "+FrameworkId);

        pieChart = (PieChart) findViewById(R.id.chart1);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();
        hitApiForPieChart();

        //AddValuesToPIEENTRY();

        //AddValuesToPieEntryLabels();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.bar_graph, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void AddValuesToPIEENTRY(){

        entries.add(new BarEntry(2, 0));
        entries.add(new BarEntry(4, 1));
        entries.add(new BarEntry(6, 2));
        entries.add(new BarEntry(8, 3));
        entries.add(new BarEntry(7, 4));
        //entries.add(new BarEntry(3, 5));

    }

    public void AddValuesToPieEntryLabels(){

        PieEntryLabels.add("Arts");
        PieEntryLabels.add("Maths");
        PieEntryLabels.add("English");
       // PieEntryLabels.add("Hindi");
        PieEntryLabels.add("Science");
        PieEntryLabels.add("Cricket");

    }

    private void hitApiForPieChart() {
        pDialog = new ProgressDialog(this);
        //pDialog.setIndeterminate(true);
        pDialog.setTitle("Creating Pie Chart");
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        if (!checkInternetState.getInstance(PieChartActivity.this).isOnline()) {
            Toasty.warning(PieChartActivity.this, "No Internet connection.", Toast.LENGTH_LONG, true).show();
            hidepDialog();
        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetSessionPieRecordModel> userCall = service.pie_chart_request(Integer.parseInt(studentId));
            userCall.enqueue(new Callback<GetSessionPieRecordModel>() {
                @Override
                public void onResponse(Call<GetSessionPieRecordModel> call, Response<GetSessionPieRecordModel> response) {
                    hidepDialog();
                    Log.d("evidences"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                GetSessionPieData pieData1 = new GetSessionPieData();

                                float totalScore = Float.parseFloat(response.body().getData().get(i).getCount())*10;
                                float score = (Float.parseFloat(response.body().getData().get(i).getScore())/totalScore )*100;
                                String s = String.format("%.2f", score);
                                entries.add(new BarEntry(Float.parseFloat(s), i));
                                PieEntryLabels.add(response.body().getData().get(i).getTitle());

                                Log.e("pieData",""+ "PieNames "+response.body().getData().get(i).getTitle()+" PieScore "+response.body().getData().get(i).getScore());
                            }
                            final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(0,229,238), Color.rgb(255,192,0),
                                    Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)
                                    , Color.rgb(0,128,128), Color.rgb(0,139,69),Color.rgb(255,215,0),Color.rgb(255,128,0)
                                    ,Color.rgb(255,106,106)};
                            ArrayList<Integer> colors = new ArrayList<Integer>();

                            for(int c: MY_COLORS) colors.add(c);

                            pieDataSet = new PieDataSet(entries, "");
                            pieData = new PieData(PieEntryLabels, pieDataSet);
                            pieDataSet.setColors(colors);
                            pieChart.setData(pieData);
                            pieChart.setCenterTextSize(32);
                            pieChart.setCenterText("%");
                            //pieChart.setUsePercentValues(true);
                            pieDataSet.setValueTextSize(18);
                           // pieDataSet.setValueTextColor(Integer.parseInt("#ffffff"));
                            pieChart.animateY(3000);

                        }else{

                            Toasty.warning(PieChartActivity.this, "data not found", Toast.LENGTH_LONG, true).show();

                        }
                    }else{
                        Toasty.error(PieChartActivity.this, "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }
                }

                @Override
                public void onFailure(Call<GetSessionPieRecordModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                    //Toasty.error(PieChartActivity.this, "Fail"+t.toString(), Toast.LENGTH_LONG, true).show();
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

        if (id == R.id.shareScreenShot){
            takeScreenshot(ScreenshotType.FULL);
        }
        return super.onOptionsItemSelected(item);
    }

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        switch (screenshotType) {
            case FULL:
                //If Screenshot type is FULL take full page screenshot i.e our root content.
                b = ScreenshotUtils.getScreenShot(rootContent);
                break;

        }

        //If bitmap is not null
        if (b != null) {

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);//save the screenshot to selected path
            shareScreenshot(file);//finally share screenshot
        } else
            //If bitmap is null show toast message
            Toast.makeText(this, "Failed to take screenshot!!", Toast.LENGTH_SHORT).show();
    }

    private void shareScreenshot(File file) {
        Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Session Progress of " +studentName);
        intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }
}
