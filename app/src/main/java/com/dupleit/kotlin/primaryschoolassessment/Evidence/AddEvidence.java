package com.dupleit.kotlin.primaryschoolassessment.Evidence;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter.CustomParentFrameSpinnerAdapter;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.AddEvidenceModel;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter.CustomSpinnerAdapter;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter.getFrameworksubTitlesAdapter;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.GetFrameworksubtitleModel;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.SubTitleData;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.activities.MainActivity;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.GetFrameworksModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.GetparentFrameworkResponse;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.parentFrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.UI.getClassStudents;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Config;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.GridSpacingItemDecoration;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.ProgressRequestBody;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

/**
 * Created by mandeep on 2/12/18.
 */

public class AddEvidence extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks{
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 101;
    private static final int CHOOSE_CAMERA_IMAGE_1 = 102;
    private static final int CHOOSE_GALLERY_IMAGE_1 = 103;
    private static final int CHOOSE_CAMERA_VIDEO_1 = 104;
    private static final int CHOOSE_GALLERY_VIDEO_1 = 105;
    private static final int CHOOSE_CAMERA_IMAGE_2 = 106;
    private static final int CHOOSE_GALLERY_IMAGE_2 = 107;
    private static final int CHOOSE_CAMERA_VIDEO_2 = 108;
    private static final int CHOOSE_GALLERY_VIDEO_2 = 109;
    private static final int CHOOSE_CAMERA_IMAGE_3 = 110;
    private static final int CHOOSE_GALLERY_IMAGE_3 = 111;
    private static final int CHOOSE_CAMERA_VIDEO_3 = 112;
    private static final int CHOOSE_GALLERY_VIDEO_3 = 113;

    @BindView(R.id.studentName) TextView studentName;
    @BindView(R.id.etSelectedDate) EditText etSelectedDate;
    @BindView(R.id.imageView1) ImageView imageView1;
    @BindView(R.id.imageView2) ImageView imageView2;
    @BindView(R.id.imageView3) ImageView imageView3;
    @BindView(R.id.videoCamera1) ImageView videoCamera1;
    @BindView(R.id.videoCamera2) ImageView videoCamera2;
    @BindView(R.id.videoCamera3) ImageView videoCamera3;
    @BindView(R.id.etComment) EditText etCommentBox;
    @BindView(R.id.frameworkTitle) TextView frameworkTitle;
    @BindView(R.id.btnCreate) Button btnCreate;
    @BindView(R.id.linearSubTitle) LinearLayout linearSubTitle;
    ProgressDialog pDialog;
    Glide glide;
    String getStudentName, getStudentId;
    ArrayList<String> fileUris = new ArrayList<String>();
    Map<String,Map<String,String>> hash;
    Uri picUri1,picUri2,picUri3;
    Uri videoUri1,videoUri2,videoUri3;
    CustomSpinnerAdapter customSpinnerAdapter;
    ArrayList<FrameworkData> FrameworksList;
    @BindView(R.id.spinnerFramework) Spinner spinnerFramework;

    CustomParentFrameSpinnerAdapter customParentFrameSpinnerAdapter;
    ArrayList<parentFrameworkData> ParentFrameworksList;
    @BindView(R.id.spinnerParentFrameworkName) Spinner spinnerParentFramework;
    @BindView(R.id.layoutframeworkSpinner)LinearLayout layoutframeworkSpinner;

