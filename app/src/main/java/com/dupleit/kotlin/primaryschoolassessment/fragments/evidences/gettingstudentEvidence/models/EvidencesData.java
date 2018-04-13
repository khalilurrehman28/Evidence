
package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EvidencesData {

    @SerializedName("EVIDENCE_ID")
    @Expose
    private String eVIDENCEID;
    @SerializedName("EVIDENCE_STUDENT_ID")
    @Expose
    private String eVIDENCESTUDENTID;
    @SerializedName("EVIDENCE_TEACHER_ID")
    @Expose
    private String eVIDENCETEACHERID;
    @SerializedName("EVIDENCE_IMAGE_VIDEO")
    @Expose
    private String eVIDENCEIMAGEVIDEO;
    @SerializedName("EVIDENCE_COMMENT")
    @Expose
    private String eVIDENCECOMMENT;
    @SerializedName("EVIDENCE_FRAMEWORK_ID")
    @Expose
    private String eVIDENCEFRAMEWORKID;
    @SerializedName("EVIDENCE_DATE")
    @Expose
    private String eVIDENCEDATE;
    @SerializedName("EVIDENCE_DATETIME")
    @Expose
    private String eVIDENCEDATETIME;
    @SerializedName("EVIDENCE_MODIFY_DATETIME")
    @Expose
    private String eVIDENCEMODIFYDATETIME;
    @SerializedName("EVIDENCE_STATUS")
    @Expose
    private String eVIDENCESTATUS;
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

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("SCORE")
    @Expose
    private String sCORE;

    public String getsCORE() {
        return sCORE;
    }

    public void setsCORE(String sCORE) {
        this.sCORE = sCORE;
    }

    public Integer getcOUNT() {
        return cOUNT;
    }

    public void setcOUNT(Integer cOUNT) {
        this.cOUNT = cOUNT;
    }

    @SerializedName("COUNT")
    @Expose
    private Integer cOUNT;
    /*public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    Boolean selected = false;*/

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public int colorIndex;

    public String getEVIDENCEID() {
        return eVIDENCEID;
    }

    public void setEVIDENCEID(String eVIDENCEID) {
        this.eVIDENCEID = eVIDENCEID;
    }

    public String getEVIDENCESTUDENTID() {
        return eVIDENCESTUDENTID;
    }

    public void setEVIDENCESTUDENTID(String eVIDENCESTUDENTID) {
        this.eVIDENCESTUDENTID = eVIDENCESTUDENTID;
    }

    public String getEVIDENCETEACHERID() {
        return eVIDENCETEACHERID;
    }

    public void setEVIDENCETEACHERID(String eVIDENCETEACHERID) {
        this.eVIDENCETEACHERID = eVIDENCETEACHERID;
    }

    public String getEVIDENCEIMAGEVIDEO() {
        return eVIDENCEIMAGEVIDEO;
    }

    public void setEVIDENCEIMAGEVIDEO(String eVIDENCEIMAGEVIDEO) {
        this.eVIDENCEIMAGEVIDEO = eVIDENCEIMAGEVIDEO;
    }

    public String getEVIDENCECOMMENT() {
        return eVIDENCECOMMENT;
    }

    public void setEVIDENCECOMMENT(String eVIDENCECOMMENT) {
        this.eVIDENCECOMMENT = eVIDENCECOMMENT;
    }

    public String getEVIDENCEFRAMEWORKID() {
        return eVIDENCEFRAMEWORKID;
    }

    public void setEVIDENCEFRAMEWORKID(String eVIDENCEFRAMEWORKID) {
        this.eVIDENCEFRAMEWORKID = eVIDENCEFRAMEWORKID;
    }

    public String getEVIDENCEDATE() {
        return eVIDENCEDATE;
    }

    public void setEVIDENCEDATE(String eVIDENCEDATE) {
        this.eVIDENCEDATE = eVIDENCEDATE;
    }

    public String getEVIDENCEDATETIME() {
        return eVIDENCEDATETIME;
    }

    public void setEVIDENCEDATETIME(String eVIDENCEDATETIME) {
        this.eVIDENCEDATETIME = eVIDENCEDATETIME;
    }

    public String getEVIDENCEMODIFYDATETIME() {
        return eVIDENCEMODIFYDATETIME;
    }

    public void setEVIDENCEMODIFYDATETIME(String eVIDENCEMODIFYDATETIME) {
        this.eVIDENCEMODIFYDATETIME = eVIDENCEMODIFYDATETIME;
    }

    public String getEVIDENCESTATUS() {
        return eVIDENCESTATUS;
    }

    public void setEVIDENCESTATUS(String eVIDENCESTATUS) {
        this.eVIDENCESTATUS = eVIDENCESTATUS;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
