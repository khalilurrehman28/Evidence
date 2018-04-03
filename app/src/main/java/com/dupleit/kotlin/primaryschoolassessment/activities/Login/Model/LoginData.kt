package com.dupleit.kotlin.primaryschoolassessment.activities.Login.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginData {

    @SerializedName("TEACHER_ID")
    @Expose
    var teacherid: String? = null
    @SerializedName("TEACHER_EMAIL")
    @Expose
    var teacheremail: String? = null
    @SerializedName("TEACHER_PASSWORD")
    @Expose
    var teacherpassword: String? = null
    @SerializedName("TEACHER_NAME")
    @Expose
    var teachername: String? = null
    @SerializedName("TEACHER_PHONE")
    @Expose
    var teacherphone: String? = null
    @SerializedName("TEACHER_ADDRESS")
    @Expose
    var teacheraddress: Any? = null
    @SerializedName("TEACHER_IMAGE")
    @Expose
    var teacherimage: String? = null
    @SerializedName("TEACHER_CLASS_ASSIEND")
    @Expose
    var teacherclassassiend: String? = null
    @SerializedName("TEACHER_SESSION_ASSIEND")
    @Expose
    var teachersessionassiend: String? = null
    @SerializedName("TEACHER_ACTIVATION")
    @Expose
    var teacheractivation: String? = null
    @SerializedName("TEACHER_DATETIME")
    @Expose
    var teacherdatetime: String? = null
    @SerializedName("TEACHER_MODIFY_DATETIME")
    @Expose
    var teachermodifydatetime: String? = null
    @SerializedName("STATUS")
    @Expose
    var status: String? = null

}
