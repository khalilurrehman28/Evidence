package com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.model;

public class MarksListComparator {


    String marks;

    public MarksListComparator(String marks, String description) {
        this.marks = marks;
        this.description = description;
    }

    String description;


    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
