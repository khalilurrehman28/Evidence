
package com.dupleit.kotlin.primaryschoolassessment.getStudents.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStudentData {

    @SerializedName("STUDENT_ID")
    @Expose
    private String sTUDENTID;
    @SerializedName("STUDENT_NAME")
    @Expose
    private String sTUDENTNAME;
    @SerializedName("STUDENT_FATHER_NAME")
    @Expose
    private String sTUDENTFATHERNAME;
    @SerializedName("STUDENT_MOTHER_NAME")
    @Expose
    private String sTUDENTMOTHERNAME;
    @SerializedName("STUDENT_DOB")
    @Expose
    private String sTUDENTDOB;
    @SerializedName("STUDENT_GENDER")
    @Expose
    private String sTUDENTGENDER;
    @SerializedName("STUDENT_CONTACT_PRIMARY")
    @Expose
    private String sTUDENTCONTACTPRIMARY;
    @SerializedName("STUDENT_CONTACT_SECONDARY")
    @Expose
    private String sTUDENTCONTACTSECONDARY;
    @SerializedName("STUDENT_CONTACT_EMAIL")
    @Expose
    private String sTUDENTCONTACTEMAIL;
    @SerializedName("STUDENT_ADDRESS_PERMANENT")
    @Expose
    private String sTUDENTADDRESSPERMANENT;
    @SerializedName("STUDENT_ADDRESS_CORRESPON")
    @Expose
    private String sTUDENTADDRESSCORRESPON;
    @SerializedName("STUDENT_IMAGE")
    @Expose
    private String sTUDENTIMAGE;
    @SerializedName("STUDENT_SESSION")
    @Expose
    private String sTUDENTSESSION;
    @SerializedName("STUDENT_CLASS")
    @Expose
    private String sTUDENTCLASS;
    @SerializedName("STUDENT_ACTIVATION")
    @Expose
    private String sTUDENTACTIVATION;
    @SerializedName("STUDENT_DATETIME")
    @Expose
    private String sTUDENTDATETIME;
    @SerializedName("STUDENT_MODIFY_DATETIME")
    @Expose
    private String sTUDENTMODIFYDATETIME;
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;


    public String getSTUDENTID() {
        return sTUDENTID;
    }

    public void setSTUDENTID(String sTUDENTID) {
        this.sTUDENTID = sTUDENTID;
    }

    public String getSTUDENTNAME() {
        return sTUDENTNAME;
    }

    public void setSTUDENTNAME(String sTUDENTNAME) {
        this.sTUDENTNAME = sTUDENTNAME;
    }

    public String getSTUDENTFATHERNAME() {
        return sTUDENTFATHERNAME;
    }

    public void setSTUDENTFATHERNAME(String sTUDENTFATHERNAME) {
        this.sTUDENTFATHERNAME = sTUDENTFATHERNAME;
    }

    public String getSTUDENTMOTHERNAME() {
        return sTUDENTMOTHERNAME;
    }

    public void setSTUDENTMOTHERNAME(String sTUDENTMOTHERNAME) {
        this.sTUDENTMOTHERNAME = sTUDENTMOTHERNAME;
    }

    public String getSTUDENTDOB() {
        return sTUDENTDOB;
    }

    public void setSTUDENTDOB(String sTUDENTDOB) {
        this.sTUDENTDOB = sTUDENTDOB;
    }

    public String getSTUDENTGENDER() {
        return sTUDENTGENDER;
    }

    public void setSTUDENTGENDER(String sTUDENTGENDER) {
        this.sTUDENTGENDER = sTUDENTGENDER;
    }

    public String getSTUDENTCONTACTPRIMARY() {
        return sTUDENTCONTACTPRIMARY;
    }

    public void setSTUDENTCONTACTPRIMARY(String sTUDENTCONTACTPRIMARY) {
        this.sTUDENTCONTACTPRIMARY = sTUDENTCONTACTPRIMARY;
    }

    public String getSTUDENTCONTACTSECONDARY() {
        return sTUDENTCONTACTSECONDARY;
    }

    public void setSTUDENTCONTACTSECONDARY(String sTUDENTCONTACTSECONDARY) {
        this.sTUDENTCONTACTSECONDARY = sTUDENTCONTACTSECONDARY;
    }

    public String getSTUDENTCONTACTEMAIL() {
        return sTUDENTCONTACTEMAIL;
    }

    public void setSTUDENTCONTACTEMAIL(String sTUDENTCONTACTEMAIL) {
        this.sTUDENTCONTACTEMAIL = sTUDENTCONTACTEMAIL;
    }

    public String getSTUDENTADDRESSPERMANENT() {
        return sTUDENTADDRESSPERMANENT;
    }

    public void setSTUDENTADDRESSPERMANENT(String sTUDENTADDRESSPERMANENT) {
        this.sTUDENTADDRESSPERMANENT = sTUDENTADDRESSPERMANENT;
    }

    public String getSTUDENTADDRESSCORRESPON() {
        return sTUDENTADDRESSCORRESPON;
    }

    public void setSTUDENTADDRESSCORRESPON(String sTUDENTADDRESSCORRESPON) {
        this.sTUDENTADDRESSCORRESPON = sTUDENTADDRESSCORRESPON;
    }

    public String getSTUDENTIMAGE() {
        return sTUDENTIMAGE;
    }

    public void setSTUDENTIMAGE(String sTUDENTIMAGE) {
        this.sTUDENTIMAGE = sTUDENTIMAGE;
    }

    public String getSTUDENTSESSION() {
        return sTUDENTSESSION;
    }

    public void setSTUDENTSESSION(String sTUDENTSESSION) {
        this.sTUDENTSESSION = sTUDENTSESSION;
    }

    public String getSTUDENTCLASS() {
        return sTUDENTCLASS;
    }

    public void setSTUDENTCLASS(String sTUDENTCLASS) {
        this.sTUDENTCLASS = sTUDENTCLASS;
    }

    public String getSTUDENTACTIVATION() {
        return sTUDENTACTIVATION;
    }

    public void setSTUDENTACTIVATION(String sTUDENTACTIVATION) {
        this.sTUDENTACTIVATION = sTUDENTACTIVATION;
    }

    public String getSTUDENTDATETIME() {
        return sTUDENTDATETIME;
    }

    public void setSTUDENTDATETIME(String sTUDENTDATETIME) {
        this.sTUDENTDATETIME = sTUDENTDATETIME;
    }

    public String getSTUDENTMODIFYDATETIME() {
        return sTUDENTMODIFYDATETIME;
    }

    public void setSTUDENTMODIFYDATETIME(String sTUDENTMODIFYDATETIME) {
        this.sTUDENTMODIFYDATETIME = sTUDENTMODIFYDATETIME;
    }

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

}
