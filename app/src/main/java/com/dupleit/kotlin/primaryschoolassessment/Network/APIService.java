package com.dupleit.kotlin.primaryschoolassessment.Network;


import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.AddEvidenceModel;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.GetFrameworksubtitleModel;
import com.dupleit.kotlin.primaryschoolassessment.Graph.model.GetMonthsBarGraphModel;
import com.dupleit.kotlin.primaryschoolassessment.PieChart.model.GetSessionPieRecordModel;
import com.dupleit.kotlin.primaryschoolassessment.activities.Login.Model.LoginModel;
import com.dupleit.kotlin.primaryschoolassessment.forgotPassword.model.forgotPasswordResponse;
import com.dupleit.kotlin.primaryschoolassessment.fragments.Profile.model.UpdateImageModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.model.GetFrameworkScoresModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.DownloadPdfModel.DownloadApi;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.models.GetEvidenceModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.GetFrameworksModel;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel.GetparentFrameworkResponse;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.models.GetStudentsModel;
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.model.GetTeacherClassesResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface APIService {
    //for login
    @FormUrlEncoded
    @POST("login_request")
    Call<LoginModel> login_request(@Field("useremail") String email, @Field("password") String password);

    //for getting all students of class
    @FormUrlEncoded
    @POST("getallstudent")
    Call<GetStudentsModel> getAllStudents(@Field("teacher_id") int teacher_id,@Field("class_id") int class_id);

    //for getting all Titles of framework
    @GET("get_parent_category")
    Call<GetparentFrameworkResponse> getParentFrameworkTitles();
    //for getting all Titles of framework
    /*@GET("get_framework")
    Call<GetFrameworksModel> getFrameworkTitles();*/

    @FormUrlEncoded
    @POST("get_framework")
    Call<GetFrameworksModel> getFrameworkTitles(@Field("CATEGORY_ID") int CATEGORY_ID);
    //for getting subtitle from framework like (Cricket is framework and bowling batting etc is subtiles of framework)
    @FormUrlEncoded
    @POST("get_framework_sub")
    Call<GetFrameworksubtitleModel> get_framework_subtitle(@Field("FRAMEWORK_ID") int FRAMEWORK_ID);

    //to update teacher profile
    @FormUrlEncoded
    @POST("edit_teacher_request")
    Call<GetFrameworksModel> updateProfile_request(@Field("TEACHER_ID") int TEACHER_ID, @Field("TEACHER_NAME") String TEACHER_NAME, @Field("TEACHER_PHONE") String TEACHER_PHONE);
    //to update teacher profile image
    @Multipart
    @POST("edit_teacher_image_request")
    Call<UpdateImageModel> updateuserimage_request(@Part("TEACHER_ID") int user_id,@Part MultipartBody.Part file);

    //to create evidence with image and videos
    @Multipart
    @POST("add_evidence_request")
    Call<AddEvidenceModel> addEvidenceRequest(@Part("EVIDENCE_STUDENT_ID") int EVIDENCE_STUDENT_ID, @Part("EVIDENCE_FRAMEWORK_ID") int EVIDENCE_FRAMEWORK_ID,
                                              @Part("EVIDENCE_DATE") String EVIDENCE_DATE, @Part("EVIDENCE_COMMENT") String EVIDENCE_COMMENT, @Part("EVIDENCE_TEACHER_ID") int EVIDENCE_TEACHER_ID,
                                              @Part List<MultipartBody.Part> files,@PartMap() Map<String,Map<String,String>> searchData);
    @FormUrlEncoded
    @POST("get_evidence_request")
    Call<GetEvidenceModel> get_evidence_request(@Field("EVIDENCE_STUDENT_ID") int EVIDENCE_STUDENT_ID);

    @FormUrlEncoded
    @POST("report_list_request")// to get evidence with filtering with date range (start Date or end date)
    Call<GetEvidenceModel> get_evidence_withdate_request(@Field("START_DATE") String START_DATE,@Field("END_DATE") String END_DATE,@Field("STUDENT_ID") int STUDENT_ID);


    @FormUrlEncoded
    @POST("get_evidence_student_request")
    Call<GetFrameworkScoresModel> get_evidence_media_request(@Field("EVIDENCE_ID") int EVIDENCE_ID);

    @FormUrlEncoded
    @POST("pie_chart_request")
    Call<GetSessionPieRecordModel> pie_chart_request(@Field("STUDENT_ID") int STUDENT_ID);

    @FormUrlEncoded
    @POST("graph_request")
    Call<GetMonthsBarGraphModel> bar_graph_request(@Field("STUDENT_ID") int STUDENT_ID);

    @FormUrlEncoded
    @POST("pdf_request")
    Call<DownloadApi> pdf_request(@Field("EVIDENCE_ID") String EVIDENCE_ID, @Field("STUDENT_ID") String STUDENT_ID);


    @FormUrlEncoded
    @POST("forgetPassword")
    Call<forgotPasswordResponse> forgetPassword(@Field("TEACHER_EMAIL") String TEACHER_EMAIL);

    @FormUrlEncoded
    @POST("updatePassword")
    Call<forgotPasswordResponse> updatePassword(@Field("KEY") int KEY,@Field("TEACHER_EMAIL") String TEACHER_EMAIL,@Field("PASSWORD") String PASSWORD);

    @FormUrlEncoded
    @POST("get_teacher_class")
    Call<GetTeacherClassesResponse> get_teacher_class(@Field("TEACHER_ID") int teacher_id);

}