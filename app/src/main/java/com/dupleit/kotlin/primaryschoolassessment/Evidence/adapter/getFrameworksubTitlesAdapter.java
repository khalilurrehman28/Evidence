package com.dupleit.kotlin.primaryschoolassessment.Evidence.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.SubTitleData;
import com.dupleit.kotlin.primaryschoolassessment.R;
import java.util.List;

/**
 * Created by mandeep on 4/9/17.
 */

public class getFrameworksubTitlesAdapter extends RecyclerView.Adapter<getFrameworksubTitlesAdapter.MyViewHolder>{
    private Context mContext;
    private List<SubTitleData> framSubtitleList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView frameworkSubTitle,subtitleMaxMarks;
        public EditText getsubtitleObtainMarks;
        public LinearLayout frame;
        public MyViewHolder(View view) {
            super(view);

            frameworkSubTitle = (TextView) view.findViewById(R.id.frameworkSubTitle);
            subtitleMaxMarks=view.findViewById(R.id.frameworkSubTitleMaxMarks);
            getsubtitleObtainMarks = view.findViewById(R.id.subtitleObtainMarks);
            frame = view.findViewById(R.id.frame);
        }
    }
    public getFrameworksubTitlesAdapter(Context mContext, List<SubTitleData> framSubtitleList) {
        this.mContext = mContext;
        this.framSubtitleList = framSubtitleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_framework_subtitle, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SubTitleData frameworks = framSubtitleList.get(position);

        if (!frameworks.getFRAMEWORKSUB().equals("")){
            holder.frameworkSubTitle.setText(frameworks.getFRAMEWORKSUB());
        }else{
            holder.frameworkSubTitle.setText("");
        }
        if (!frameworks.getSCORE().equals("")){

            holder.subtitleMaxMarks.setText("/ "+frameworks.getSCORE());
        }else{
            holder.subtitleMaxMarks.setText("/");
        }



        holder.getsubtitleObtainMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                frameworks.setEtGetScore(s.toString());
                //notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return framSubtitleList.size();
    }

}
