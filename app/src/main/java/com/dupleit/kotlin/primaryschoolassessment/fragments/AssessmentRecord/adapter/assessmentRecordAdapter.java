package com.dupleit.kotlin.primaryschoolassessment.fragments.AssessmentRecord.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.getStudents.models.GetStudentData;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mandeep on 4/9/17.
 */

public class assessmentRecordAdapter extends RecyclerView.Adapter<assessmentRecordAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    public ArrayList<GetStudentData> studentList;
    private List<GetStudentData> studentListFiltered;
    private ContactsAdapterListener listener;
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public CircleImageView studentImage;
        public TextView studentName, gardianName,checkMaleFemale;
        public CardView mCardView;
        public MyViewHolder(View view) {
            super(view);
            studentImage = view.findViewById(R.id.stuImage);
            studentName = view.findViewById(R.id.stuName);
            gardianName = view.findViewById(R.id.gardianName);
            checkMaleFemale =view.findViewById(R.id.checkMaleFemale);
            mCardView = view.findViewById(R.id.card_view);
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(studentListFiltered.get(getAdapterPosition()));
                }
            });

        }


    }
    public assessmentRecordAdapter(Context mContext, ArrayList<GetStudentData> studentList, ContactsAdapterListener listener) {
        this.mContext = mContext;
        this.studentList = studentList;
        this.studentListFiltered = studentList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_student_preview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final assessmentRecordAdapter.MyViewHolder holder, int position) {
        GetStudentData classstudent = studentListFiltered.get(position);
        holder.mCardView.setTag(position);
        Glide.with(mContext).load(Utils.webUrlHome+classstudent.getSTUDENTIMAGE()).into(holder.studentImage);
        holder.studentName.setText(classstudent.getSTUDENTNAME());

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //view.setBackgroundColor(color);
        ((GradientDrawable)holder.checkMaleFemale.getBackground()).setColor(color);
        holder.checkMaleFemale.setPadding(2,2,2,2);

        if(classstudent.getSTUDENTGENDER().equals("1")){
            holder.checkMaleFemale.setText("M");
        }else {
            holder.checkMaleFemale.setText("F");
        }
        if (!classstudent.getSTUDENTFATHERNAME().equals("")){
            holder.gardianName.setText("Guardian: "+classstudent.getSTUDENTFATHERNAME());
        }else {
            holder.gardianName.setText("");
        }

        Log.e("adapter",""+"  image  "+Utils.webUrl+classstudent.getSTUDENTIMAGE()+"  name  "+classstudent.getSTUDENTNAME()+ "  Roll No. "+classstudent.getSTUDENTDOB());
    }

    @Override
    public int getItemCount() {
        return studentListFiltered.size();
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
                    List<GetStudentData> filteredList = new ArrayList<>();
                    for (GetStudentData row : studentList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSTUDENTNAME().toLowerCase().contains(charString.toLowerCase()) || row.getSTUDENTFATHERNAME().contains(charSequence)) {
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
                studentListFiltered = (ArrayList<GetStudentData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface ContactsAdapterListener {
        void onContactSelected(GetStudentData getStudentData);
    }
}
