
package com.dupleit.kotlin.primaryschoolassessment.fragments.Profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageData {

    @SerializedName("TEACHER_IMAGE")
    @Expose
    private String tEACHERIMAGE;

    public String getTEACHERIMAGE() {
        return tEACHERIMAGE;
    }

    public void setTEACHERIMAGE(String tEACHERIMAGE) {
        this.tEACHERIMAGE = tEACHERIMAGE;
    }

}
