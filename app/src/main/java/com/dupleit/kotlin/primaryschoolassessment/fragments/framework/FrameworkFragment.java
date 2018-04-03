package com.dupleit.kotlin.primaryschoolassessment.fragments.framework;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.activities.Login.UI.LoginActivity;
import com.dupleit.kotlin.primaryschoolassessment.activities.studentProfile.studentProfile;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.adapter.getFrameworksTitlesAdapter;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.GetFrameworksModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.subTitleframework.gettingFrameworkSubtitles;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.UI.getClassStudents;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.RecyclerTouchListener;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrameworkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrameworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrameworkFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.noFrameworksFound) TextView noFrameworksFound;
    /*@BindView(R.id.fab) FloatingActionButton fab;*/

    private List<FrameworkData> frameworksList;
    getFrameworksTitlesAdapter adapter;
    View mView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FrameworkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrameworkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FrameworkFragment newInstance(String param1, String param2) {
        FrameworkFragment fragment = new FrameworkFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_frameworks, container, false);
        ButterKnife.bind(this,mView);
        initilize(mView);
        return mView;
    }

    private void initilize(final View mView) {
        frameworksList = new ArrayList<>();
        adapter = new getFrameworksTitlesAdapter(mView.getContext(), frameworksList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mView.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!getTeacherEmail().equals("")){
            progressBar.setVisibility(View.VISIBLE);
            prepareFrameWorkTitles();

        }else {
            Intent i = new Intent(mView.getContext(), LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toasty.warning(mView.getContext(), "Please login first", Toast.LENGTH_SHORT, true).show();

        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mView.getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final FrameworkData frames = frameworksList.get(position);

                Intent i = new Intent(mView.getContext(), gettingFrameworkSubtitles.class);
                i.putExtra("frameworkId", frames.getFRAMEWORKID());
                i.putExtra("frameworkTitle", frames.getFRAMEWORKTITLE());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                *//*Intent intent = new Intent(getActivity(), selectClassSpinner.class);
                intent.putExtra("activityType","noticeBoard");

                startActivity(intent);*//*
            }
        });
        //to show or hide fab on scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 ||dy<0 && fab.isShown())
                {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    fab.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/
    }
    private void prepareFrameWorkTitles() {
        noFrameworksFound.setVisibility(View.GONE);
        if (!checkInternetState.getInstance(mView.getContext()).isOnline()) {
            Toasty.warning(mView.getContext(), "No Internet connection.", Toast.LENGTH_LONG, true).show();
            noFrameworksFound.setVisibility(View.VISIBLE);
            noFrameworksFound.setText("No Internet connection.");

            progressBar.setVisibility(View.GONE);
        }else {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworksModel> userCall = service.getFrameworkTitles();
            userCall.enqueue(new Callback<GetFrameworksModel>() {
                @Override
                public void onResponse(Call<GetFrameworksModel> call, Response<GetFrameworksModel> response) {
                    progressBar.setVisibility(View.GONE);

                    Log.d("students"," "+response.body().getStatus());
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {
                            noFrameworksFound.setVisibility(View.GONE);

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                FrameworkData frameworkTitles= new FrameworkData();
                                frameworkTitles.setFRAMEWORKID(response.body().getData().get(i).getFRAMEWORKID());
                                frameworkTitles.setFRAMEWORKDATETIME(response.body().getData().get(i).getFRAMEWORKDATETIME());
                                frameworkTitles.setFRAMEWORKTITLE(response.body().getData().get(i).getFRAMEWORKTITLE());
                                frameworksList.add(frameworkTitles);
                                //adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                            }

                        }else{
                            noFrameworksFound.setVisibility(View.VISIBLE);
                            noFrameworksFound.setText("No Frameworks found.");
                        }
                    }else{
                        Toasty.error(mView.getContext(), "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }
                }

                @Override
                public void onFailure(Call<GetFrameworksModel> call, Throwable t) {
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
