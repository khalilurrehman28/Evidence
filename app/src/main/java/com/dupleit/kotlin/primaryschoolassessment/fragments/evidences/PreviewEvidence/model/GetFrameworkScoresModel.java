
package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFrameworkScoresModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<getMediaData> data = null;
    @SerializedName("data1")
    @Expose
    private List<getScoreData> data1 = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<getMediaData> getData() {
        return data;
    }

    public void setData(List<getMediaData> data) {
        this.data = data;
    }

    public List<getScoreData> getData1() {
        return data1;
    }

    public void setData1(List<getScoreData> data1) {
        this.data1 = data1;
    }

}
