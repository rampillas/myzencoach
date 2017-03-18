package com.unir.grupo2.myzeancoach.ui.profil;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 18/03/2017.
 */

public class EmoticonsItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.emoticon_item_imageView) ImageView emoticonImageView;
    @BindView(R.id.name_emoticon_textView) TextView nameTextView;

    public EmoticonsItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
