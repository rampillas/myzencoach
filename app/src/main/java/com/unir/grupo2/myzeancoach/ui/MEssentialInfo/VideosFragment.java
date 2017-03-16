package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.VideosUseCase;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.Video;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList.VideoListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Cesar on 22/02/2017.
 */

public class VideosFragment extends Fragment implements VideoListAdapter.OnItemVideoClickListener{

    List<Video> videoItemList;
    VideoListAdapter videoListAdapter;
    private UseCase useCase;

    @BindView(R.id.video_recycler_view) RecyclerView videoListRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;


    VideosFragment.OnItemVideoSelectedListener onItemVideoSelectedListener;

    public interface OnItemVideoSelectedListener{
        void onItemVideoSelected(String urlVideo, String videoName, boolean isWatched);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof VideosFragment.OnItemVideoSelectedListener){
            onItemVideoSelectedListener = (VideosFragment.OnItemVideoSelectedListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements VideosFragment.OnItemVideoSelectedListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_layout,null);
        ButterKnife.bind(this, view);

        updateData();

        videoListRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        videoListRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onItemVideoClick(String urlVideo, String videoName, boolean isWatched) {
        onItemVideoSelectedListener.onItemVideoSelected(urlVideo, videoName, isWatched);
    }

    private void updateData() {
        showLoading();
        //we must pass a real token**
        new VideosUseCase().execute(new VideosSubscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        videoListRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        videoListRecyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        videoListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }


    private void updateList(List<Video> videoList){
        videoItemList = videoList;
        videoListAdapter = new VideoListAdapter(getContext(),videoItemList, this);
        videoListRecyclerView.setAdapter(videoListAdapter);
    }

    //Save videos on Shared Preference to be used from other places
    private void saveVideosSharedPreference(List<Video> videoList){
        Gson gson = new Gson();
        String jsonList = gson.toJson(videoList);

        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.PREFERENCES_VIDEOS), jsonList);
        editor.commit();
    }

    private final class VideosSubscriber extends Subscriber<List<Video>> {
        //3 callbacks

        //Show the listView
        @Override public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(List<Video> videoList) {
            saveVideosSharedPreference(videoList);
            updateList(videoList);
        }
    }
}