    String parentFrameworkId, parentFrameWorkTitle,FrameworkId, FrameWorkTitle;
    @BindView(R.id.recyclerSubTitle) RecyclerView recyclerSubTitle;
    private List<SubTitleData> frameworksubTList;
    getFrameworksubTitlesAdapter subTitleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evidence);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("New Evidence");
        hash = new HashMap<>();
        studentName.setText("Select student");
        fileUris.add(0, "");
        fileUris.add(1, "");
        fileUris.add(2, "");
        linearSubTitle.setVisibility(View.GONE);
        layoutframeworkSpinner.setVisibility(View.GONE);

        etSelectedDate.setInputType(InputType.TYPE_NULL);
        // etSelectFramesDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.calendar,0,0, 0);
        //etSelectFramesDate.setCompoundDrawablePadding(15);
        etSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        monthOfYear = monthOfYear + 1;
                        String mon = (monthOfYear < 10) ? "0" + monthOfYear : "" + monthOfYear;
                        String day = (dayOfMonth < 10) ? "0" + dayOfMonth : "" + dayOfMonth;
                        //String getDate = year+"/"+mon+"/"+day;
                        String getDate = day + "/" + mon + "/" + year;

                        etSelectedDate.setText(getDate);
                    }
                };
                Calendar calendar = Calendar.getInstance();
                int Date = calendar.get(Calendar.DAY_OF_MONTH);
                int Month = calendar.get(Calendar.MONTH);
                int Year = calendar.get(Calendar.YEAR);
                //Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(AddEvidence.this, dpd, Year,Month,Date);
                d.getDatePicker().setMaxDate(System.currentTimeMillis());
                d.show();


            }
        });

        studentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEvidence.this, getClassStudents.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecttype(1);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecttype(2);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecttype(3);
            }
        });
        getDropDownOfParentFrameWork();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    uploadImages(fileUris,etSelectedDate.getText().toString(),etCommentBox.getText().toString());
                }
            }
        });

    }

    private void getDropDownOfParentFrameWork() {
        ParentFrameworksList = new ArrayList<>();
        ParentFrameworksList.clear();

        customParentFrameSpinnerAdapter = new CustomParentFrameSpinnerAdapter(this, ParentFrameworksList);
        spinnerParentFramework.setAdapter(customParentFrameSpinnerAdapter);
        createParentFrameWorkList();
        spinnerParentFramework.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final parentFrameworkData ParentframeData = ParentFrameworksList.get(position);

                parentFrameworkId = ParentframeData.getCATEGORYID();
                parentFrameWorkTitle = ParentframeData.getCATEGORYNAME();

                getDropDownOfFrameWork(parentFrameworkId);

                // Toast.makeText(parent.getContext(), "class id" + classId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createParentFrameWorkList() {
        pDialog = new ProgressDialog(this);
        //pDialog.setIndeterminate(true);
        pDialog.setMessage("Please wait getting data...");
        pDialog.setCancelable(false);

        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();
            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        } else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetparentFrameworkResponse> userCall = service.getParentFrameworkTitles();
            userCall.enqueue(new Callback<GetparentFrameworkResponse>() {
                @Override
                public void onResponse(Call<GetparentFrameworkResponse> call, Response<GetparentFrameworkResponse> response) {
                    hidepDialog();
                    Log.d("getListStatus", " " + response.body().getStatus());
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            layoutframeworkSpinner.setVisibility(View.VISIBLE);
                            /*to get notice of principal to all*/
                            Log.d("getMessage", "" + response.body().getMsg());
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                parentFrameworkData parentFrames = new parentFrameworkData();

                                Log.d("data", "" + response.body().getData().get(i).getCATEGORYID());
                                parentFrames.setCATEGORYID(response.body().getData().get(i).getCATEGORYID());
                                parentFrames.setCATEGORYNAME(response.body().getData().get(i).getCATEGORYNAME());
                                parentFrames.setDATETIME(response.body().getData().get(i).getDATETIME());
                                parentFrames.setSTATUS(response.body().getData().get(i).getSTATUS());
                                ParentFrameworksList.add(parentFrames);
                                //adapter.notifyDataSetChanged();

                                customParentFrameSpinnerAdapter.notifyDataSetChanged();
                            }

                        } else {
                            layoutframeworkSpinner.setVisibility(View.GONE);

                            Toast.makeText(AddEvidence.this, "there have no data ", Toast.LENGTH_SHORT).show();
                            //noFramesFound.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetparentFrameworkResponse> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }


        customParentFrameSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
        super.onBackPressed();

    }
    private void getDropDownOfFrameWork(String parentFrameworkId) {
        FrameworksList = new ArrayList<>();
        FrameworksList.clear();

        customSpinnerAdapter = new CustomSpinnerAdapter(this, FrameworksList);
        spinnerFramework.setAdapter(customSpinnerAdapter);
        createFrameWorkList(parentFrameworkId);
        spinnerFramework.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final FrameworkData frameData = FrameworksList.get(position);

                FrameworkId = frameData.getFRAMEWORKID();
                FrameWorkTitle = frameData.getFRAMEWORKTITLE();
                getSubTitles();

                // Toast.makeText(parent.getContext(), "class id" + classId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSubTitles() {

        frameworksubTList = new ArrayList<>();
        subTitleAdapter = new getFrameworksubTitlesAdapter(this, frameworksubTList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerSubTitle.setLayoutManager(mLayoutManager);
        recyclerSubTitle.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerSubTitle.setItemAnimator(new DefaultItemAnimator());
        recyclerSubTitle.setAdapter(subTitleAdapter);

        prepareSubtitles();

    }

    private void prepareSubtitles() {

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetFrameworksubtitleModel> userCall = service.get_framework_subtitle(Integer.parseInt(FrameworkId));
        userCall.enqueue(new Callback<GetFrameworksubtitleModel>() {
            @Override
            public void onResponse(Call<GetFrameworksubtitleModel> call, Response<GetFrameworksubtitleModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        linearSubTitle.setVisibility(View.VISIBLE);

                        frameworkTitle.setText(FrameWorkTitle);
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            SubTitleData SubTitleData = new SubTitleData();
                            SubTitleData.setFRAMEWORKSTATUS(response.body().getData().get(i).getFRAMEWORKSTATUS());
                            SubTitleData.setFRAMEWORKSUB(response.body().getData().get(i).getFRAMEWORKSUB());
                            SubTitleData.setSCORE(response.body().getData().get(i).getSCORE());
                            SubTitleData.setSubId(response.body().getData().get(i).getSubId());
                            SubTitleData.setEtGetScore("0");

                            frameworksubTList.add(SubTitleData);
                            //adapter.notifyDataSetChanged();
                            subTitleAdapter.notifyDataSetChanged();
                        }

                    } else {
                        linearSubTitle.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(AddEvidence.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFrameworksubtitleModel> call, Throwable t) {
                //hidepDialog();
                Log.d("onFailure", t.toString());

            }
        });
    }

    private void createFrameWorkList(String GetFrameworksModel) {

        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {


            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        } else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworksModel> userCall = service.getFrameworkTitles(Integer.parseInt(GetFrameworksModel));
            userCall.enqueue(new Callback<GetFrameworksModel>() {
                @Override
                public void onResponse(Call<GetFrameworksModel> call, Response<GetFrameworksModel> response) {

                    Log.d("getListStatus", " " + response.body().getStatus());
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            /*to get notice of principal to all*/
                            Log.d("getMessage", "" + response.body().getMsg());
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                FrameworkData Frames = new FrameworkData();

                                Log.d("data", "" + response.body().getData().get(i).getFRAMEWORKDATETIME());
                                Frames.setFRAMEWORKTITLE(response.body().getData().get(i).getFRAMEWORKTITLE());
                                Frames.setFRAMEWORKID(response.body().getData().get(i).getFRAMEWORKID());
                                Frames.setFRAMEWORKDATETIME(response.body().getData().get(i).getFRAMEWORKDATETIME());
                                FrameworksList.add(Frames);
                                //adapter.notifyDataSetChanged();

                                customSpinnerAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(AddEvidence.this, "there have no data ", Toast.LENGTH_SHORT).show();
                            //noFramesFound.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetFrameworksModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }


        customSpinnerAdapter.notifyDataSetChanged();
    }

    private boolean validateData() {
        if (etSelectedDate.getText().toString().equals("")) {
            etSelectedDate.setError("Please select date");
            return false;
        }else {
            etSelectedDate.setError(null);

        }
        if (studentName.getText().toString().equals("Select student") || studentName.getText().toString().equals("")) {
            studentName.setError("Please select student");
            return false;
        }else {
            studentName.setError(null);

        }

        if (etCommentBox.getText().toString().equals("")) {
            etCommentBox.setError("Please type some comment");
            return false;
        }else {
            studentName.setError(null);
        }
        return true;
    }

    private void selecttype(final int type) {

        final CharSequence[] options = {"Image (Camera)","Image (Gallery)", "Video (Camera)","Video (Gallery)"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddEvidence.this);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Image (Camera)")) {
                    if (getApplicationContext().getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA)) {
                        if (type == 1) {

                            try {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                                File file = new File(Environment.getExternalStorageDirectory(),
                                        "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

                                picUri1 = Uri.fromFile(file);

                                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, picUri1);

                                cameraIntent.putExtra("return-data", true);
                                startActivityForResult(cameraIntent, CHOOSE_CAMERA_IMAGE_1);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }


                        } else if (type == 2) {

                            try {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                                File file = new File(Environment.getExternalStorageDirectory(),
                                        "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

                                picUri2 = Uri.fromFile(file);

                                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, picUri2);

                                cameraIntent.putExtra("return-data", true);
                                startActivityForResult(cameraIntent, CHOOSE_CAMERA_IMAGE_2);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }


                        } else if (type == 3) {


                            try {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                                File file = new File(Environment.getExternalStorageDirectory(),
                                        "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

                                picUri3 = Uri.fromFile(file);

                                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, picUri3);

                                cameraIntent.putExtra("return-data", true);
                                startActivityForResult(cameraIntent, CHOOSE_CAMERA_IMAGE_3);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }


                        }
                    } else {
                        Toasty.error(AddEvidence.this, "Camera not supported", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
                    }

                }else if (options[item].equals("Image (Gallery)")) {
                    if (type == 1) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, CHOOSE_GALLERY_IMAGE_1);
                        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/jpeg");
                        startActivityForResult(intent, CHOOSE_GALLERY_IMAGE_1);*/
                    } else if (type == 2) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, CHOOSE_GALLERY_IMAGE_2);
                    } else if (type == 3) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, CHOOSE_GALLERY_IMAGE_3);
                    }

                } else if (options[item].equals("Video (Camera)")) {
                    if (getApplicationContext().getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA)) {
                        if (type == 1) {
                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            videoUri1 = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                            // set video quality
                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri1); // set the image file
                            startActivityForResult(intent, CHOOSE_CAMERA_VIDEO_1);
                        } else if (type == 2) {
                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            videoUri2 = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                            // set video quality
                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri2); // set the image file
                            startActivityForResult(intent, CHOOSE_CAMERA_VIDEO_2);
                        } else if (type == 3) {
                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            videoUri3 = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                            // set video quality
                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri3); // set the image file
                            startActivityForResult(intent, CHOOSE_CAMERA_VIDEO_3);
                        }
                    } else {
                        Toasty.error(AddEvidence.this, "Camera not supported", Toast.LENGTH_LONG, true).show();

                    }

                }else if (options[item].equals("Video (Gallery)")) {
                    if (type == 1) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CHOOSE_GALLERY_VIDEO_1);
                    } else if (type == 2) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CHOOSE_GALLERY_VIDEO_2);
                    } else if (type == 3) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CHOOSE_GALLERY_VIDEO_3);
                    }

                }

            }
        });
        builder.show();
    }
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case SECOND_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    // get String data from Intent
                    getStudentName = data.getStringExtra("studentName");
                    getStudentId = data.getStringExtra("studentId");
                    studentName.setText(getStudentName);
                }
                break;
            case CHOOSE_CAMERA_IMAGE_1:
                if (resultCode == RESULT_OK && picUri1 != null) {

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(picUri1));
                    String mediaPath = String.valueOf(finalFile);
                    if (!mediaPath.equals("")) {

                        fileUris.set(0, mediaPath);
                        videoCamera1.setVisibility(View.GONE);
                        glide.with(this).load(mediaPath).into(imageView1);
                    }else {
                        Toasty.warning(AddEvidence.this, "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }

                }
                break;
            case CHOOSE_GALLERY_IMAGE_1:
                if (resultCode == RESULT_OK && data != null) {

                    Uri selectedImage = data.getData();
                    String mediaPath = getRealPathFromURI(selectedImage);
                    Log.e("imagePath", "" + mediaPath + "  imageUri " + selectedImage);

                    if (!mediaPath.equals("")) {
                        fileUris.set(0, mediaPath);
                        videoCamera1.setVisibility(View.GONE);
                        glide.with(this).load(mediaPath).into(imageView1);
                    }
                }
                break;
            case CHOOSE_CAMERA_VIDEO_1:
                if (resultCode == RESULT_OK ) {
                    // MEDIA GALLERY
                    String selectedVideoPath = videoUri1.getPath();
                    Log.e("VideoPath1", "" + selectedVideoPath );

                    if (!selectedVideoPath.equals("")) {
                        fileUris.set(0, selectedVideoPath);
                        videoCamera1.setVisibility(View.VISIBLE);
                        glide.with(this).load(selectedVideoPath).into(imageView1);
                    }
                }
                break;

            case CHOOSE_GALLERY_VIDEO_1:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedVideoUri = data.getData();

                    // MEDIA GALLERY
                    String selectedVideoPath = getPath(selectedVideoUri);
                    Log.e("VideoPath1", "" + selectedVideoPath + " uri " + selectedVideoUri);

                    if (!selectedVideoPath.equals("")) {
                        fileUris.set(0, selectedVideoPath);
                        videoCamera1.setVisibility(View.VISIBLE);
                        glide.with(this).load(selectedVideoPath).into(imageView1);
                    }
                }
                break;
            case CHOOSE_CAMERA_IMAGE_2:
                if (resultCode == RESULT_OK && picUri2 != null) {

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(picUri2));
                    String mediaPath = String.valueOf(finalFile);
                    if (!mediaPath.equals("")) {

                        fileUris.set(1, mediaPath);
                        videoCamera2.setVisibility(View.GONE);
                        glide.with(this).load(mediaPath).into(imageView2);
                    }else {
                        Toasty.warning(AddEvidence.this, "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }

                }
                break;
            case CHOOSE_GALLERY_IMAGE_2:
                if (resultCode == RESULT_OK && data != null) {

                    Uri selectedImage = data.getData();
                    String mediaPath = getRealPathFromURI(selectedImage);
                    Log.e("imagePath2", "" + mediaPath + "  imageUri " + selectedImage);

                    if (!mediaPath.equals("")) {
                        fileUris.set(1, mediaPath);
                        videoCamera2.setVisibility(View.GONE);
                        glide.with(this).load(mediaPath).into(imageView2);

                    }
                }
                break;
            case CHOOSE_CAMERA_VIDEO_2:
                if (resultCode == RESULT_OK ) {
                    // MEDIA GALLERY
                    String selectedVideoPath = videoUri2.getPath();
                    Log.e("VideoPath2", "" + selectedVideoPath );

                    if (!selectedVideoPath.equals("")) {
                        fileUris.set(1, selectedVideoPath);
                        videoCamera2.setVisibility(View.VISIBLE);
                        glide.with(this).load(selectedVideoPath).into(imageView2);
                    }
                }
                break;
            case CHOOSE_GALLERY_VIDEO_2:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedVideoUri = data.getData();

                    // MEDIA GALLERY
                    String selectedVideoPath = getPath(selectedVideoUri);
                    Log.e("VideoPath2", "" + selectedVideoPath + " uri " + selectedVideoUri);

                    if (!selectedVideoPath.equals("")) {
                        fileUris.set(1, selectedVideoPath);
                        videoCamera2.setVisibility(View.VISIBLE);
                        glide.with(this).load(selectedVideoPath).into(imageView2);
                    }
                }
                break;
            case CHOOSE_CAMERA_IMAGE_3:
                if (resultCode == RESULT_OK && picUri3 != null) {

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(picUri3));
                    String mediaPath = String.valueOf(finalFile);
                    if (!mediaPath.equals("")) {

                        fileUris.set(2, mediaPath);
                        videoCamera3.setVisibility(View.GONE);
                        glide.with(this).load(mediaPath).into(imageView3);
                    }else {
                        Toasty.warning(AddEvidence.this, "Something went wrong", Toast.LENGTH_LONG, true).show();

                    }

                }
                break;
            case CHOOSE_GALLERY_IMAGE_3:
                if (resultCode == RESULT_OK && data != null) {

                    Uri selectedImage = data.getData();
                    String mediaPath = getRealPathFromURI(selectedImage);
                    Log.e("imagePath3", "" + mediaPath + "  imageUri " + selectedImage);

                    if (!mediaPath.equals("")) {
                        fileUris.set(2, mediaPath);
                        videoCamera3.setVisibility(View.GONE);
                        glide.with(this).load(mediaPath).into(imageView3);
                    }
                }
                break;
            case CHOOSE_CAMERA_VIDEO_3:
                if (resultCode == RESULT_OK ) {
                    // MEDIA GALLERY
                    String selectedVideoPath = videoUri3.getPath();
                    Log.e("VideoPath2", "" + selectedVideoPath );

                    if (!selectedVideoPath.equals("")) {
                        fileUris.set(2, selectedVideoPath);
                        videoCamera3.setVisibility(View.VISIBLE);
                        glide.with(this).load(selectedVideoPath).into(imageView3);
                    }
                }
                break;
            case CHOOSE_GALLERY_VIDEO_3:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedVideoUri = data.getData();

                    // MEDIA GALLERY
                    String selectedVideoPath = getPath(selectedVideoUri);
                    Log.e("VideoPath3", "" + selectedVideoPath + " uri " + selectedVideoUri);

                    if (!selectedVideoPath.equals("")) {
                        fileUris.set(2, selectedVideoPath);
                        videoCamera3.setVisibility(View.VISIBLE);
                        glide.with(this).load(selectedVideoPath).into(imageView3);
                    }
                }
                break;

            default:

                break;
        }


    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("", "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void uploadImages(ArrayList<String> fileUris, final String evidenceDate,final String commentText) {
        hash.clear();
        fileUris.removeAll(Collections.singleton(""));//for remove all null("") values from arraylist
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Uploading Evidence");
        //pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setProgress(0);
        pDialog.incrementProgressBy(2);
        pDialog.setMax(100);
        pDialog.setCancelable(false);
        showpDialog();
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < fileUris.size(); i++) {
            parts.add(prepareFilePart("EVIDENCE_IMAGE_VIDEO[]", fileUris.get(i)));
            Log.e("getUri", "" + fileUris.get(i));
            Log.e("getUr",""+parts);

        }
        //ArrayList<String> data1 = new ArrayList<>();
        Map<String,String> userMap = new HashMap<>();
        for (int i = 0; i <frameworksubTList.size() ; i++) {
            Log.d("recyclerView",""+frameworksubTList.get(i).getFRAMEWORKSUB()+" "+frameworksubTList.get(i).getEtGetScore()+" "+frameworksubTList.get(i).getSubId());
            userMap.put(frameworksubTList.get(i).getSubId(),frameworksubTList.get(i).getEtGetScore());

            // hash.put(frameworksubTList.get(i).getSubId(),frameworksubTList.get(i).getEtGetScore());

            /*subTitleIDs.add(frameworksubTList.get(i).getSubId());
            frameworkScores.add(frameworksubTList.get(i).getEtGetScore());*/
        }

        hash.put("userData",userMap);
        Log.d("score",""+hash);

        Log.e("AllData "," studentID "+Integer.parseInt(getStudentId)+" frameID "+ Integer.parseInt(FrameworkId)+" evidenceID "+ evidenceDate+" comment "+ commentText+" sharedId "+ Integer.parseInt(sharedId())+ " media "+ parts+" frameData "+ hash);

        if (!checkInternetState.getInstance(AddEvidence.this).isOnline()) {
            hidepDialog();
            Toasty.warning(AddEvidence.this, "Please check your internet connection.", Toast.LENGTH_LONG, true).show();
        } else {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<AddEvidenceModel> call = service.addEvidenceRequest(Integer.parseInt(getStudentId), Integer.parseInt(FrameworkId), evidenceDate, commentText, Integer.parseInt(sharedId()), parts, hash/*,subTitleIDs,frameworkScores*/);
            call.enqueue(new Callback<AddEvidenceModel>() {
                @Override
                public void onResponse(Call<AddEvidenceModel> call, Response<AddEvidenceModel> response) {
                    hidepDialog();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            //new PrefManager(propertyImageUpload.this).saveUserImage(response.body().getMessage());
                            Log.e("", response.body().getMsg());
                            Toasty.success(AddEvidence.this, "Evidence Created", Toast.LENGTH_LONG, true).show();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(AddEvidence.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            }, 500);

                        } else {
                            Toasty.info(AddEvidence.this, "Evidence not created", Toast.LENGTH_LONG, true).show();

                            Log.e("Message", response.body().getMsg());
                        }
                    }else {
                        Toasty.warning(AddEvidence.this, "Please try after some time", Toast.LENGTH_LONG, true).show();

                    }
                }

                @Override
                public void onFailure(Call<AddEvidenceModel> call, Throwable t) {
                    hidepDialog();
                    Toasty.warning(AddEvidence.this, "Something went wrong", Toast.LENGTH_LONG, true).show();
                    Log.e("onFail", "" + t.getMessage());

                }
            });
        }
    }
        /*for (int i = 0; i < fileUris.size(); i++) {
            Log.e("getUri1", "" + fileUris.get(i));

        }*/

    // }

    // for getting image path
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = AddEvidence.this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    // for getting video path
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private String sharedId() {
        return new PreferenceManager(this).getUserID();
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

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri);

        // create RequestBody instance from file
        ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
        //MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("propertyImage", file.getName(), fileBody);
        /*RequestBody requestFile =
            RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    file
            );
*/
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), fileBody);
    }

    @Override
    public void onProgressUpdate(int percentage) {
        // pDialog.setMessage(String.valueOf(percentage) + "%");
        pDialog.setMessage("Uploading "+percentage+"% data");
        Log.e("percentage",""+percentage);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Uploading Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish() {
        pDialog.setMessage("Uploading Complete");

        //pDialog.setProgress(100);
    }
}