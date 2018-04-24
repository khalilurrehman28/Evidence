package com.dupleit.kotlin.primaryschoolassessment.activities.Login.UI

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dupleit.kotlin.primaryschoolassessment.Network.APIService
import com.dupleit.kotlin.primaryschoolassessment.Network.ApiClient

import com.dupleit.kotlin.primaryschoolassessment.R
import com.dupleit.kotlin.primaryschoolassessment.activities.Login.Model.LoginModel
import com.dupleit.kotlin.primaryschoolassessment.activities.MainActivity
import com.dupleit.kotlin.primaryschoolassessment.forgotPassword.fogotPassswordActivity
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.checkInternetState
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.model.GetTeacherClassesResponse
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.model.getClassesData
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.selectStudentClass
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private var togglepassword: Boolean?= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressbar.visibility = View.INVISIBLE
        val customFont = Typeface.createFromAsset(assets, "fonts/LatoLight.ttf")
        val customFont1 = Typeface.createFromAsset(assets, "fonts/LatoRegular.ttf")
        userEmail.typeface = customFont
        userPassword.typeface = customFont
        noAccount.typeface = customFont
        show_hide_password.typeface = customFont
        forgot_password.typeface = customFont
        loginBtn.typeface =customFont1

        loginBtn.setOnClickListener{
            if (validateData()){
                hitApi(userEmail?.text.toString(),userPassword?.text.toString())

            }
        }
        show_hide_password.setOnClickListener{
            if (togglepassword==false){
                show_hide_password.text = "Hide Password"
                userPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglepassword = true
            }else{
                show_hide_password.text = "Show Password"
                userPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglepassword =false
            }

        }
        forgot_password.setOnClickListener{
            val intent = Intent(this@LoginActivity, fogotPassswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hitApi(userEmail: String, userPassword: String) {
        progressbar.visibility = View.VISIBLE

        if (!checkInternetState.getInstance(applicationContext).isOnline) {
            Toasty.warning(applicationContext, "No Internet connection.", Toast.LENGTH_LONG, true).show();
            progressbar.visibility = View.INVISIBLE

            //new CustomToast().Show_Toast(ctx, view,"Please Check Your Internet Connection." );
        } else {
            val service = ApiClient.getClient().create(APIService::class.java)
            val userCall = service.login_request(userEmail,userPassword)
            userCall.enqueue(object : Callback<LoginModel> {
                override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                    progressbar.visibility = View.INVISIBLE
                    if (response.body()?.status == true) {

                        for (i in 0 until response.body()?.data!!.size) {

                            if(response.body()?.data!!.get(i).teacheractivation=="1"){
                                Toasty.warning(applicationContext, "You are not active now. Please contact your school", Toast.LENGTH_LONG, true).show();

                            }else{
                                PreferenceManager(applicationContext).saveUserName(response.body()?.data!!.get(i).teachername)
                                PreferenceManager(applicationContext).saveUserEmail(response.body()?.data!!.get(i).teacheremail)
                                PreferenceManager(applicationContext).saveUserId(response.body()?.data!!.get(i).teacherid)
                                PreferenceManager(applicationContext).saveUserImage(response.body()?.data!!.get(i).teacherimage)
                                PreferenceManager(applicationContext).saveUserMobile(response.body()?.data!!.get(i).teacherphone)

                                Log.e("loginData","name "+response.body()?.data!!.get(i).teachername+" email "+response.body()?.data!!.get(i).teacheremail)
                                val progressRunnable = Runnable {
                                    prepareClassList(response.body()?.data!!.get(i).teacherid);
                                }

                                val pdCanceller = Handler()
                                pdCanceller.postDelayed(progressRunnable, 500)


                            }
                        }

                        Log.e("checkStatus",""+response.body()?.msg)
                    } else {
                        Log.e("checkStatus1",""+response.body()?.msg)

                        Toasty.error(applicationContext, ""+response.body()?.msg, Toast.LENGTH_SHORT, true).show();

                    }
                }

                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    Log.d("onFailure", t.toString())
                    progressbar.visibility = View.INVISIBLE

                    Toasty.error(applicationContext, "something went wrong", Toast.LENGTH_SHORT, true).show();
                }
            })
        }
    }

    private fun validateData(): Boolean {
        if (userEmail.text.toString().equals("")){
            userEmail.error = "Email is empty"
           // Toasty.error(this, "Email is empty", Toast.LENGTH_LONG, true).show()
            return false
        }else if(!isEmailValid(userEmail?.text.toString())){
            userEmail.error = "Please check your email format"
            //Toasty.error(this, "Please check your email format", Toast.LENGTH_LONG, true).show()
            return false
        }

        if (userPassword.text.toString().equals("")){
            userPassword.error ="Password is empty"
            return false
        }

        return true
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun prepareClassList(teacherid: String?) {
        val progress = ProgressDialog(this@LoginActivity)
        progress.setMessage("Please wait...")
        progress.setCancelable(false)
        progress.show()

        if (!checkInternetState.getInstance(applicationContext).isOnline) {
            Toasty.warning(applicationContext, "No Internet connection.", Toast.LENGTH_LONG, true).show();
            progress.cancel()

            //new CustomToast().Show_Toast(ctx, view,"Please Check Your Internet Connection." );
        }  else {

            val service = ApiClient.getClient().create(APIService::class.java)
            val userCall = service.get_teacher_class(Integer.parseInt(teacherid))
            userCall.enqueue(object : Callback<GetTeacherClassesResponse> {
                override fun onResponse(call: Call<GetTeacherClassesResponse>, response: Response<GetTeacherClassesResponse>) {
                   progress.cancel()
                    Log.d("classes", " " + response.body()!!.status!!)
                    if (response.isSuccessful) {
                        if (response.body()!!.status!!) {


                            for (i in 0 until response.body()!!.data.size) {

                                val classes = getClassesData()
                                classes.classid = response.body()!!.data[i].classid
                                classes.classdatetime = response.body()!!.data[i].classdatetime
                                classes.classname = response.body()!!.data[i].classname
                                classes.classmodifydatetime = response.body()!!.data[i].classmodifydatetime
                                classes.classstatus = response.body()!!.data[i].classstatus

                                if (response.body()!!.data.size==1){
                                    PreferenceManager(applicationContext).saveTeacherClassName(classes.classname)
                                    PreferenceManager(applicationContext).saveTeacherClassId(classes.classid)
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)

                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                }else if (response.body()!!.data.size>1){
                                    val intent = Intent(this@LoginActivity, selectStudentClass::class.java)
                                    intent.putExtra("activityType","login")
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                }
                            }

                        } else {
                            Toasty.error(applicationContext, "No class found", Toast.LENGTH_SHORT, true).show();


                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<GetTeacherClassesResponse>, t: Throwable) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString())
                   progress.cancel()

                }
            })
        }
    }

}
