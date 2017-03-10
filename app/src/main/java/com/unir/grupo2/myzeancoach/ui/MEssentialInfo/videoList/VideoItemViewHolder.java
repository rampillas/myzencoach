package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Video;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 28/02/2017.
 */

public class VideoItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.video_imageView) ImageView videoImageView;
    @BindView(R.id.status_video_imageView) ImageView isCompletedImageView;

    public VideoItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final Video videoItem, final VideoListAdapter.OnItemVideoClickListener listener) {
        Picasso.with(itemView.getContext()).load(videoItem.getPhotoUrl()).resize(700, 700).into(videoImageView);

        isCompletedImageView.setVisibility(View.VISIBLE);
        if (videoItem.getIsWatched()){
            isCompletedImageView.setImageResource(R.mipmap.checked);
        }else{
            isCompletedImageView.setImageResource(R.mipmap.checked_no);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemVideoClick(videoItem.getUrl());
            }
        });
    }
}
