
package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getMediaData {

    @SerializedName("IMAGE_ID")
    @Expose
    private String iMAGEID;
    @SerializedName("IMAGE_PATH")
    @Expose
    private String iMAGEPATH;
    @SerializedName("IMAGE_TIMESTAMP")
    @Expose
    private String iMAGETIMESTAMP;

    private int type;

    public String getIMAGEID() {
        return iMAGEID;
    }

    public void setIMAGEID(String iMAGEID) {
        this.iMAGEID = iMAGEID;
    }

    public String getIMAGEPATH() {
        return iMAGEPATH;
    }

    public void setIMAGEPATH(String iMAGEPATH) {
        this.iMAGEPATH = iMAGEPATH;
    }

    public String getIMAGETIMESTAMP() {
        return iMAGETIMESTAMP;
    }

    public void setIMAGETIMESTAMP(String iMAGETIMESTAMP) {
        this.iMAGETIMESTAMP = iMAGETIMESTAMP;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
