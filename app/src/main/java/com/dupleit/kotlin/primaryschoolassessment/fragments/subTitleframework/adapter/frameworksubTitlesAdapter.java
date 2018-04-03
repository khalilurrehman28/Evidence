package com.dupleit.kotlin.primaryschoolassessment.fragments.subTitleframework.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dupleit.kotlin.primaryschoolassessment.Evidence.modelforgetFrameSubtitle.SubTitleData;
import com.dupleit.kotlin.primaryschoolassessment.R;

import java.util.List;
import java.util.Random;

/**
 * Created by mandeep on 4/9/17.
 */

public class frameworksubTitlesAdapter extends RecyclerView.Adapter<frameworksubTitlesAdapter.MyViewHolder>{
    private Context mContext;
    private List<SubTitleData> frameSubtitleList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView frameworkSubTitle,frameworkScore;
        public LinearLayout frame,layoutBackground;
        public MyViewHolder(View view) {
            super(view);

            frameworkSubTitle =  view.findViewById(R.id.frameworkSubTitle);
            frameworkScore =  view.findViewById(R.id.frameworkSubTitleScore);
            layoutBackground = view.findViewById(R.id.layoutBackground);

            frame = view.findViewById(R.id.frame);
        }
    }
    public frameworksubTitlesAdapter(Context mContext, List<SubTitleData> frameSubtitleList) {
        this.mContext = mContext;
        this.frameSubtitleList = frameSubtitleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_subtitles, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final frameworksubTitlesAdapter.MyViewHolder holder, int position) {
        SubTitleData frameworks = frameSubtitleList.get(position);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //view.setBackgroundColor(color);
        ((GradientDrawable)holder.layoutBackground.getBackground()).setColor(color);
        //holder.layoutBackground.setPadding(2,2,2,2);
        if (!frameworks.getFRAMEWORKSUB().equals("")){

            holder.frameworkSubTitle.setText(frameworks.getFRAMEWORKSUB());
        }else{
            holder.frame.setVisibility(View.GONE);
        }

        if (!frameworks.getSCORE().equals("")){

            holder.frameworkScore.setText(frameworks.getSCORE());
        }else{
            holder.frameworkScore.setText("");

        }


    }

    @Override
    public int getItemCount() {
        return frameSubtitleList.size();
    }
}
