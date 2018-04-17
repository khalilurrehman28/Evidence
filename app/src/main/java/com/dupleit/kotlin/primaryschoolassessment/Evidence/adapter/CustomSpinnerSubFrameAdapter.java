package com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.SubTitleData;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;

import java.util.ArrayList;

/**
 * Created by mandeep on 9/8/17.
 */

public class CustomSpinnerSubFrameAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private ArrayList<SubTitleData> subframeworkList;

    public CustomSpinnerSubFrameAdapter(Context context, ArrayList<SubTitleData> asr) {
        this.subframeworkList =asr;
        activity = context;
    }



    public int getCount()
    {
        return subframeworkList.size();
    }

    public Object getItem(int i)
    {
        return subframeworkList.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        final SubTitleData subFramework = subframeworkList.get(position);
        TextView txt = new TextView(activity);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER);
        String frameName = subFramework.getFRAMEWORKSUB();
        if (!frameName.equals("")){
            txt.setText( subFramework.getFRAMEWORKSUB()/*+"  ("+new DateConverter().convertDate(subFramework.getFRAMEWORKDATETIME())+")"*/);
        }
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        final SubTitleData subFramework = subframeworkList.get(i);

        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.CENTER);
        txt.setSingleLine(true);
        txt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        String subframeName = subFramework.getFRAMEWORKSUB();
        if (!subframeName.equals("")){
            txt.setText( subFramework.getFRAMEWORKSUB()/*+"  ("+new DateConverter().convertDate(subFramework.getFRAMEWORKDATETIME())+")"*/);
        }
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

}