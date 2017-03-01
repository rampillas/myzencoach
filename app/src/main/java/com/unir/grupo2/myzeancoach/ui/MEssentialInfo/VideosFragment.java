package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Context;
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

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList.VideoItem;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class VideosFragment extends Fragment implements VideoListAdapter.OnItemVideoClickListener{

    List<VideoItem> videoItemList;
    VideoListAdapter videoListAdapter;
    @BindView(R.id.video_recycler_view) RecyclerView videoListRecyclerView;

    VideosFragment.OnItemVideoSelectedListener onItemVideoSelectedListener;

    public interface OnItemVideoSelectedListener{
        void onItemVideoSelected(String urlVideo);
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

        videoListRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        videoListRecyclerView.setLayoutManager(layoutManager);

        videoItemList = new ArrayList<>();
        VideoItem videoItem1 = new VideoItem(getContext().getDrawable(R.mipmap.film1), true, "https://www.youtube.com/watch?v=jgKnhss6j2Q");
        VideoItem videoItem2 = new VideoItem(getContext().getDrawable(R.mipmap.film2), true, "https://www.youtube.com/watch?v=X69Di5dY0Eo");
        VideoItem videoItem3 = new VideoItem(getContext().getDrawable(R.mipmap.film3), false, "https://www.yhttps://www.youtube.com/watch?v=X69Di5dY0Eooutube.com/watch?v=X69Di5dY0Eo");
        VideoItem videoItem4 = new VideoItem(getContext().getDrawable(R.mipmap.film4), true, "https://www.youtube.com/watch?v=X69Di5dY0Eo");
        VideoItem videoItem5 = new VideoItem(getContext().getDrawable(R.mipmap.film5), false, "https://www.youtube.com/watch?v=X69Di5dY0Eo");
        VideoItem videoItem6 = new VideoItem(getContext().getDrawable(R.mipmap.film6), true, "https://www.youtube.com/watch?v=X69Di5dY0Eo");
        VideoItem videoItem7 = new VideoItem(getContext().getDrawable(R.mipmap.film7), true, "https://www.youtube.com/watch?v=X69Di5dY0Eo");
        VideoItem videoItem8 = new VideoItem(getContext().getDrawable(R.mipmap.film8), true, "https://www.youtube.com/watch?v=X69Di5dY0Eo");
        VideoItem videoItem9 = new VideoItem(getContext().getDrawable(R.mipmap.film9), false, "https://www.youtube.com/watch?v=X69Di5dY0Eo");

        videoItemList.add(videoItem1);
        videoItemList.add(videoItem2);
        videoItemList.add(videoItem3);
        videoItemList.add(videoItem4);
        videoItemList.add(videoItem5);
        videoItemList.add(videoItem6);
        videoItemList.add(videoItem7);
        videoItemList.add(videoItem8);
        videoItemList.add(videoItem9);

        videoListAdapter = new VideoListAdapter(getContext(),videoItemList, this);
        videoListRecyclerView.setAdapter(videoListAdapter);

        return view;
    }

    @Override
    public void onItemVideoClick(String urlVideo) {
        onItemVideoSelectedListener.onItemVideoSelected(urlVideo);
    }
}
