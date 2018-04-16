package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.AssessmentRecord.adapter.assessmentRecordAdapter;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.gettingstudentEvidence.models.EvidencesData;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mandeep on 4/9/17.
 */

public class getEvidneceByStudentAdapter extends RecyclerView.Adapter<getEvidneceByStudentAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    public ArrayList<EvidencesData> studentList;
    private ArrayList<EvidencesData> studentListFiltered;
    private EvidenceAdapterListener listener;
    private List<String> selectedIds = new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView evidenceTitle,frameworkTitle,evidenceGrade,evidenceTags;
                LinearLayout frame,layoutBackground;
        public CardView mCardView;
        public MyViewHolder(View view) {
            super(view);

            evidenceTitle = view.findViewById(R.id.evidenceTitle);
            frameworkTitle = view.findViewById(R.id.frameworkTitle);
            evidenceGrade= view.findViewById(R.id.evidenceGrade);
            evidenceTags = view.findViewById(R.id.evidenceTags);
            frame= view.findViewById(R.id.frame);
           // mCardView = (CardView) view.findViewById(R.id.card_view);
            layoutBackground= view.findViewById(R.id.layoutBackground);
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onEvidenceSelected(studentListFiltered.get(getAdapterPosition()));
                }
            });

        }

    }
    public getEvidneceByStudentAdapter(Context mContext, ArrayList<EvidencesData> studentList, EvidenceAdapterListener listener) {
        this.mContext = mContext;
        this.studentList = studentList;
        this.studentListFiltered = studentList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_evidence, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EvidencesData evidences = studentListFiltered.get(position);
        //Random rnd = new Random();
        //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //view.setBackgroundColor(color);

        //((GradientDrawable)holder.layoutBackground.getBackground()).setColor(evidences.getColorIndex());
        //holder.evidenceTitle.setPadding(12,12,12,12);

        String id = evidences.getEVIDENCEID();

        if (selectedIds.contains(id)){
            //if item is selected then,set foreground color of FrameLayout.
            holder.frame.setBackgroundColor(mContext.getResources().getColor(R.color.colorControlActivated));

        }
        else {
            //else remove selected item color.
            holder.frame.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }

        /*if (evidences.getSelected()) {
            holder.frame.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            //holder.evidenceTitle.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            holder.frame.setBackgroundColor(mContext.getResources().getColor(R.color.white));

//            holder.evidenceTitle.setTextColor(mContext.getResources().getColor(R.color.white));
        }*/

        holder.evidenceTitle.setText(evidences.getTitle());
        holder.frameworkTitle.setText("E Date "+evidences.getEVIDENCEDATE());

        if (evidences.geteVIDENCETAGS()==null ){
            holder.evidenceTags.setVisibility(View.GONE);
            holder.evidenceTags.setText("");
        }else {
            holder.evidenceTags.setText("#"+evidences.geteVIDENCETAGS().replaceAll(",\\s+"," #"));
            Log.d("Tags1 ", evidences.geteVIDENCETAGS().replaceAll(",\\s+"," #"));
        }

        float totalFrameScore = Integer.parseInt(evidences.getsCORE());
        float framecount = evidences.getcOUNT();

        float gradeAverage = totalFrameScore/framecount;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        float oneDigitsF = Float.valueOf(decimalFormat.format(gradeAverage));
        Range<Float> range = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            if (new Range<>(9.1f,10.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("A1");
                holder.evidenceGrade.setTextColor(Color.parseColor("#2E7D32"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.green_line);
            }else if (new Range<>(8.1f,9.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("A2");
                holder.evidenceGrade.setTextColor(Color.parseColor("#2E7D32"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.green_line);
            }else if (new Range<>(7.1f,8.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("B1");
                holder.evidenceGrade.setTextColor(Color.parseColor("#2E7D32"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.green_line);
            }else if (new Range<>(6.1f,7.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("B2");
                holder.evidenceGrade.setTextColor(Color.parseColor("#2E7D32"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.green_line);
            }else if (new Range<>(5.1f,6.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("C1");
                holder.evidenceGrade.setTextColor(Color.parseColor("#efcd37"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.yellow_line);
            }else if (new Range<>(4.1f,5.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("C2");
                holder.evidenceGrade.setTextColor(Color.parseColor("#efcd37"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.yellow_line);
            }else if (new Range<>(3.1f,4.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("D");
                holder.evidenceGrade.setTextColor(Color.parseColor("#e42f2f"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.red_line);
            }else if (new Range<>(2.1f,3.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("E1");
                holder.evidenceGrade.setTextColor(Color.parseColor("#e42f2f"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.red_line);
            }else if (new Range<>(1.1f,2.0f).contains(oneDigitsF)){
                holder.evidenceGrade.setText("E2");
                holder.evidenceGrade.setTextColor(Color.parseColor("#e42f2f"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.red_line);
            }else {
                holder.evidenceGrade.setText("F");
                holder.evidenceGrade.setTextColor(Color.parseColor("#e42f2f"));
                holder.evidenceGrade.setBackgroundResource(R.drawable.red_line);
            }
        }

       // Toast.makeText(mContext, "grade "+gradeWithProcess, Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return studentListFiltered.size();
    }

    public EvidencesData getItem(int position){
        return studentList.get(position);
    }

    public void setSelectedIds(List<String> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    studentListFiltered = studentList;
                } else {
                    ArrayList<EvidencesData> filteredList = new ArrayList<>();
                    for (EvidencesData row : studentList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.geteVIDENCETAGS().contains(charSequence) || row.getEVIDENCEDATE().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    studentListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = studentListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                studentListFiltered = (ArrayList<EvidencesData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface EvidenceAdapterListener {
        void onEvidenceSelected(EvidencesData getStudentData);
    }

}
