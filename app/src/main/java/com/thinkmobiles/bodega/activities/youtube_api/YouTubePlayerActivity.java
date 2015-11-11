package com.thinkmobiles.bodega.activities.youtube_api;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;

/**
 * Created by illia on 04.11.15.
 */
public class YouTubePlayerActivity extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnFullscreenListener {

    private YouTubePlayerView mPlayerView;
    private YouTubePlayer mYouTubePlayer;
    private String mVideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_view);
        mPlayerView = (YouTubePlayerView) findViewById(R.id.ytvYouTubePlayer);

        Intent intent = getIntent();
        mVideoId = getVideoId(intent.getStringExtra(Constants.EXTRA_VIDEO));
        if (TextUtils.isEmpty(mVideoId))
            finish();
        mPlayerView.initialize(Constants.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        this.mYouTubePlayer = player;
        mYouTubePlayer.setFullscreen(true);
        mYouTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        mYouTubePlayer.setOnFullscreenListener(this);
        if (!wasRestored) {
            mYouTubePlayer.loadVideo(mVideoId);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return mPlayerView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private String getVideoId(String url) {
        int start_index = url.lastIndexOf("/")+1;
        int end_index = url.length();
        return  url.substring(start_index,end_index);
    }

    @Override
    public void onFullscreen(boolean b) {
        // nothing
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mYouTubePlayer.release();
        this.finish();
    }
}
