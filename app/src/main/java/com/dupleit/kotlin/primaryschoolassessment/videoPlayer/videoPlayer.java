package com.dupleit.kotlin.primaryschoolassessment.videoPlayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.dupleit.kotlin.primaryschoolassessment.R;

public class videoPlayer extends AppCompatActivity {
    VideoView mVideoView;
    String videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Video");
        videoPath = getIntent().getStringExtra("VideoPath");
        mVideoView = findViewById(R.id.videoView);
        Log.e("VideoPath",""+videoPath);
        Uri uriVideo = Uri.parse(videoPath);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(uriVideo);
        mVideoView.seekTo(100);
        mVideoView.requestFocus();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
