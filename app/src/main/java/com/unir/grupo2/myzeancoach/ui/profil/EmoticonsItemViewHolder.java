package com.unir.grupo2.myzeancoach.ui.profil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.utils.Constants;

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

    public void bind(final String emoticonName,
                     final EmoticonsListAdapter.OnItemEmoticonClickListener listener, Context context) {

        switch (emoticonName) {
            case Constants.VERY_HAPPY:
                emoticonImageView.setImageResource(R.mipmap.ic_very_happy);
                nameTextView.setText(context.getString(R.string.emoticon_very_happy));
                break;
            case Constants.HAPPY:
                emoticonImageView.setImageResource(R.mipmap.ic_happy);
                nameTextView.setText(context.getString(R.string.emoticon_happy));
                break;
            case Constants.IN_LOVE:
                emoticonImageView.setImageResource(R.mipmap.ic_in_love);
                nameTextView.setText(context.getString(R.string.emoticon_in_love));
                break;
            case Constants.LAUGHING:
                emoticonImageView.setImageResource(R.mipmap.ic_laughing);
                nameTextView.setText(context.getString(R.string.emoticon_laughing));
                break;
            case Constants.SAT:
                emoticonImageView.setImageResource(R.mipmap.ic_sat);
                nameTextView.setText(context.getString(R.string.emoticon_sat));
                break;
            case Constants.DISAPPOINTED:
                emoticonImageView.setImageResource(R.mipmap.ic_disappointed);
                nameTextView.setText(context.getString(R.string.emoticon_disappointed));
                break;
            case Constants.CRYING:
                emoticonImageView.setImageResource(R.mipmap.ic_crying);
                nameTextView.setText(context.getString(R.string.emoticon_crying));
                break;
            case Constants.ANGRY:
                emoticonImageView.setImageResource(R.mipmap.ic_angry);
                nameTextView.setText(context.getString(R.string.emoticon_angry));
                break;
            default:
                emoticonImageView.setImageResource(R.mipmap.ic_happy);
                break;
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemEmoticonClick(emoticonName);
            }
        });
    }
}
