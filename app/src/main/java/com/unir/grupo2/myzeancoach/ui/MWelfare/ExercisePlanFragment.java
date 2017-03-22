package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 22/02/2017.
 */

public class ExercisePlanFragment extends Fragment {

    @BindView(R.id.description_textView) TextView descriptionTextView;
    @BindView(R.id.instructions_textView) TextView instructionTextView;
    @BindView(R.id.play_audio_imageView) ImageView playAudioImageView;
    @BindView(R.id.youtube_layout) FrameLayout youtubeView;

    String fullUrlAudio;
    private String url;

    private static final String YOUTUBE_KEY = "AIzaSyA5x5UI2cPPfivQWeY4bT7gdB-6Er1aO2Q";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welfare_exercise_layout, null);
        ButterKnife.bind(this, view);

        ExerciseWelfare exercise;
        Bundle bundle = getArguments();
        if (bundle != null) {
            exercise = bundle.getParcelable("EXERCISE");
        }else{
            exercise = null;
        }

        descriptionTextView.setText(exercise.getDescription());
        instructionTextView.setText(exercise.getInstructions());

        fullUrlAudio = exercise.getAudioUrl();

        return view;
    }

    @OnClick(R.id.play_audio_imageView)
    public void playAudio(View view) {
        playAudioImageView.setVisibility(View.GONE);
        youtubeView.setVisibility(View.VISIBLE);

        //Checking if the URL is from Youtube
        if (!fullUrlAudio.toLowerCase().contains("watch?v".toLowerCase())){
            new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.dialog_title_url_no_valid))
                    .setMessage(getString(R.string.dialog_massage_no_youtube_url_audio))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            int inicio = fullUrlAudio.indexOf("watch?v") + 8;
            url = fullUrlAudio.substring(inicio);

            YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

            youTubePlayerFragment.initialize(YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    if (!b) {

                        youTubePlayer.loadVideo(url);
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    String errorMessage = youTubeInitializationResult.toString();
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                    Log.d("errorMessage:", errorMessage);
                }

            });
        }
    }
}
