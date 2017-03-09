package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoYoutubeActivity extends YouTubeBaseActivity {

    @BindView(R.id.youtube_view) YouTubePlayerView youtubeView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    private static final String YOUTUBE_KEY = "AIzaSyA5x5UI2cPPfivQWeY4bT7gdB-6Er1aO2Q";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_youtube_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String fullUrl = intent.getStringExtra("URL");
        int inicio = fullUrl.indexOf("watch?v") + 8;
        url = fullUrl.substring(inicio);

        onInitializedListener = new YouTubePlayer.OnInitializedListener(){

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(url);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youtubeView.initialize(YOUTUBE_KEY, onInitializedListener);
    }
}
