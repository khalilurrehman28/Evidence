
package com.dupleit.kotlin.primaryschoolassessment.fragments.framework.parentFrameworkModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class parentFrameworkData {

    @SerializedName("CATEGORY_ID")
    @Expose
    private String cATEGORYID;
    @SerializedName("CATEGORY_NAME")
    @Expose
    private String cATEGORYNAME;
    @SerializedName("DATETIME")
    @Expose
    private String dATETIME;
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;

    public String getCATEGORYID() {
        return cATEGORYID;
    }

    public void setCATEGORYID(String cATEGORYID) {
        this.cATEGORYID = cATEGORYID;
    }

    public String getCATEGORYNAME() {
        return cATEGORYNAME;
    }

    public void setCATEGORYNAME(String cATEGORYNAME) {
        this.cATEGORYNAME = cATEGORYNAME;
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
