package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList;

/**
 * Created by Cesar on 28/02/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.model.VideoUI;

import java.util.List;

/**
 * Created by Cesar on 28/02/2017.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoItemViewHolder> {
    private List<VideoUI> videoItemList;
    private Context context;

    public interface OnItemVideoClickListener{
        public void onItemVideoClick(String urlVideo);
    }

    VideoListAdapter.OnItemVideoClickListener listener;

    public VideoListAdapter(Context context,List<VideoUI> videoItemList,VideoListAdapter.OnItemVideoClickListener listener) {
        this.context = context;
        this.videoItemList = videoItemList;
        this.listener = listener;
    }

    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videos_item_layout, viewGroup, false);
        final VideoItemViewHolder videoItemViewHolder = new VideoItemViewHolder(view);
       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = videoItemViewHolder.getAdapterPosition();
                listener.onItemVideoClick("jgKnhss6j2Q");
            }
        });*/
        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoItemViewHolder viewHolder, int position) {
        VideoUI videoItem = videoItemList.get(position);
        viewHolder.bind(videoItemList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return videoItemList.size();
    }
}
