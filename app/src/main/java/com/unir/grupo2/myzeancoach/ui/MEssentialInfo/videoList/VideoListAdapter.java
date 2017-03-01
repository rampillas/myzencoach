package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList;

/**
 * Created by Cesar on 28/02/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.unir.grupo2.myzeancoach.R;

import java.util.List;

/**
 * Created by Cesar on 28/02/2017.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoItemViewHolder> {
    private List<VideoItem> videoItemList;
    private Context context;

    public interface OnItemVideoClickListener{
        public void onItemVideoClick(String urlVideo);
    }

    VideoListAdapter.OnItemVideoClickListener listener;

    public VideoListAdapter(Context context,List<VideoItem> videoItemList,VideoListAdapter.OnItemVideoClickListener listener) {
        this.context = context;
        this.videoItemList = videoItemList;
        this.listener = listener;
    }

    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videos_item_layout, viewGroup, false);
        final VideoItemViewHolder videoItemViewHolder = new VideoItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemVideoClick("jgKnhss6j2Q");
            }
        });
        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoItemViewHolder viewHolder, int position) {
        VideoItem videoItem = videoItemList.get(position);
        if (position == 0){
            Picasso.with(context).load(R.mipmap.film1).resize(700, 700).into(viewHolder.videoImageView);
        }else if (position == 1){
            Picasso.with(context).load(R.mipmap.film2).resize(700, 700).into(viewHolder.videoImageView);
        }else if (position == 2){
            Picasso.with(context).load(R.mipmap.film3).resize(700, 700).into(viewHolder.videoImageView);
        }
        else if (position == 3){
            Picasso.with(context).load(R.mipmap.film4).resize(700, 700).into(viewHolder.videoImageView);
        }
        else if (position == 4){
            Picasso.with(context).load(R.mipmap.film5).resize(700, 700).into(viewHolder.videoImageView);
        }else if (position == 5){
            Picasso.with(context).load(R.mipmap.film6).resize(700, 700).into(viewHolder.videoImageView);
        }else if (position == 6){
            Picasso.with(context).load(R.mipmap.film7).resize(700, 700).into(viewHolder.videoImageView);
        }else if (position == 7){
            Picasso.with(context).load(R.mipmap.film8).resize(700, 700).into(viewHolder.videoImageView);
        }else if (position == 8){
            Picasso.with(context).load(R.mipmap.film9).resize(700, 700).into(viewHolder.videoImageView);
        }

        viewHolder.isCompletedImageView.setVisibility(View.VISIBLE);
        if (videoItem.isCompleted()){
            viewHolder.isCompletedImageView.setImageResource(R.mipmap.checked);
        }else{
            viewHolder.isCompletedImageView.setImageResource(R.mipmap.checked_no);
        }
    }

    @Override
    public int getItemCount() {
        return videoItemList.size();
    }
}
