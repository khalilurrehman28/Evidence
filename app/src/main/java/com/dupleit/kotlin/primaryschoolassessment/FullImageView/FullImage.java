package com.dupleit.kotlin.primaryschoolassessment.FullImageView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;*/
import com.bumptech.glide.Glide;
import com.dupleit.kotlin.primaryschoolassessment.R;

public class FullImage extends AppCompatActivity {
    String imagepath;
    ImageView fullimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_image);


        imagepath = getIntent().getStringExtra("ImagePath");
        Log.e("imagePath"," "+imagepath);
        // Toast.makeText(this, "path "+imagepath, Toast.LENGTH_SHORT).show();
        fullimage = (ImageView)findViewById(R.id.fullimageview);
        Glide.with(FullImage.this).load(imagepath).into(fullimage);

       /* Glide.with(this)
                .load(imagepath)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(fullimage);*/
    }
}
