
package com.dupleit.kotlin.primaryschoolassessment.PieChart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSessionPieData {

    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("count")
    @Expose
    private String count;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
