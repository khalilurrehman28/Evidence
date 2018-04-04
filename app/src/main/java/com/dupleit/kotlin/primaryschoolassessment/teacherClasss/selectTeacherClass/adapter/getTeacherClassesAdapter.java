package com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.adapter;

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
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.model.getClassesData;

import java.util.List;

/**
 * Created by mandeep on 4/9/17.
 */

public class getTeacherClassesAdapter extends RecyclerView.Adapter<getTeacherClassesAdapter.MyViewHolder>{
    private Context mContext;
    private List<getClassesData> studentsOfClassList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView className;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);

            className = (TextView) view.findViewById(R.id.className);

            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
    public getTeacherClassesAdapter(Context mContext, List<getClassesData> studentsOfClassList) {
        this.mContext = mContext;
        this.studentsOfClassList = studentsOfClassList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_teacher_classes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        getClassesData frameworks = studentsOfClassList.get(position);
        holder.className.setText("Class "+frameworks.getCLASSNAME());



    }

    @Override
    public int getItemCount() {
        return studentsOfClassList.size();
    }
}
