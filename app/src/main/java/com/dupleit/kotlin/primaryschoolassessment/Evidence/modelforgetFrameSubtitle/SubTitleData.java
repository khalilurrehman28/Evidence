
package com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubTitleData implements Parcelable
{
    @SerializedName("FRAMEWORK_SUB")
    @Expose
    private String fRAMEWORKSUB;
    @SerializedName("FRAMEWORK_STATUS")
    @Expose
    private String fRAMEWORKSTATUS;
    @SerializedName("SCORE")
    @Expose
    private String sCORE;
    @SerializedName("sub_id")
    @Expose
    private String subId;

    public String getrEMARK() {
        return rEMARK;
    }

    public void setrEMARK(String rEMARK) {
        this.rEMARK = rEMARK;
    }

    @SerializedName("REMARK")
    @Expose
    private String rEMARK;


    private String etGetScore;
    public final static Parcelable.Creator<SubTitleData> CREATOR = new Creator<SubTitleData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SubTitleData createFromParcel(Parcel in) {
            return new SubTitleData(in);
        }

        public SubTitleData[] newArray(int size) {
            return (new SubTitleData[size]);
        }

    }
            ;

    protected SubTitleData(Parcel in) {
        this.fRAMEWORKSUB = ((String) in.readValue((String.class.getClassLoader())));
        this.fRAMEWORKSTATUS = ((String) in.readValue((String.class.getClassLoader())));
        this.sCORE = ((String) in.readValue((String.class.getClassLoader())));
        this.subId = ((String) in.readValue((String.class.getClassLoader())));
        this.etGetScore = ((String) in.readValue((String.class.getClassLoader())));

    }

    public SubTitleData() {
    }

    public String getFRAMEWORKSUB() {
        return fRAMEWORKSUB;
    }

    public void setFRAMEWORKSUB(String fRAMEWORKSUB) {
        this.fRAMEWORKSUB = fRAMEWORKSUB;
    }

    public String getFRAMEWORKSTATUS() {
        return fRAMEWORKSTATUS;
    }

    public void setFRAMEWORKSTATUS(String fRAMEWORKSTATUS) {
        this.fRAMEWORKSTATUS = fRAMEWORKSTATUS;
    }

    public String getSCORE() {
        return sCORE;
    }

    public void setSCORE(String sCORE) {
        this.sCORE = sCORE;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getEtGetScore() {
        return etGetScore;
    }

    public void setEtGetScore(String etGetScore) {
        this.etGetScore = etGetScore;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(fRAMEWORKSUB);
        dest.writeValue(fRAMEWORKSTATUS);
        dest.writeValue(sCORE);
        dest.writeValue(subId);
        dest.writeValue(etGetScore);

    }

    public int describeContents() {
        return 0;
    }

}