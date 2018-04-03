package com.dupleit.kotlin.primaryschoolassessment.fragments.framework.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.model.FrameworkData;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.DateConverter;

import java.util.List;

/**
 * Created by mandeep on 4/9/17.
 */

public class getFrameworksTitlesAdapter extends RecyclerView.Adapter<getFrameworksTitlesAdapter.MyViewHolder>{
    private Context mContext;
    private List<FrameworkData> studentsOfClassList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView frameworkTitle, createdDate;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);

            frameworkTitle = (TextView) view.findViewById(R.id.frameworkTitle);
            createdDate = (TextView) view.findViewById(R.id.createdDate);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
    public getFrameworksTitlesAdapter(Context mContext, List<FrameworkData> studentsOfClassList) {
        this.mContext = mContext;
        this.studentsOfClassList = studentsOfClassList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_framework_title, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final getFrameworksTitlesAdapter.MyViewHolder holder, int position) {
        FrameworkData frameworks = studentsOfClassList.get(position);
        holder.frameworkTitle.setText(frameworks.getFRAMEWORKTITLE());
        holder.createdDate.setText("Created on "+new DateConverter().convertDate(frameworks.getFRAMEWORKDATETIME()));


    }

    @Override
    public int getItemCount() {
        return studentsOfClassList.size();
    }
}
