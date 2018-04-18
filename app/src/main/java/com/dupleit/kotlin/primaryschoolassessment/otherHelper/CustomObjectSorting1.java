package com.dupleit.kotlin.primaryschoolassessment.otherHelper;

import com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.model.subMarksData;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.models.EvidencesData;

import java.util.Comparator;

/**
 * Created by khalil on 4/3/17.
 */

public class CustomObjectSorting1 implements Comparator<subMarksData> {

    Boolean dataOrder;
    public CustomObjectSorting1(Boolean order){
        this.dataOrder = order;
    }

    @Override
    public int compare(subMarksData o1, subMarksData o2) {
        if (dataOrder){
            return o2.getMARKS().compareTo(o1.getMARKS());
        }else{
            return o1.getMARKS().compareTo(o2.getMARKS());
        }
    }
}