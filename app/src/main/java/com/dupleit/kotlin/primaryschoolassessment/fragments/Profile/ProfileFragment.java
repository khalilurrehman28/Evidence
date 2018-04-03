package com.dupleit.kotlin.primaryschoolassessment.fragments.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.Profile.model.ImageData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.Profile.model.UpdateImageModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.GetFrameworksModel;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.ProgressRequestBody;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Utils;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements ProgressRequestBody.UploadCallbacks{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.UserProfileImage)
    CircleImageView teacherImage;

    @BindView(R.id.teacherEmail)
    TextView teacherEmail;

    @BindView(R.id.etTeacherName)
    EditText etName;

    @BindView(R.id.etContact)
    EditText etContact;
    @BindView(R.id.updateProfile)
    LinearLayout updateProfile;
    String mediaPath;
    View mView;
    Glide glide;
    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, mView);
        initilize(mView);
        return mView;
    }

    private void initilize(final View v) {
        mediaPath="";
        teacherEmail.setText((new PreferenceManager(v.getContext()).getUserEmail()));
        etName.setText((new PreferenceManager(v.getContext()).getUsername()));
        etContact.setText((new PreferenceManager(v.getContext()).getUserMobile()));
        Glide.with(this).load(Utils.webUrlHome+(new PreferenceManager(v.getContext()).getUserImage())).into(teacherImage);
        teacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(getContext(),ProfileFragment.this);
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    updateprofile(etName.getText().toString(),etContact.getText().toString());
                }
            }
        });
    }

    private void updateprofile(final String etName, final String etContact) {
        final ProgressDialog pd = new ProgressDialog(mView.getContext());
        pd.setTitle("Update profile");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        if (!checkInternetState.getInstance(mView.getContext()).isOnline()) {
            Toasty.warning(mView.getContext(), "Please check your internet connection.", Toast.LENGTH_LONG, true).show();
        }else {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<GetFrameworksModel> userCall = service.updateProfile_request(Integer.parseInt(sharedId()),etName,etContact);
            userCall.enqueue(new Callback<GetFrameworksModel>() {
                @Override
                public void onResponse(Call<GetFrameworksModel> call, Response<GetFrameworksModel> response) {
                    pd.hide();
                    if (response.body().getStatus()){
                        new PreferenceManager(mView.getContext()).saveUserName(etName);
                        new PreferenceManager(mView.getContext()).saveUserMobile(etContact);
                        Toasty.success(mView.getContext(), "Profile updated", Toast.LENGTH_LONG, true).show();

                    }

                }
                @Override
                public void onFailure(Call<GetFrameworksModel> call, Throwable t) {
                    Log.d("onFailure", t.toString());
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = "";
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri selectedImageUri = result.getUri();
                mediaPath = getRealPathFromURI(selectedImageUri);
                Log.e("mediaPath ",""+mediaPath +"  selectedImageUri  "+selectedImageUri);
                //Toast.makeText(profile.this, "media path  "+mediaPath, Toast.LENGTH_SHORT).show();
                if (!mediaPath.equals("")){
                    glide.with(this).load(mediaPath).into(teacherImage);
                    uploadImage(mediaPath);
                }else{
                    Toast.makeText(mView.getContext(), "do not get path", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    private void uploadImage(String mediaPath) {
        final ProgressDialog pd = new ProgressDialog(mView.getContext());
        pd.setTitle("Uploading Image");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        File file = new File(mediaPath);
        ProgressRequestBody fileBody = new ProgressRequestBody(file,this);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("TEACHER_IMAGE", file.getName(), fileBody);
        String extension = mediaPath.substring(mediaPath.lastIndexOf(".") + 1);
        //Toast.makeText(mContext, ""+extension, Toast.LENGTH_SHORT).show();
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<UpdateImageModel> call = service.updateuserimage_request(Integer.parseInt(sharedId()),fileToUpload);
        call.enqueue(new Callback<UpdateImageModel>() {
            @Override
            public void onResponse(Call<UpdateImageModel> call, Response<UpdateImageModel> response) {
                pd.hide();
                if (response.body().getStatus()) {


                    new PreferenceManager(mView.getContext()).saveUserImage(response.body().getData().getTEACHERIMAGE());
                    Log.e("Message true",response.body().getData().getTEACHERIMAGE());
                   Toasty.success(mView.getContext(), "Image updated", Toast.LENGTH_LONG, true).show();

                } else {
                    Toasty.info(mView.getContext(), "Image not updated", Toast.LENGTH_LONG, true).show();

                    Log.e("Message",response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<UpdateImageModel> call, Throwable t) {
                Log.e("response",""+t);

                pd.hide();

            }
        });
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = mView.getContext().getContentResolver().query(contentURI, null, null, null, null);
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

    private Boolean validate() {
        if (etName.getText().toString().equals("")){
            etName.setError("Please fill name");
            return false;
        }else{
            etName.setError(null);
        }
        if (etContact.getText().toString().equals("")){
            etContact.setError("Please fill contact");
            return false;
        }else{
            etContact.setError(null);
        }
        return true;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private String sharedId() {
        return new PreferenceManager(mView.getContext()).getUserID();
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

    @Override
    public void onProgressUpdate(int percentage) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

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
