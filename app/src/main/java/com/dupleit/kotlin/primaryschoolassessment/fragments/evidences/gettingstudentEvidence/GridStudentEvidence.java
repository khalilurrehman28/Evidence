package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.dupleit.kotlin.primaryschoolassessment.Graph.graphActivity;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.PieChart.PreviewPieChart.PieChartActivity;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.activities.Login.UI.LoginActivity;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.EvidencePreview;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.DownloadPdfModel.DownloadApi;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.adapter.getEvidneceByStudentAdapter;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.models.EvidencesData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.models.GetEvidenceModel;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.CustomObjectComparator;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.RecyclerItemClickListener1;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridStudentEvidence extends AppCompatActivity implements getEvidneceByStudentAdapter.EvidenceAdapterListener,ActionMode.Callback{
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 101;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noStudentsFound)
    TextView noStudentsFound;
    @BindView(R.id.btnSort) LinearLayout btnSort;
    @BindView(R.id.btnFilter) LinearLayout btnFilter;
    @BindView(R.id.noSearchResultFound)
    TextView noSearchResultFound;
    List<Integer> ColorArray;
    Random random;
    private ArrayList<EvidencesData> studentList;
    private getEvidneceByStudentAdapter adapter;
    String studentId,studentName,activityType;
    private boolean ascending = true;
    SearchView searchView;
    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    private DownloadManager downloadManager;
    private long refid;
    String[] url;

    //i created List of int type to store id of data, you can create custom class type data according to your need.
    private List<String> selectedIds ;
    private static final int PERMISSION_REQUEST_CODE = 1;
    ArrayList<Long> selctedCardslist = new ArrayList<>();
    ProgressDialog pDialog;
    ArrayList<String> pieFrameworkNames = new ArrayList<String>();
    ArrayList<Integer> pieFrameworkScore = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_student_evidence);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//to hide keyboad
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);



        studentId= getIntent().getStringExtra("studentId");
        studentName= getIntent().getStringExtra("studentName");
        activityType= getIntent().getStringExtra("activityType");

        if (activityType.equals("AllEvidence")){
            setTitle("Evidence of "+studentName);
        }else if (activityType.equals("AssessmentRecord")){
            setTitle("Select Evidence");
        }
        initilize();

        registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            Log.e("IN", "" + referenceId);

            selctedCardslist.remove(referenceId);


            if (selctedCardslist.isEmpty())
            {




                NotificationCompat.Builder builder = new NotificationCompat.Builder(GridStudentEvidence.this);
                builder.setSmallIcon(R.drawable.ic_pdf);
               // File file = new File(Environment.DIRECTORY_DOWNLOADS, "/evidence"  +"/"+ "Evidence_report_"+url[1]);
                Intent intent1 = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                PendingIntent pendingIntent = PendingIntent.getActivity(GridStudentEvidence.this, 0, intent1, 0);
                builder.setContentIntent(pendingIntent);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                builder.setContentTitle("Evidence_report_"+ url[1]);
                builder.setContentText("Download completed");
                //builder.setSubText("Tap to view the website.");



             /*   NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(GridStudentEvidence.this)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                .setSmallIcon(R.drawable.ic_pdf)
                                .setContentTitle("Evidence_report_"+ url[1])
                                .setContentText("Download completed");*/

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, builder.build());

                Toasty.success(getApplicationContext(),"file download in evidence folder",Toast.LENGTH_LONG).show();



            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.grid_evidence, menu);
        MenuItem menugraph = menu.findItem(R.id.showGraph);

        if (activityType.equals("AllEvidence")){
            menugraph.setVisible(false);
        }else if (activityType.equals("AssessmentRecord")){
            menugraph.setVisible(true);
        }

        MenuItem item = menu.findItem(R.id.searchEvidences);

        searchView = (SearchView)item.getActionView();
        searchView.setQueryHint("Search date/framework...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query.trim());
                if(adapter.getItemCount()<1){
                    recyclerView.setVisibility(View.GONE);
                    noSearchResultFound.setVisibility(View.VISIBLE);
                    noSearchResultFound.setText("No results found '"+query.trim()+"'");
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noSearchResultFound.setVisibility(View.GONE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.trim());
                if(adapter.getItemCount()<1){
                    recyclerView.setVisibility(View.GONE);
                    noSearchResultFound.setVisibility(View.VISIBLE);
                    noSearchResultFound.setText("No results found '"+newText.trim()+"'");
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noSearchResultFound.setVisibility(View.GONE);
                }return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setVisibility(View.VISIBLE);
                noSearchResultFound.setVisibility(View.GONE);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void initilize() {
        ColorArray = new ArrayList<>();
        random = new Random();
        studentList = new ArrayList<>();
        adapter = new getEvidneceByStudentAdapter(this, studentList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(0,229,238), Color.rgb(255,192,0),
                Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)
                , Color.rgb(0,128,128), Color.rgb(0,139,69),Color.rgb(255,215,0),Color.rgb(255,128,0)
                ,Color.rgb(255,106,106)};
        for (int item : MY_COLORS) {
            ColorArray.add(item);
        }
        if (!getTeacherEmail().equals("")){
            progressBar.setVisibility(View.VISIBLE);
            prepareEvidenceList();

        }else {
            Intent i = new Intent(GridStudentEvidence.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            Toasty.warning(GridStudentEvidence.this, "Please login first", Toast.LENGTH_SHORT, true).show();

        }
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortData(ascending);
                ascending = !ascending;
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GridStudentEvidence.this, filterActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!searchView.isIconified()) {
                            searchView.onActionViewCollapsed();

                        }
                    }
                }, 140);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener1(this, recyclerView, new RecyclerItemClickListener1.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EvidencesData evidences = studentList.get(position);
                if (activityType.equals("AllEvidence")){
                   /* Intent i = new Intent(GridStudentEvidence.this, EvidencePreview.class);
                    i.putExtra("studentId", studentId);
                    i.putExtra("studentName", studentName);
                    i.putExtra("evidenceId", evidences.getEVIDENCEID());
                    i.putExtra("evidenceDate", evidences.getEVIDENCEDATE());
                    i.putExtra("evidenceComment", evidences.getEVIDENCECOMMENT());

                    startActivity(i);*/
                }else if (activityType.equals("AssessmentRecord")){
                    if (isMultiSelect){
                        //if multiple selection is enabled then select item on single click else perform normal click on item.
                        multiSelect(position);
                    }


                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (activityType.equals("AllEvidence")){

                }else if (activityType.equals("AssessmentRecord")){
                    if (!isMultiSelect){
                        selectedIds = new ArrayList<>();
                        isMultiSelect = true;

                        if (actionMode == null){
                            actionMode = startActionMode(GridStudentEvidence.this); //show ActionMode.
                        }
                    }

                    multiSelect(position);


                }

            }
        }));
    }

    private void multiSelect(int position) {
        EvidencesData data =  adapter.getItem(position);
        if (data != null){
            if (actionMode != null) {
                if (selectedIds.contains(data.getEVIDENCEID()))
                    selectedIds.remove(data.getEVIDENCEID());
                else
                    selectedIds.add(data.getEVIDENCEID());

                if (selectedIds.size() > 0)
                    actionMode.setTitle(String.valueOf(selectedIds.size())+" Selected"); //show selected item count on action mode.
                else{
                    actionMode.setTitle(""); //remove item count from action mode.
                    actionMode.finish(); //hide action mode.
                }
                adapter.setSelectedIds(selectedIds);

            }
        }
    }

    @Override
    public void onEvidenceSelected(EvidencesData evidences) {
        if (activityType.equals("AllEvidence")){
            Intent i = new Intent(GridStudentEvidence.this, EvidencePreview.class);
            i.putExtra("studentId", studentId);
            i.putExtra("studentName", studentName);
            i.putExtra("evidenceId", evidences.getEVIDENCEID());
            i.putExtra("evidenceDate", evidences.getEVIDENCEDATE());
            i.putExtra("evidenceComment", evidences.getEVIDENCECOMMENT());
            i.putExtra("evidenceScore", evidences.getsCORE());
            i.putExtra("frameCount", String.valueOf(evidences.getcOUNT()));
            i.putExtra("evidenceTags", evidences.geteVIDENCETAGS());

            startActivity(i);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!searchView.isIconified()) {
                        searchView.onActionViewCollapsed();

                    }
                }
            }, 140);
        }else if (activityType.equals("AssessmentRecord")){
            // Toast.makeText(GridStudentEvidence.this, "assessmentRecord", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case SECOND_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    // get String data from Intent
                    String startDate= data.getStringExtra("startDate");
                    String endDate = data.getStringExtra("endDate");
                    prepareEvidenceListWithFiltering(startDate,endDate);

                }
                break;

            default:

                break;
        }


    }

    private void prepareEvidenceListWithFiltering(String startDate, String endDate) {
        studentList.clear();
        noStudentsFound.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if (!checkInternetState.getInstance(GridStudentEvidence.this).isOnline()) {
            Toasty.warning(GridStudentEvidence.this, "No Internet connection.", Toast.LENGTH_LONG, true).show();

            progressBar.setVisibility(View.GONE);
        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetEvidenceModel> userCall = service.get_evidence_withdate_request(startDate,endDate,Integer.parseInt(studentId));
            userCall.enqueue(new Callback<GetEvidenceModel>() {
                @Override
                public void onResponse(Call<GetEvidenceModel> call, Response<GetEvidenceModel> response) {
                    progressBar.setVisibility(View.GONE);

                    Log.d("evidences"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                int  n = random.nextInt(ColorArray.size());
                                if (n==ColorArray.size()){
                                    n -=1;
                                }
                                EvidencesData evidences = new EvidencesData();
                                evidences.setEVIDENCEID(response.body().getData().get(i).getEVIDENCEID());
                                evidences.setEVIDENCEDATE(response.body().getData().get(i).getEVIDENCEDATE());
                                evidences.setEVIDENCEFRAMEWORKID(response.body().getData().get(i).getEVIDENCEFRAMEWORKID());
                                evidences.setEVIDENCECOMMENT(response.body().getData().get(i).getEVIDENCECOMMENT());
                                evidences.setTitle(response.body().getData().get(i).getTitle());
                                evidences.setsCORE(response.body().getData().get(i).getsCORE());
                                evidences.setcOUNT(response.body().getData().get(i).getcOUNT());
                                evidences.seteVIDENCETAGS(response.body().getData().get(i).geteVIDENCETAGS());
                                evidences.setColorIndex(ColorArray.get(n));
                                studentList.add(evidences);
                                //adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                            }

                        }else{
                            noStudentsFound.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toasty.warning(GridStudentEvidence.this, "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }
                }

                @Override
                public void onFailure(Call<GetEvidenceModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    progressBar.setVisibility(View.GONE);

                }
            });
        }
    }

    private void sortData(boolean ascendingDes) {
        Collections.sort(studentList, new CustomObjectComparator(ascendingDes));
        adapter.notifyDataSetChanged();

    }

    private void prepareEvidenceList() {

        if (!checkInternetState.getInstance(GridStudentEvidence.this).isOnline()) {
            progressBar.setVisibility(View.GONE);
            Toasty.warning(GridStudentEvidence.this, "No Internet connection.", Toast.LENGTH_LONG, true).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetEvidenceModel> userCall = service.get_evidence_request(Integer.parseInt(studentId));
            userCall.enqueue(new Callback<GetEvidenceModel>() {
                @Override
                public void onResponse(Call<GetEvidenceModel> call, Response<GetEvidenceModel> response) {
                    progressBar.setVisibility(View.GONE);

                    Log.d("evidences"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                int  n = random.nextInt(ColorArray.size());
                                if (n==ColorArray.size()){
                                    n -=1;
                                }
                                EvidencesData evidences = new EvidencesData();
                                evidences.setEVIDENCEID(response.body().getData().get(i).getEVIDENCEID());
                                evidences.setEVIDENCEDATE(response.body().getData().get(i).getEVIDENCEDATE());
                                evidences.setEVIDENCEFRAMEWORKID(response.body().getData().get(i).getEVIDENCEFRAMEWORKID());
                                evidences.setEVIDENCECOMMENT(response.body().getData().get(i).getEVIDENCECOMMENT());
                                evidences.setTitle(response.body().getData().get(i).getTitle());
                                evidences.setsCORE(response.body().getData().get(i).getsCORE());
                                evidences.setcOUNT(response.body().getData().get(i).getcOUNT());
                                evidences.seteVIDENCETAGS(response.body().getData().get(i).geteVIDENCETAGS());
                                evidences.setColorIndex(ColorArray.get(n));
                                studentList.add(evidences);
                                //adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                            }

                        }else{
                            noStudentsFound.setVisibility(View.VISIBLE);

                        }
                    }else{
                        Toasty.error(GridStudentEvidence.this, "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }
                }

                @Override
                public void onFailure(Call<GetEvidenceModel> call, Throwable t) {
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

    private String sharedId() {
        return new PreferenceManager(this).getUserID();
    }
    private String getTeacherEmail() {
        return new PreferenceManager(this).getUserEmail();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.showGraph){
            final CharSequence[] options = {/*"Image (Camera)",*/"Month Performance","Session Performance" };

            AlertDialog.Builder builder = new AlertDialog.Builder(GridStudentEvidence.this);

            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Month Performance")) {

                        Intent i = new Intent(GridStudentEvidence.this, graphActivity.class);
                        i.putExtra("studentId", studentId);
                        i.putExtra("studentName", studentName);

                        startActivity(i);

                    } else if (options[item].equals("Session Performance")) {
                        Intent i = new Intent(GridStudentEvidence.this, PieChartActivity.class);
                        i.putExtra("studentId", studentId);
                        i.putExtra("studentName", studentName);

                        startActivity(i);

                    }

                }
            });
            builder.show();


            return true;
        }

        if (id == R.id.searchEvidences) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }




    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_create_pdf:
                //just to show selected items.
                if(checkPermission()){
                    getPdfUrlFromServer();


                } else {
                    requestPermission();
                }

                return true;
        }
        return false;
    }

    private void getPdfUrlFromServer() {
        String evidenceID ="";
        for (EvidencesData data : studentList) {
            if (selectedIds.contains(data.getEVIDENCEID())){

                evidenceID = data.getEVIDENCEID()+","+evidenceID;
            }
        }

        Log.d("evidenceID",""+evidenceID);


        if (!checkInternetState.getInstance(GridStudentEvidence.this).isOnline()) {
            //progressBar.setVisibility(View.GONE);
            Toasty.warning(GridStudentEvidence.this, "No Internet connection.", Toast.LENGTH_LONG, true).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<DownloadApi> userCall = service.pdf_request(evidenceID,studentId);
            userCall.enqueue(new Callback<DownloadApi>() {
                @Override
                public void onResponse(Call<DownloadApi> call, Response<DownloadApi> response) {
                    //progressBar.setVisibility(View.GONE);

                    Log.d("evidences"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            selctedCardslist.clear();
                            Toast.makeText(GridStudentEvidence.this, "Start downloading...", Toast.LENGTH_SHORT).show();
                            url = response.body().getData().split("orderlist/");

                            DownloadManager.Request request = new DownloadManager.Request( Uri.parse(response.body().getData()));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(false);
                            request.setTitle("Downloading " + "PDF");
                            request.setDescription("Downloading evidence" + url[1]);
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/evidence"  +"/"+ "Evidence_report_"+url[1]);


                            refid = downloadManager.enqueue(request);


                            Log.e("OUT", "" + refid);

                            selctedCardslist.add(refid);
                        }else{
                            //noStudentsFound.setVisibility(View.VISIBLE);
                            Toasty.error(GridStudentEvidence.this, "file not download", Toast.LENGTH_LONG, true).show();

                        }
                    }else{
                        Toasty.error(GridStudentEvidence.this, "Something went wrong", Toast.LENGTH_LONG, true).show();
                    }
                }

                @Override
                public void onFailure(Call<DownloadApi> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    progressBar.setVisibility(View.GONE);

                }
            });
        }

    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        isMultiSelect = false;
        selectedIds = new ArrayList<>();
        adapter.setSelectedIds(new ArrayList<String>());
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission(){

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   /* for (EvidencesData data : studentList) {
                        if (selectedIds.contains(data.getEVIDENCEID())){
                            //stringBuilder.append(",").append(data.getEVIDENCEID());
                            selectedEvidences.add(data.getEVIDENCEID());
                            *//*for (int i = 0; i < selectedEvidences.size(); i++) {
                                Toast.makeText(this, "Selected items are :" + selectedEvidences.get(i), Toast.LENGTH_SHORT).show();

                            }*//*
                        }

                    }*/

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }
}

