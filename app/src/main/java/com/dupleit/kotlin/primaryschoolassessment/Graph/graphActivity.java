package com.dupleit.kotlin.primaryschoolassessment.Graph;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dupleit.kotlin.primaryschoolassessment.Graph.model.GetMonthsBarGraphModel;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.PieChart.model.GetSessionPieData;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.ScreenshotType;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.ScreenshotUtils;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class graphActivity extends AppCompatActivity {
    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA;
    ProgressDialog pDialog;
    String studentId,studentName;
    private LinearLayout rootContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rootContent = findViewById(R.id.root_content);

        chart = (BarChart) findViewById(R.id.chart1);
        studentId= getIntent().getStringExtra("studentId");
        studentName= getIntent().getStringExtra("studentName");
        setTitle("Monthly performance of "+studentName);
        BARENTRY = new ArrayList<>();


        BarEntryLabels = new ArrayList<String>();

        //AddValuesToBARENTRY();

        //AddValuesToBarEntryLabels();
        hitApiForBarGraph();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.bar_graph, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(2, 0));
        BARENTRY.add(new BarEntry(4, 1));
        BARENTRY.add(new BarEntry(6.5f, 2));
        BARENTRY.add(new BarEntry(8, 3));
        BARENTRY.add(new BarEntry(7, 4));
        BARENTRY.add(new BarEntry(3, 5));
        BARENTRY.add(new BarEntry(5, 6));
        BARENTRY.add(new BarEntry(6, 7));
        BARENTRY.add(new BarEntry(9, 8));
        BARENTRY.add(new BarEntry(8, 9));
        BARENTRY.add(new BarEntry(1, 10));
        BARENTRY.add(new BarEntry(5, 11));


    }

    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("Jan");
        BarEntryLabels.add("Feb");
        BarEntryLabels.add("March");
        BarEntryLabels.add("April");
        BarEntryLabels.add("May");
        BarEntryLabels.add("June");
        BarEntryLabels.add("July");
        BarEntryLabels.add("Aug");
        BarEntryLabels.add("Sep");
        BarEntryLabels.add("Oct");
        BarEntryLabels.add("Nov");
        BarEntryLabels.add("Dec");


    }

    private void hitApiForBarGraph() {
        pDialog = new ProgressDialog(this);
        //pDialog.setIndeterminate(true);
        pDialog.setTitle("Creating Bar Graph");
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        if (!checkInternetState.getInstance(graphActivity.this).isOnline()) {
            Toasty.warning(graphActivity.this, "No Internet connection.", Toast.LENGTH_LONG, true).show();
            hidepDialog();
        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetMonthsBarGraphModel> userCall = service.bar_graph_request(Integer.parseInt(studentId));
            userCall.enqueue(new Callback<GetMonthsBarGraphModel>() {
                @Override
                public void onResponse(Call<GetMonthsBarGraphModel> call, Response<GetMonthsBarGraphModel> response) {
                    hidepDialog();
                    Log.d("evidences"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                int MonthIndex = 0;
                                String month[] = response.body().getData().get(i).getDate().split("-");
                                switch (month[1]) {
                                    case "01":
                                        MonthIndex= 0;
                                        break;
                                    case "02":
                                        MonthIndex= 1;
                                        break;
                                    case "03":
                                        MonthIndex= 2;
                                        break;
                                    case "04":
                                        MonthIndex= 3;
                                        break;
                                    case "05":
                                        MonthIndex= 4;
                                        break;
                                    case "06":
                                        MonthIndex= 5;
                                        break;
                                    case "07":
                                        MonthIndex= 6;
                                        break;
                                    case "08":
                                        MonthIndex= 7;
                                        break;
                                    case "09":
                                        MonthIndex= 8;
                                        break;
                                    case "10":
                                        MonthIndex= 9;
                                        break;
                                    case "11":
                                        MonthIndex= 10;
                                        break;
                                    case "12":
                                        MonthIndex= 11;
                                        break;
                                    default:

                                        break;
                                }
                                float score = Float.parseFloat(response.body().getData().get(i).getScore())/Float.parseFloat(response.body().getData().get(i).getCount());
                                String s = String.format("%.2f", score);
                                BARENTRY.add(new BarEntry(Float.parseFloat(s), MonthIndex));
                                AddValuesToBarEntryLabels();
                                Log.e("BarData",""+ "PieNames "+response.body().getData().get(i).getScore()+" PieScore "+response.body().getData().get(i).getScore());
                            }

                            final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(0,229,238), Color.rgb(255,192,0),
                                    Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)
                                    , Color.rgb(0,128,128), Color.rgb(0,139,69),Color.rgb(255,215,0),Color.rgb(255,128,0)
                                    ,Color.rgb(255,106,106)};
                            ArrayList<Integer> colors = new ArrayList<Integer>();

                            for(int c: MY_COLORS) colors.add(c);

                            Bardataset = new BarDataSet(BARENTRY, "Projects");

                            BARDATA = new BarData(BarEntryLabels, Bardataset);

                            Bardataset.setColors(colors);

                            chart.setData(BARDATA);

                            chart.animateY(3000);

                        }else{

                            Toasty.warning(graphActivity.this, "data not found", Toast.LENGTH_LONG, true).show();

                        }
                    }else{
                        Toasty.error(graphActivity.this, "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }
                }

                @Override
                public void onFailure(Call<GetMonthsBarGraphModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                    Toasty.error(graphActivity.this, "Fail"+t.toString(), Toast.LENGTH_LONG, true).show();
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
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Monthly performance of "+studentName);
        intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }
}
