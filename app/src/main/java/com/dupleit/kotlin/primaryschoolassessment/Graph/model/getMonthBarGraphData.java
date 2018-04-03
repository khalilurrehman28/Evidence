
package com.dupleit.kotlin.primaryschoolassessment.Graph.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getMonthBarGraphData {

    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("count")
    @Expose
    private String count;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
