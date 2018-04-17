package com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.model.subMarksData;

import java.util.List;

/**
 * Created by mandeep on 4/9/17.
 */

public class getSubMarksDetailAdapter extends RecyclerView.Adapter<getSubMarksDetailAdapter.MyViewHolder>{
    private Context mContext;
    private List<subMarksData> subMarksDetailList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView subTitleMark,subTitleMarkDetail;
        public LinearLayout frame;
        public MyViewHolder(View view) {
            super(view);

            subTitleMark = (TextView) view.findViewById(R.id.subTitleMark);
            subTitleMarkDetail=view.findViewById(R.id.subTitleMarkDetail);
            frame = view.findViewById(R.id.frame);
        }


    }
    public getSubMarksDetailAdapter(Context mContext, List<subMarksData> subMarksDetailList) {
        this.mContext = mContext;
        this.subMarksDetailList = subMarksDetailList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_preview_sub_marks_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final subMarksData frameworks = subMarksDetailList.get(position);

        if (!frameworks.getMARKS().equals("")){
            holder.subTitleMark.setText(frameworks.getMARKS());
        }else{
            holder.subTitleMark.setText("");
        }
        if (!frameworks.getDESCRIPTION().equals("")){

            holder.subTitleMarkDetail.setText(frameworks.getDESCRIPTION());
        }else{
            holder.subTitleMarkDetail.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return subMarksDetailList.size();
    }

}
