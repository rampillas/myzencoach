package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    boolean isWatched;
    boolean isUpdated = false;
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
        isWatched = intent.getBooleanExtra("IS_WATCHED", false);


        //Checking if the URL is from Youtube
        if (!fullUrl.toLowerCase().contains("watch?v".toLowerCase())){
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_title_url_no_valid))
                    .setMessage(getString(R.string.dialog_massage_no_youtube_url_video))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            int inicio = fullUrl.indexOf("watch?v") + 8;
            url = fullUrl.substring(inicio);

            myPlayerStateChangeListener = new MyPlayerStateChangeListener();

            onInitializedListener = new YouTubePlayer.OnInitializedListener(){

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                    youTubePlayer.loadVideo(url);

                    youTubePlayer.setPlayerStateChangeListener(myPlayerStateChangeListener);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            };

            youtubeView.initialize(YOUTUBE_KEY, onInitializedListener);
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        private void updateLog(String prompt){
            log +=  "Video player " + "\n" +
                    prompt + "\n\n=====";
            Toast.makeText(getApplicationContext(),
                    log,
                    Toast.LENGTH_SHORT).show();
        };

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onError(
                com.google.android.youtube.player.YouTubePlayer.ErrorReason arg0) {
            updateLog("onError(): " + arg0.toString());
        }

        @Override
        public void onLoaded(String arg0) {

        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
            updateLog("onVideoEnded()");
            if (!isWatched){
                String text = "{\n" +
                        "\t\"name\": \""+ videoName + "\",\n" +
                        "\t\"is_watched\": true\n" +
                        "}";
                RequestBody body =
                        RequestBody.create(MediaType.parse("text/plain"), text);

                new UpdateVideoUseCase(body).execute(new UpdateVideoSubscriber());
            }else{
                setReturnData();
                finish();
            }
        }

        @Override
        public void onVideoStarted() {

        }
    }

    @Override
    public void onBackPressed() {
        setReturnData();
        finish();
    }

    private void setReturnData(){
        Intent resultData = new Intent();
        resultData.putExtra("IS_UPDATED", isUpdated);
        setResult(Activity.RESULT_OK, resultData);
    }

    private final class UpdateVideoSubscriber extends Subscriber<Void> {
        //3 callbacks

        //Show the listView
        @Override public void onCompleted() {
        }

        //Show the error
        @Override public void onError(Throwable e) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.error_update_video),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Void aVoid) {
            isUpdated = true;
            setReturnData();
            finish();
        }
    }

}
