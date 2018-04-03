
package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.DownloadPdfModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadApi {

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
    private String data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DownloadApi() {
    }

    /**
     * 
     * @param status
     * @param data
     * @param code
     * @param msg
     */
    public DownloadApi(Boolean status, String code, String msg, String data) {
        super();
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
