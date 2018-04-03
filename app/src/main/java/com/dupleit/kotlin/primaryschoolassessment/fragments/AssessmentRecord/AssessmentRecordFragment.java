package com.dupleit.kotlin.primaryschoolassessment.fragments.AssessmentRecord;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.activities.Login.UI.LoginActivity;
import com.dupleit.kotlin.primaryschoolassessment.activities.studentProfile.studentProfile;
import com.dupleit.kotlin.primaryschoolassessment.fragments.AssessmentRecord.adapter.assessmentRecordAdapter;
import com.dupleit.kotlin.primaryschoolassessment.fragments.allStudents.adapter.allStudentsAdapter;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.GridStudentEvidence;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.models.GetStudentData;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.models.GetStudentsModel;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AssessmentRecordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AssessmentRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssessmentRecordFragment extends Fragment implements assessmentRecordAdapter.ContactsAdapterListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noStudentsFound)
    TextView noStudentsFound;
    private ArrayList<GetStudentData> studentList;
    private assessmentRecordAdapter adapter;
    View mView;
    SearchView searchView;
    private OnFragmentInteractionListener mListener;

    public AssessmentRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssessmentRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssessmentRecordFragment newInstance(String param1, String param2) {
        AssessmentRecordFragment fragment = new AssessmentRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_assessment_record, container, false);
        ButterKnife.bind(this,mView);
        initilize(mView);
        return mView;  
    
    }
    private void initilize(View v) {
        studentList = new ArrayList<>();
        adapter = new assessmentRecordAdapter(mView.getContext(), studentList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mView.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!getTeacherEmail().equals("")){
            progressBar.setVisibility(View.VISIBLE);
            prepareStudentList();

        }else {
            Intent i = new Intent(mView.getContext(), LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            Toasty.warning(mView.getContext(), "Please login first", Toast.LENGTH_SHORT, true).show();

        }

        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mView.getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                GetStudentData students = studentList.get(position);
                if (students.getSTUDENTACTIVATION().equals("0")) {
                    Intent i = new Intent(mView.getContext(), studentProfile.class);
                    i.putExtra("studentId", students.getSTUDENTID());
                    i.putExtra("studentName", students.getSTUDENTNAME());
                    i.putExtra("studentImage", students.getSTUDENTIMAGE());
                    i.putExtra("studentGender", students.getSTUDENTGENDER());
                    i.putExtra("studentDOB", students.getSTUDENTDOB());
                    i.putExtra("studentClass", students.getSTUDENTCLASS());
                    i.putExtra("studentFName", students.getSTUDENTFATHERNAME());
                    i.putExtra("studentMName", students.getSTUDENTMOTHERNAME());
                    i.putExtra("studentContactPrimary", students.getSTUDENTCONTACTPRIMARY());
                    i.putExtra("studentEmail", students.getSTUDENTCONTACTEMAIL());
                    i.putExtra("studentAddress", students.getSTUDENTADDRESSPERMANENT());
                    i.putExtra("classSession", students.getSTUDENTSESSION());
                    startActivity(i);
                }else {
                    Toasty.warning(mView.getContext(), "Student is not activate now", Toast.LENGTH_LONG, true).show();

                }
            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));*/
    }
    @Override
    public void onContactSelected(GetStudentData students) {
        if (students.getSTUDENTACTIVATION().equals("0")) {
            Intent i = new Intent(mView.getContext(), GridStudentEvidence.class);
            i.putExtra("studentId", students.getSTUDENTID());
            i.putExtra("studentName", students.getSTUDENTNAME());
            i.putExtra("activityType","AssessmentRecord");

            startActivity(i);
            // for closing search action if action is open
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!searchView.isIconified()) {
                        searchView.onActionViewCollapsed();

                    }
                }
            }, 140);
        }else {
            Toasty.warning(mView.getContext(), "Student is not activate now", Toast.LENGTH_LONG, true).show();

        }
    }




    private void prepareStudentList() {
        noStudentsFound.setVisibility(View.GONE);
        if (!checkInternetState.getInstance(mView.getContext()).isOnline()) {
            progressBar.setVisibility(View.GONE);
            noStudentsFound.setText("No Internet Connection.");
            noStudentsFound.setVisibility(View.VISIBLE);
        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetStudentsModel> userCall = service.getAllStudents(Integer.parseInt(sharedId()));
            userCall.enqueue(new Callback<GetStudentsModel>() {
                @Override
                public void onResponse(Call<GetStudentsModel> call, Response<GetStudentsModel> response) {
                    progressBar.setVisibility(View.GONE);

                    Log.d("students"," "+response.body().getStatus());
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
                                students.setSTUDENTCONTACTSECONDARY(response.body().getData().get(i).getSTUDENTCONTACTSECONDARY());
                                students.setSTUDENTDATETIME(response.body().getData().get(i).getSTUDENTDATETIME());
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
                            noStudentsFound.setText("No students found");
                            noStudentsFound.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(mView.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.all_students, menu);
        MenuItem item = menu.findItem(R.id.searchStudents);

        searchView = (SearchView)item.getActionView();
        searchView.setQueryHint("Search Student...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        //super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.searchStudents) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private String sharedId() {
        return new PreferenceManager(mView.getContext()).getUserID();
    }
    private String getTeacherEmail() {
        return new PreferenceManager(mView.getContext()).getUserEmail();
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
