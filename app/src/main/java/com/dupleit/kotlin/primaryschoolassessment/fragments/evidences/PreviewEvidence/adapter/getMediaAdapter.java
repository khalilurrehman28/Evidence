package com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.PreviewEvidence.model.getMediaData;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Utils;

import java.util.HashMap;
import java.util.List;

public class getMediaAdapter extends RecyclerView.Adapter<getMediaAdapter.GalleryViewHolder>{

    private Context context;
    private List<getMediaData> medialists;

    public class GalleryViewHolder extends RecyclerView.ViewHolder{


        public ImageView mediaImage,videoCamera;
        public View mCardView;

        public GalleryViewHolder(View itemView) {
            super(itemView);

            mediaImage = itemView.findViewById(R.id.mediaImage);
            videoCamera = itemView.findViewById(R.id.videoCamera);

            mCardView = (CardView)itemView.findViewById(R.id.card_view);

        }

    }

    public getMediaAdapter(Context context, List<getMediaData> medialists) {
        this.context = context;
        this.medialists = medialists;

    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_preview_evidence, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        getMediaData mediaData = medialists.get(position);

        switch (mediaData.getType()){
            case 0:
                Glide.with(context).load(Utils.webUrlHome+mediaData.getIMAGEPATH()).into(holder.mediaImage);
                holder.videoCamera.setVisibility(View.GONE);
                break;
            case 1:
                Glide.with(context).load(Utils.webUrlHome+mediaData.getIMAGEPATH()).into(holder.mediaImage);
                holder.videoCamera.setVisibility(View.VISIBLE);

                Log.e("imagePath",""+Utils.webUrlHome+mediaData.getIMAGEPATH());
              /*  Bitmap bMap = ThumbnailUtils.createVideoThumbnail(Utils.webUrlHome+mediaData.getIMAGEPATH(), MediaStore.Video.Thumbnails.MICRO_KIND);
                holder.mediaImage.setImageBitmap(bMap);*/
                break;
        }
    }
    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
    @Override
    public int getItemCount() {
        return medialists.size();
    }
}
