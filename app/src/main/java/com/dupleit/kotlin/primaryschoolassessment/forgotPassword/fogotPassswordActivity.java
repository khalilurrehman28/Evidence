package com.dupleit.kotlin.primaryschoolassessment.forgotPassword;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.Graph.graphActivity;
import com.dupleit.kotlin.primaryschoolassessment.Graph.model.GetMonthsBarGraphModel;
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService;
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.forgotPassword.model.forgotPasswordResponse;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fogotPassswordActivity extends AppCompatActivity {
    EditText etEmail;
    TextView tvEmail;
    Button btnProceed;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            //inputgroupname.setText(s.toString());
            // textCount.setText(String.valueOf(s.length()));
        }

        public void afterTextChanged(Editable s) {

            if (isEmailValid(etEmail.getText().toString().trim())) {
                tvEmail.setVisibility(View.GONE);
                btnProceed.setVisibility(View.VISIBLE);
            }else {
                btnProceed.setVisibility(View.GONE);
                tvEmail.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_passsword);
        setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface customFont1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");

        etEmail= findViewById(R.id.etEmail);
        tvEmail= findViewById(R.id.tvEmail);
        btnProceed= findViewById(R.id.btnProceed);
        etEmail.addTextChangedListener(mTextEditorWatcher);
        btnProceed.setTypeface(customFont1);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){
                    hitApi();

                }
            }
        });

    }

    private void hitApi() {
        final String email = etEmail.getText().toString().trim();
        if (!checkInternetState.getInstance(fogotPassswordActivity.this).isOnline()) {
            showWarning("No internet connection.");

        }else {
            final ProgressDialog progressDialog = new ProgressDialog(fogotPassswordActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Sending code to your email...");
            progressDialog.show();
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<forgotPasswordResponse> userCall = service.forgetPassword(email);
            userCall.enqueue(new Callback<forgotPasswordResponse>() {
                @Override
                public void onResponse(Call<forgotPasswordResponse> call, Response<forgotPasswordResponse> response) {
                    progressDialog.hide();
                    if (response.isSuccessful()){
                        if (response.body().getStatus()) {

                            showSuccess("code sent to your email");
                            Intent intent =new Intent(fogotPassswordActivity.this,forgotPasswordValidate.class);
                            intent.putExtra("etEmail",etEmail.getText().toString().trim());
                            startActivity(intent);
                        }else{
                            showError("email not found");
                        }
                    }else{
                        showError("Something went wrong.");
                    }
                }

                @Override
                public void onFailure(Call<forgotPasswordResponse> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                    progressDialog.hide();

                    Toasty.error(fogotPassswordActivity.this, "Fail"+t.toString(), Toast.LENGTH_LONG, true).show();
                }
            });
        }


    }

    private boolean validateData() {
        if (etEmail.getText().toString().trim().equals("")){
            showWarning("Please enter Email Id");
            return false;
        }
        if (!isEmailValid(etEmail.getText().toString().trim())) {
            showError("Email type is not correct");
            return false;
        }

        return true;
    }

    private void showError(String s) {
        Toasty.error(getApplicationContext(), s, Toast.LENGTH_LONG, true).show();
    }

    private void showWarning(String s) {
        Toasty.warning(getApplicationContext(), s, Toast.LENGTH_LONG, true).show();
    }
    private void showSuccess(String s) {
        Toasty.success(getApplicationContext(), s, Toast.LENGTH_LONG, true).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
