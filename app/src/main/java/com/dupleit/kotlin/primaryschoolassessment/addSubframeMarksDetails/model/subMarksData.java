
package com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class subMarksData {

    @SerializedName("MARKS_DETAIL_ID")
    @Expose
    private String mARKSDETAILID;
    @SerializedName("SUB_TITLE_ID")
    @Expose
    private String sUBTITLEID;
    @SerializedName("DESCRIPTION")
    @Expose
    private String dESCRIPTION;
    @SerializedName("MARKS")
    @Expose
    private String mARKS;
    @SerializedName("DATETIME")
    @Expose
    private String dATETIME;
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;

    public String getMARKSDETAILID() {
        return mARKSDETAILID;
    }

    public void setMARKSDETAILID(String mARKSDETAILID) {
        this.mARKSDETAILID = mARKSDETAILID;
    }

    public String getSUBTITLEID() {
        return sUBTITLEID;
    }

    public void setSUBTITLEID(String sUBTITLEID) {
        this.sUBTITLEID = sUBTITLEID;
    }

    public String getDESCRIPTION() {
        return dESCRIPTION;
    }

    public void setDESCRIPTION(String dESCRIPTION) {
        this.dESCRIPTION = dESCRIPTION;
    }

    public String getMARKS() {
        return mARKS;
    }

    public void setMARKS(String mARKS) {
        this.mARKS = mARKS;
    }

    public String getDATETIME() {
        return dATETIME;
    }

    public void setDATETIME(String dATETIME) {
        this.dATETIME = dATETIME;
    }

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

}
