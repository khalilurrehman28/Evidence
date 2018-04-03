package com.dupleit.kotlin.primaryschoolassessment.otherHelper;

import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.models.EvidencesData;

import java.util.Comparator;

/**
 * Created by khalil on 4/3/17.
 */

public class CustomObjectComparator implements Comparator<EvidencesData> {

    Boolean dataOrder;
    public CustomObjectComparator(Boolean order){
        this.dataOrder = order;
    }

    @Override
    public int compare(EvidencesData o1, EvidencesData o2) {
        if (dataOrder){
            return new DateFormatConverter().convertDate(o2.getEVIDENCEDATE()).compareTo(new DateFormatConverter().convertDate(o1.getEVIDENCEDATE()));
        }else{
            return new DateFormatConverter().convertDate(o1.getEVIDENCEDATE()).compareTo(new DateFormatConverter().convertDate(o2.getEVIDENCEDATE()));
        }
    }

}