package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.UpdateVideoUseCase;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

public class VideoYoutubeActivity extends YouTubeBaseActivity {

    @BindView(R.id.youtube_view) YouTubePlayerView youtubeView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    private static final String YOUTUBE_KEY = "AIzaSyA5x5UI2cPPfivQWeY4bT7gdB-6Er1aO2Q";
    String url;
    String videoName;
    String log = "";

    private MyPlayerStateChangeListener myPlayerStateChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_youtube_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String fullUrl = intent.getStringExtra("URL");
        videoName = intent.getStringExtra("VIDEO_NAME");

        int inicio = fullUrl.indexOf("watch?v") + 8;
        url = fullUrl.substring(inicio);

        myPlayerStateChangeListener = new MyPlayerStateChangeListener();

        onInitializedListener = new YouTubePlayer.OnInitializedListener(){

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                youTubePlayer.loadVideo(url);

                Toast.makeText(getApplicationContext(),
                        "YouTubePlayer.onInitializationSuccess()",
                        Toast.LENGTH_LONG).show();

                youTubePlayer.setPlayerStateChangeListener(myPlayerStateChangeListener);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youtubeView.initialize(YOUTUBE_KEY, onInitializedListener);
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        private void updateLog(String prompt){
            log +=  "MyPlayerStateChangeListener" + "\n" +
                    prompt + "\n\n=====";
            Toast.makeText(getApplicationContext(),
                    log,
                    Toast.LENGTH_SHORT).show();
        };

        @Override
        public void onAdStarted() {
           // updateLog("onAdStarted()");
        }

        @Override
        public void onError(
                com.google.android.youtube.player.YouTubePlayer.ErrorReason arg0) {
            updateLog("onError(): " + arg0.toString());
        }

        @Override
        public void onLoaded(String arg0) {
            //updateLog("onLoaded(): " + arg0);
        }

        @Override
        public void onLoading() {
            //updateLog("onLoading()");
        }

        @Override
        public void onVideoEnded() {
            updateLog("onVideoEnded()");
            String text = "{\n" +
                    "\t\"name\": \"Talco\",\n" +
                    "\t\"is_watched\": true\n" +
                    "}";
            RequestBody body =
                    RequestBody.create(MediaType.parse("text/plain"), text);

            new UpdateVideoUseCase("application/json", "Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK",body).execute(new UpdateVideoSubscriber());
        }

        @Override
        public void onVideoStarted() {
            updateLog("onVideoStarted()");
        }
    }

    private final class UpdateVideoSubscriber extends Subscriber<Void> {
        //3 callbacks

        //Show the listView
        @Override public void onCompleted() {
            Toast.makeText(getApplicationContext(),
                    "onCompleted",
                    Toast.LENGTH_SHORT).show();
        }

        //Show the error
        @Override public void onError(Throwable e) {
            Toast.makeText(getApplicationContext(),
                    "onError",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Void aVoid) {
            Toast.makeText(getApplicationContext(),
                    "onNext",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
