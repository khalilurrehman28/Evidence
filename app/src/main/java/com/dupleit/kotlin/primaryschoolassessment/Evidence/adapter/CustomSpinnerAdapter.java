package com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.DateConverter;

import java.util.ArrayList;

/**
 * Created by mandeep on 9/8/17.
 */

public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private ArrayList<FrameworkData> frameworkList;

    public CustomSpinnerAdapter(Context context, ArrayList<FrameworkData> asr) {
        this.frameworkList =asr;
        activity = context;
    }



    public int getCount()
    {
        return frameworkList.size();
    }

    public Object getItem(int i)
    {
        return frameworkList.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        final FrameworkData notice = frameworkList.get(position);
        TextView txt = new TextView(activity);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        String frameName = notice.getFRAMEWORKTITLE();
        if (!frameName.equals("")){
            txt.setText( notice.getFRAMEWORKTITLE()+"  ("+new DateConverter().convertDate(notice.getFRAMEWORKDATETIME())+")");
        }
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        final FrameworkData notice = frameworkList.get(i);

        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        String frameName = notice.getFRAMEWORKTITLE();
        if (!frameName.equals("")){
            txt.setText( notice.getFRAMEWORKTITLE()+"  ("+new DateConverter().convertDate(notice.getFRAMEWORKDATETIME())+")");
        }
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

}