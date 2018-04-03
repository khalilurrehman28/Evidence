package com.dupleit.kotlin.primaryschoolassessment.getStudents.UI;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.activities.Login.UI.LoginActivity;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.adapter.getStudentsAdapter;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.models.GetStudentData;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.models.GetStudentsModel;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getClassStudents extends AppCompatActivity implements getStudentsAdapter.StudentsAdapterListener{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.noStudentsFound) TextView noStudentsFound;
    private List<GetStudentData> studentList;
    private getStudentsAdapter adapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_class_students);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//to hide keyboad
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        initialize();


    }

    private void initialize() {
        studentList = new ArrayList<>();
        adapter = new getStudentsAdapter(this, studentList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!getTeacherEmail().equals("")){
            progressBar.setVisibility(View.VISIBLE);
            prepareStudentList();

        }else {
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            Toasty.warning(this, "Please login first", Toast.LENGTH_SHORT, true).show();

        }

       /* recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final GetStudentData students = studentList.get(position);

                Intent intent = new Intent();
                intent.putExtra("studentName", students.getSTUDENTNAME());
                intent.putExtra("studentId", students.getSTUDENTID());
                setResult(RESULT_OK, intent);
                finish();

            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_get_students, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }


    @Override
    public void onContactSelected(GetStudentData students) {
        Intent intent = new Intent();
        intent.putExtra("studentName", students.getSTUDENTNAME());
        intent.putExtra("studentId", students.getSTUDENTID());
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
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

    private void prepareStudentList() {
        noStudentsFound.setVisibility(View.GONE);
        if (!checkInternetState.getInstance(this).isOnline()) {
            progressBar.setVisibility(View.GONE);
            noStudentsFound.setVisibility(View.VISIBLE);
            noStudentsFound.setText("No Internet Found.");
        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetStudentsModel> userCall = service.getAllStudents(Integer.parseInt(sharedId()));
            userCall.enqueue(new Callback<GetStudentsModel>() {
                @Override
                public void onResponse(Call<GetStudentsModel> call, Response<GetStudentsModel> response) {
                    progressBar.setVisibility(View.GONE);

                    //Log.d("students"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            noStudentsFound.setVisibility(View.GONE);
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                GetStudentData students = new GetStudentData();
                                students.setSTATUS(response.body().getData().get(i).getSTATUS());
                                students.setSTUDENTNAME(response.body().getData().get(i).getSTUDENTNAME());
                                students.setSTUDENTID(response.body().getData().get(i).getSTUDENTID());
                                students.setSTUDENTACTIVATION(response.body().getData().get(i).getSTUDENTACTIVATION());
                                students.setSTUDENTADDRESSPERMANENT(response.body().getData().get(i).getSTUDENTADDRESSPERMANENT());
                                students.setSTUDENTCLASS(response.body().getData().get(i).getSTUDENTCLASS());
                                students.setSTUDENTCONTACTEMAIL(response.body().getData().get(i).getSTUDENTCONTACTEMAIL());
                                students.setSTUDENTCONTACTPRIMARY(response.body().getData().get(i).getSTUDENTCONTACTPRIMARY());
                                students.setSTUDENTCONTACTSECONDARY(response.body().getData().get(i).getSTUDENTCONTACTSECONDARY());students.setSTUDENTDATETIME(response.body().getData().get(i).getSTUDENTDATETIME());
                                students.setSTUDENTGENDER(response.body().getData().get(i).getSTUDENTGENDER());
                                students.setSTUDENTDOB(response.body().getData().get(i).getSTUDENTDOB());
                                students.setSTUDENTFATHERNAME(response.body().getData().get(i).getSTUDENTFATHERNAME());
                                students.setSTUDENTMOTHERNAME(response.body().getData().get(i).getSTUDENTMOTHERNAME());
                                students.setSTUDENTIMAGE(response.body().getData().get(i).getSTUDENTIMAGE());
                                students.setSTUDENTSESSION(response.body().getData().get(i).getSTUDENTSESSION());
                                students.setSTUDENTMODIFYDATETIME(response.body().getData().get(i).getSTUDENTMODIFYDATETIME());
                                studentList.add(students);
                                //adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                            }

                        }else{
                            noStudentsFound.setVisibility(View.VISIBLE);
                            noStudentsFound.setText("No students found");

                        }
                    }else{
                        Toast.makeText(getClassStudents.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<GetStudentsModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    progressBar.setVisibility(View.GONE);

                }
            });
        }

    }

    /**
     * Converting dp to pixel
     */
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


}