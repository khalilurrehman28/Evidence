
package com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getClassesData {

    @SerializedName("CLASS_ID")
    @Expose
    private String cLASSID;
    @SerializedName("CLASS_NAME")
    @Expose
    private String cLASSNAME;
    @SerializedName("CLASS_DATETIME")
    @Expose
    private String cLASSDATETIME;
    @SerializedName("CLASS_MODIFY_DATETIME")
    @Expose
    private String cLASSMODIFYDATETIME;
    @SerializedName("CLASS_STATUS")
    @Expose
    private String cLASSSTATUS;

    public String getCLASSID() {
        return cLASSID;
    }

    public void setCLASSID(String cLASSID) {
        this.cLASSID = cLASSID;
    }

    public String getCLASSNAME() {
        return cLASSNAME;
    }

    public void setCLASSNAME(String cLASSNAME) {
        this.cLASSNAME = cLASSNAME;
    }

    public String getCLASSDATETIME() {
        return cLASSDATETIME;
    }

    public void setCLASSDATETIME(String cLASSDATETIME) {
        this.cLASSDATETIME = cLASSDATETIME;
    }

    public String getCLASSMODIFYDATETIME() {
        return cLASSMODIFYDATETIME;
    }

    public void setCLASSMODIFYDATETIME(String cLASSMODIFYDATETIME) {
        this.cLASSMODIFYDATETIME = cLASSMODIFYDATETIME;
    }

    public String getCLASSSTATUS() {
        return cLASSSTATUS;
    }

    public void setCLASSSTATUS(String cLASSSTATUS) {
        this.cLASSSTATUS = cLASSSTATUS;
    }

}
