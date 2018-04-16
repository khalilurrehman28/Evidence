package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.model.getScoreData;

import java.util.List;

public class getFrameworkPreviewAdapter extends RecyclerView.Adapter<getFrameworkPreviewAdapter.GalleryViewHolder>{

    private Context context;
    private List<getScoreData> medialists;

    public class GalleryViewHolder extends RecyclerView.ViewHolder{


        public TextView frameworkSubTitle,frameworkDes;
        LinearLayout ll1,ll2,ll3,ll4,ll5,ll6,ll7,ll8,ll9,ll10;
        public View mCardView;

        public GalleryViewHolder(View itemView) {
            super(itemView);

            frameworkSubTitle = itemView.findViewById(R.id.frameworkSubTitle);
            frameworkDes = itemView.findViewById(R.id.frameworkDes);
            ll1= itemView.findViewById(R.id.ll1);
            ll2= itemView.findViewById(R.id.ll2);
            ll3= itemView.findViewById(R.id.ll3);
            ll4= itemView.findViewById(R.id.ll4);
            ll5= itemView.findViewById(R.id.ll5);
            ll6= itemView.findViewById(R.id.ll6);
            ll7= itemView.findViewById(R.id.ll7);
            ll8= itemView.findViewById(R.id.ll8);
            ll9= itemView.findViewById(R.id.ll9);
            ll10= itemView.findViewById(R.id.ll10);


        }

    }

    public getFrameworkPreviewAdapter(Context context, List<getScoreData> medialists) {
        this.context = context;
        this.medialists = medialists;

    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_assesment_preview, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        getScoreData frameworkData = medialists.get(position);
        holder.frameworkSubTitle.setText(frameworkData.getFRAMEWORKSUB());
        if (!frameworkData.getfRAMEWORKREMARK().equals("")){
            String s= "Des:  "+frameworkData.getfRAMEWORKREMARK();
            SpannableString ss1=  new SpannableString(s);
            ss1.setSpan(new RelativeSizeSpan(1.1f), 0,4, 0); // set size
            ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 4, 0);// set color

            holder.frameworkDes.setText(ss1);
        }else {
            holder.frameworkDes.setText("");
        }

Log.e("score",""+frameworkData.getSCORE());
        switch (frameworkData.getSCORE()) {
            case "0":
               // holder.ll1.setPadding(3,3,3,3);
                break;
            case "1":
                holder.ll1.setPadding(4,4,4,4);
                break;
            case "2":
                holder.ll2.setPadding(4,4,4,4);
                break;
            case "3":
                holder.ll3.setPadding(4,4,4,4);
                break;
            case "4":
                holder.ll4.setPadding(4,4,4,4);
                break;
            case "5":
                holder.ll5.setPadding(4,4,4,4);
                break;
            case "6":
                holder.ll6.setPadding(4,4,4,4);
                break;
            case "7":
                holder.ll7.setPadding(4,4,4,4);
                break;
            case "8":
                holder.ll8.setPadding(4,4,4,4);
                break;
            case "9":
                holder.ll9.setPadding(4,4,4,4);
                break;
            case "10":
                holder.ll10.setPadding(4,4,4,4);
                break;

            default:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return medialists.size();
    }
}
