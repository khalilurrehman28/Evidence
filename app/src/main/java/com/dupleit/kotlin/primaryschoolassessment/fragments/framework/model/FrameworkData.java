
package com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrameworkData {

    @SerializedName("FRAMEWORK_TITLE")
    @Expose
    private String fRAMEWORKTITLE;
    @SerializedName("FRAMEWORK_ID")
    @Expose
    private String fRAMEWORKID;
    @SerializedName("FRAMEWORK_DATETIME")
    @Expose
    private String fRAMEWORKDATETIME;

    public String getFRAMEWORKTITLE() {
        return fRAMEWORKTITLE;
    }

    public void setFRAMEWORKTITLE(String fRAMEWORKTITLE) {
        this.fRAMEWORKTITLE = fRAMEWORKTITLE;
    }

    public String getFRAMEWORKID() {
        return fRAMEWORKID;
    }

    public void setFRAMEWORKID(String fRAMEWORKID) {
        this.fRAMEWORKID = fRAMEWORKID;
    }

    public String getFRAMEWORKDATETIME() {
        return fRAMEWORKDATETIME;
    }

    public void setFRAMEWORKDATETIME(String fRAMEWORKDATETIME) {
        this.fRAMEWORKDATETIME = fRAMEWORKDATETIME;
    }

}
