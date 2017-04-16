package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Video;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList.VideoListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Cesar on 22/02/2017.
 */

public class VideosFragment extends Fragment implements VideoListAdapter.OnItemVideoClickListener{

    static final int VIDEO_YOUTUBE_REQUEST = 1;

    @BindView(R.id.video_recycler_view) RecyclerView videoListRecyclerView;
    @BindView(R.id.content_layout) RelativeLayout contentRelativeLayout;
    @BindView(R.id.no_video_layout) LinearLayout noVideoLayout;
    @BindView(R.id.message_textView) TextView messageNoDielmmaTextView;
    @BindView(R.id.loadItemsLayout_recyclerView) RelativeLayout loadingDataRelativeLayout;

    private LinearLayoutManager layoutManager;

    // Variables for scroll listener
    private boolean userScrolled = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    UpdateDataEsentialInfoListener updateDataListener;

    public interface UpdateDataEsentialInfoListener {
        void updateDataEsentialInfo(int positionViewPager);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof VideosFragment.UpdateDataEsentialInfoListener){
            updateDataListener = (VideosFragment.UpdateDataEsentialInfoListener) context;
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

        Bundle bundle = getArguments();
        List<Video> videoItemList = bundle.getParcelableArrayList("VIDEOS");

        if (videoItemList != null && !videoItemList.isEmpty()){

            videoListRecyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getContext(),2);
            videoListRecyclerView.setLayoutManager(layoutManager);
            VideoListAdapter videoListAdapter = new VideoListAdapter(getContext(),videoItemList, this);
            videoListRecyclerView.setAdapter(videoListAdapter);
            implementScrollListener();
            showContent();
        }else{
            showNoDilemma();
        }

        return view;
    }

    @Override
    public void onItemVideoClick(String urlVideo, String videoName, boolean isWatched) {
        Intent intent = new Intent(getActivity(), VideoYoutubeActivity.class);
        intent.putExtra("URL", urlVideo);
        intent.putExtra("VIDEO_NAME", videoName);
        intent.putExtra("IS_WATCHED", isWatched);
        startActivityForResult(intent, VIDEO_YOUTUBE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**************Module Essential information***************/
        if (requestCode == VIDEO_YOUTUBE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getBooleanExtra("IS_UPDATED", true)) {
                        updateDataListener.updateDataEsentialInfo(0);
                    }
                }
            }
        }
    }

    public void showNoDilemma() {
        noVideoLayout.setVisibility(View.VISIBLE);
        contentRelativeLayout.setVisibility(View.GONE);
        messageNoDielmmaTextView.setText(getString(R.string.message_no_video));
    }

    public void showContent() {
        contentRelativeLayout.setVisibility(View.VISIBLE);
        noVideoLayout.setVisibility(View.GONE);
    }

    //Pagination
    // Implement scroll listener
    private void implementScrollListener() {
        videoListRecyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);

                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;

                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager

                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;

                            updateRecyclerView();
                        }

                    }

                });

    }

    // Method for repopulating recycler view
    private void updateRecyclerView() {

        // Show Progress Layout
        loadingDataRelativeLayout.setVisibility(View.VISIBLE);

        // Handler to show refresh for a period of time you can use async task
        // while commnunicating serve

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                // After adding new data hide the view.
                loadingDataRelativeLayout.setVisibility(View.GONE);

            }
        }, 8000);
    }
}
