package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 28/02/2017.
 */

public class VideoItemViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.video_imageView) ImageView videoImageView;
    @BindView(R.id.status_video_imageView) ImageView isCompletedImageView;

    public VideoItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
